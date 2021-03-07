package pts4.model.piece;

import pts4.controller.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        //Test si il peut avancer de 2 cases (au premier mouvement)
        if (!isHasMove()){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction*2,this.getCoordinate().getY()));
        }
        //Avancer d'une case
        allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()));

        //Si il n'est pas collé a gauche il peut manger a gauche
        if (getCoordinate().getY() < 7){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()+1));
        }

        //Si il n'est pas collé a droite il peut manger a droite
        if (getCoordinate().getY() > 0){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()-1));
        }
        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        return null;
    }
}
