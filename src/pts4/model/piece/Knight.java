package pts4.model.piece;

import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Knight extends Piece {

    public Knight(ChessBoard board, Color color, Coordinate coordinate) {
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
