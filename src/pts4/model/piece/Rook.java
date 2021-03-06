package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Rook extends Piece {

    private int x,y;

    public Rook(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'r');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        if(getCoordinate().getX()<7){
            x= getCoordinate().getX();
            while (x < 7){
                x++;
                allPossibility.add(new Coordinate(x,getCoordinate().getY()));
            }
        }
        if (getCoordinate().getX()> 0){
            x= getCoordinate().getX();
            while(x > 0){
                x--;
                allPossibility.add(new Coordinate(x,getCoordinate().getY()));
            }
        }
        if (getCoordinate().getY() > 0){
            y = getCoordinate().getY();
            while(y > 0){
                y--;
                allPossibility.add(new Coordinate(getCoordinate().getX(),y));
            }
        }
        if (getCoordinate().getY() < 7){
            y = getCoordinate().getY();
            while(y < 7 ){
                y++;
                allPossibility.add(new Coordinate(getCoordinate().getX(),y));
            }
        }
        return allPossibility;
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
