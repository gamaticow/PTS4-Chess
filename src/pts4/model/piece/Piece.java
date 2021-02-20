package pts4.model.piece;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:41
 */

@RequiredArgsConstructor
public abstract class Piece {

    @Getter @NonNull private final ChessBoard board;
    @Getter @NonNull private final Color color;
    @Getter @NonNull private Coordinate coordinate;

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
    public void moveTo(Coordinate coordinate) {
        if(canMove(coordinate)) {
            //TODO remove eaten piece
            this.coordinate = coordinate;
        }
    }

}
