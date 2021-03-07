package pts4.model.piece;

import pts4.controller.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Bishop extends Piece {

    private int y ,x;

    public Bishop(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'b');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        for(int i = -7; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX()+i, getCoordinate().getY()+i));
            allPossibility.add(new Coordinate(getCoordinate().getX()+i, getCoordinate().getY()-i));
        }

        //On enlève toutes les cases négatives
        allPossibility.removeIf(coordinate -> coordinate.getX() < 0 || coordinate.getX() > 7 || coordinate.getY() < 0 || coordinate.getY() > 7);
        //On enlève la case courante de la pièce
        allPossibility.removeIf(coordinate -> coordinate.equals(getCoordinate()));

        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }

}
