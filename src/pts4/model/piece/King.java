package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class King extends Piece {

    public King(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'k');
    }

    @Override
    public List<Coordinate> allMoveList() {
        return null;
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }

    /**
     * Méthode pour savoir si le roi est en échec
     * @return Si le roi est en échec
     */
    public boolean isCheck() {

        return false;
    }

}
