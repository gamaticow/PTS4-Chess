package pts4.model.piece;

import javafx.scene.image.ImageView;
import lombok.Getter;
import pts4.controller.GameController;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.io.*;
import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:41
 */

public abstract class Piece {

    @Getter private final ChessBoard board;
    @Getter private final ChessColor color;
    @Getter private Coordinate coordinate;
    @Getter private final ImageView image;
    @Getter private boolean hasMove;


    public Piece(ChessBoard board, ChessColor color, Coordinate coordinate, char piece){
        this.board = board;
        this.color = color;
        this.coordinate = coordinate;
        this.hasMove = false;

        String name = String.valueOf(color.getPrefix()) + piece + ".png";
        File image = new File(Piece.class.getClassLoader().getResource(name).getFile());
        this.image = new ImageView(image.toURI().toString());
    }

    /**
     * Liste de tous les coups possible sans prendre en compte les autres pièces du plateau
     * @return La liste des coups
     */
    public abstract List<Coordinate> allMoveList();

    /**
     * Liste de tous les coups réelement possible en enlevant les coup bloquer par ses pièces et en s'aretant au pièces adverse
     * @return La liste des coups possible
     */
    public abstract List<Coordinate> moveList();

    /**
     * Teste si le coup est possible sur une case particulière
     * @param coordinate La coordonnée de la case
     * @return Si la pièce peux aller sur cette case
     */
    public boolean canMove(Coordinate coordinate) {
        return moveList().contains(coordinate);
    }

    /**
     * Déplace la pièce
     * @param coordinate Case sur laquel déplacer la pièce
     */
    public boolean moveTo(Coordinate coordinate) {
        //System.out.println(coordinate);
        if(canMove(coordinate)) {
            Piece pieceOn = board.getPiece(coordinate);
            if(pieceOn != null) {
                if (pieceOn.color != color) {
                    board.getPieces().remove(pieceOn);
                } else {
                    return false;
                }
            }

            this.hasMove = true;
            this.coordinate = coordinate;
            return true;
        }
        return false;
    }

    public String toString() {
        String out = "";

        String name = getClass().getSimpleName();
        if(name.equals("Knight"))
            name = "n";

        out += Character.toLowerCase(name.charAt(0));
        out += Character.toLowerCase(getColor().toString().charAt(0));
        out += getCoordinate().getX();
        out += getCoordinate().getY();

        return out;
    }

    public static Piece from(ChessBoard board, String string) {
        Piece piece = null;
        ChessColor color = ChessColor.WHITE;
        if(string.charAt(1) == 'b')
            color = ChessColor.BLACK;

        Coordinate coordinate = new Coordinate(Integer.parseInt(String.valueOf(string.charAt(2))), Integer.parseInt(String.valueOf(string.charAt(3))));

        switch (string.charAt(0)) {
            case 'b':
                piece = new Bishop(board, color, coordinate);
                break;
            case 'k':
                piece = new King(board, color, coordinate);
                break;
            case 'n':
                piece = new Knight(board, color, coordinate);
                break;
            case 'p':
                piece = new Pawn(board, color, coordinate);
                break;
            case 'q':
                piece = new Queen(board, color, coordinate);
                break;
            case 'r':
                piece = new Rook(board, color, coordinate);
        }

        return piece;
    }

}
