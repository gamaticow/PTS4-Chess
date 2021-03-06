package pts4.model.piece;

import pts4.view.ChessBoard;
import pts4.model.Coordinate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:41
 */

public class Pawn extends Piece {

    public Pawn(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'p');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        int direction = getColor() == ChessColor.WHITE ? 1 : -1;
        if (!isHasMove()){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction*2,this.getCoordinate().getY()));
        }
        if(getCoordinate().getX()<7 && getCoordinate().getX() > 0){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()));
        }
        if (getCoordinate().getY() <7 && (getCoordinate().getX() >0 && getCoordinate().getX() < 7)){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()+1));
        }
        if (getCoordinate().getY() > 0  && (getCoordinate().getX() >0 && getCoordinate().getX() < 7)){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()-1));
        }
        return allPossibility;
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
