package pts4.model.piece;

import lombok.SneakyThrows;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 31/03/2021 at 23:57
 */

public class CheckDetector {

    private final ChessBoard board;
    private final ChessColor color;

    public CheckDetector(ChessBoard board, ChessColor color) {
        this.board = board;
        this.color = color;
    }

    public boolean isCheck() {
        King test = color == ChessColor.WHITE ? board.getWhiteKing() : board.getBlackKing();
        for(Piece piece : board.getPieces()) {
            if(piece.getColor() == color)
                continue;
            if(piece.moveList().contains(test.getCoordinate()))
                return true;
        }
        return false;
    }

    @SneakyThrows
    public List<Coordinate> getMoveFor(Piece piece) {
        if(!isCheck())
            return piece.moveList();

        List<Coordinate> moves = new ArrayList<>();
        for(Coordinate coordinate : piece.moveList()) {
            ChessBoard clone = board.clone();
            clone.getPiece(piece.getCoordinate()).moveTo(coordinate, false);
            if(!new CheckDetector(clone, color).isCheck())
                moves.add(coordinate);
        }

        return moves;
    }

    public boolean hasNoMove() {
        for(Piece piece : board.getPieces()) {
            if(piece.getColor() == color) {
                if(getMoveFor(piece).size() > 0)
                    return false;
            }
        }
        return true;
    }

}
