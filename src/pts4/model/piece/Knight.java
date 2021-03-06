package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Knight extends Piece {

    public Knight(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'n');
    }

    @Override
    public List<Coordinate> allMoveList() {ArrayList<Coordinate> allPossibility = new ArrayList<>();
        if(getCoordinate().getX()<7 && getCoordinate().getY()<7){
            allPossibility.add(new Coordinate(getCoordinate().getX()+2,getCoordinate().getY()+1));
            allPossibility.add(new Coordinate(getCoordinate().getX()+1,getCoordinate().getY()+2));
        }
        if (getCoordinate().getX()>0 && getCoordinate().getY()<7){
            allPossibility.add(new Coordinate(getCoordinate().getX()-1,getCoordinate().getY()+2));
            allPossibility.add(new Coordinate(getCoordinate().getX()-2,getCoordinate().getY()+1));
        }
        if (getCoordinate().getX()>0 && getCoordinate().getY()>0){
            allPossibility.add(new Coordinate(getCoordinate().getX()-2,getCoordinate().getY()-1));
            allPossibility.add(new Coordinate(getCoordinate().getX()-1,getCoordinate().getY()-2));
        }
        if (getCoordinate().getX()<7 && getCoordinate().getY()>0){
            allPossibility.add(new Coordinate(getCoordinate().getX()+1,getCoordinate().getY()-2));
            allPossibility.add(new Coordinate(getCoordinate().getX()+2,getCoordinate().getY()-1));
        }
        return allPossibility;}

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
