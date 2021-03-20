package pts4.model.piece;

import javafx.scene.image.ImageView;
import lombok.Getter;
import pts4.controller.ChessBoard;
import pts4.model.Coordinate;

import java.io.File;
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
        //System.out.println(name);
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
        System.out.println(coordinate);
        if(canMove(coordinate)) {
            //TODO remove eaten piece
            this.hasMove = true;
            this.coordinate = coordinate;
            return true;
        }
        return false;
    }


}
