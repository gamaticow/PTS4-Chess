package pts4.model;

import lombok.Getter;
import pts4.controller.GameController;
import pts4.model.piece.*;
import pts4.model.player.LocalPlayer;
import pts4.model.player.Player;
import pts4.model.player.RemotePlayer;
import pts4.model.socket.DataPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 30/03/2021 at 18:54
 */

public class ChessBoard {

    @Getter private King whiteKing, blackKing;
    @Getter private final List<Piece> pieces;
    @Getter private Player p1, p2;

    private GameController toRevalidate;

    private ChessBoard() {
        pieces = new ArrayList<>();
    }

    public ChessBoard(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.pieces = new ArrayList<>();
        fill();

        p1.setBoard(this);
        p1.setTurn(true);
        p2.setBoard(this);
        p2.setTurn(false);
    }

    public void swapPlaying() {
        if(p1.isTurn()) {
            p1.setTurn(false);
            p2.setTurn(true);
        } else {
            p2.setTurn(false);
            p1.setTurn(true);
        }
    }

    private void fill(){
        //WHITE
        ChessColor color = ChessColor.WHITE;
        // - PAWN
        int row = 1;
        for(int i = 0; i < 8; i++)
            pieces.add(new Pawn(this, color, new Coordinate(row, i)));
        // - ROOK
        row = 0;
        pieces.add(new Rook(this, color, new Coordinate(row, 0)));
        pieces.add(new Rook(this, color, new Coordinate(row, 7)));
        // - KNIGHT
        pieces.add(new Knight(this, color, new Coordinate(row, 1)));
        pieces.add(new Knight(this, color, new Coordinate(row, 6)));
        // - BISHOP
        pieces.add(new Bishop(this, color, new Coordinate(row, 2)));
        pieces.add(new Bishop(this, color, new Coordinate(row, 5)));
        // - QUEEN
        pieces.add(new Queen(this, color, new Coordinate(row, 3)));
        // - KING
        whiteKing = new King(this, color, new Coordinate(row, 4));
        pieces.add(whiteKing);

        //BLACK
        color = ChessColor.BLACK;
        // - PAWN
        row = 6;
        for(int i = 0; i < 8; i++)
            pieces.add(new Pawn(this, color, new Coordinate(row, i)));
        // - ROOK
        row = 7;
        pieces.add(new Rook(this, color, new Coordinate(row, 0)));
        pieces.add(new Rook(this, color, new Coordinate(row, 7)));
        // - KNIGHT
        pieces.add(new Knight(this, color, new Coordinate(row, 1)));
        pieces.add(new Knight(this, color, new Coordinate(row, 6)));
        // - BISHOP
        pieces.add(new Bishop(this, color, new Coordinate(row, 2)));
        pieces.add(new Bishop(this, color, new Coordinate(row, 5)));
        // - QUEEN
        pieces.add(new Queen(this, color, new Coordinate(row, 3)));
        // - KING
        blackKing = new King(this, color, new Coordinate(row, 4));
        pieces.add(blackKing);
    }

    public Piece getPiece(Coordinate coordinate) {
        for(Piece piece : pieces){
            if(piece.getCoordinate().equals(coordinate))
                return piece;
        }
        return null;
    }

    public DataPacket toData() {
        DataPacket packet = new DataPacket();

        int i = 0;
        for(Piece piece : pieces) {
            packet.write("piece"+i, piece.toString());
            i++;
        }

        packet.write("size", String.valueOf(i));

        packet.write("p1", p1.getName());
        packet.write("p2", p2.getName());

        packet.write("turn", p1.isTurn() ? "1" : "2");

        return packet;
    }

    public static ChessBoard from(String string) {
        ChessBoard board = new ChessBoard();
        DataPacket packet = DataPacket.from(string);

        for(int i = 0; i < Integer.parseInt(packet.read("size")); i++) {
            board.pieces.add(Piece.from(board, packet.read("piece"+i)));
        }

        if(packet.read("you").equals("1")) {
            board.p1 = new LocalPlayer(packet.read("p1"), ChessColor.WHITE);
            board.p2 = new RemotePlayer(ChessColor.WHITE);
            board.p2.setName(packet.read("p2"));
        }else {
            board.p2 = new LocalPlayer(packet.read("p2"), ChessColor.BLACK);
            board.p1 = new RemotePlayer(ChessColor.WHITE);
            board.p1.setName(packet.read("p1"));
        }

        if(packet.read("turn").equals("1")) {
            board.p1.setTurn(true);
            board.p2.setTurn(false);
        }else {
            board.p2.setTurn(true);
            board.p1.setTurn(false);
        }

        return board;
    }

    public void setRevalidate(GameController controller) {
        this.toRevalidate = controller;
    }

    public void revalidate() {
        if(toRevalidate != null)
            toRevalidate.revalidate();
    }

}
