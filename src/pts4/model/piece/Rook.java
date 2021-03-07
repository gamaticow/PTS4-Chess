package pts4.model.piece;

import pts4.controller.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Rook extends Piece {

    public Rook(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'r');
    }

    @Override
    public List<Coordinate> allMoveList() {
        List<Coordinate> allPossibility = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX(), i));
            allPossibility.add(new Coordinate(i, getCoordinate().getY()));
        }
        allPossibility.removeIf(coordinate -> coordinate.equals(getCoordinate()));
        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
