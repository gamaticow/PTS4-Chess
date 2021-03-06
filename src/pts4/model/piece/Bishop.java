package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;

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
        if(getCoordinate().getX()<7 && getCoordinate().getY() < 7){
            x= getCoordinate().getX();
            y = getCoordinate().getY();
            while (x < 7 && y < 7){
                x++;
                y++;
                allPossibility.add(new Coordinate(x,y));
            }
        }
        if (getCoordinate().getY() > 0 && getCoordinate().getX()> 0){
            x= getCoordinate().getX();
            y = getCoordinate().getY();
            while(y > 0 && x > 0){
                x--;
                y--;
                allPossibility.add(new Coordinate(x,y));
            }
        }
        if (getCoordinate().getY() > 0 && getCoordinate().getX() <7){
            x= getCoordinate().getX();
            y = getCoordinate().getY();
            while(y > 0 && x < 7){
                x++;
                y--;
                allPossibility.add(new Coordinate(x,y));
            }
        }
        if (getCoordinate().getY() < 7&& getCoordinate().getX() > 0){
            x= getCoordinate().getX();
            y = getCoordinate().getY();
            while(y < 7 && x > 0){
                x--;
                y++;
                allPossibility.add(new Coordinate(x,y));
            }
        }

        return allPossibility;
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }

}
