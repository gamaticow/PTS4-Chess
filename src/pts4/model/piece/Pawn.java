package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:41
 */

public class Pawn extends Piece {

    public Pawn(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate);
    }

    @Override
    public List<Coordinate> allMoveList() {
        return null;
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
