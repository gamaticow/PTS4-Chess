package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Knight extends Piece {

    public Knight(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'n');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        //Tous les mouvement possible d'un cavalier
        allPossibility.add(new Coordinate(getCoordinate().getX()+2,getCoordinate().getY()+1));
        allPossibility.add(new Coordinate(getCoordinate().getX()+1,getCoordinate().getY()+2));
        allPossibility.add(new Coordinate(getCoordinate().getX()-1,getCoordinate().getY()+2));
        allPossibility.add(new Coordinate(getCoordinate().getX()-2,getCoordinate().getY()+1));
        allPossibility.add(new Coordinate(getCoordinate().getX()-2,getCoordinate().getY()-1));
        allPossibility.add(new Coordinate(getCoordinate().getX()-1,getCoordinate().getY()-2));
        allPossibility.add(new Coordinate(getCoordinate().getX()+1,getCoordinate().getY()-2));
        allPossibility.add(new Coordinate(getCoordinate().getX()+2,getCoordinate().getY()-1));

        //On enlève tous les cases négatives
        allPossibility.removeIf(coordinate -> coordinate.getX() < 0 || coordinate.getX() > 7 || coordinate.getY() < 0 || coordinate.getY() > 7);

        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }

}
