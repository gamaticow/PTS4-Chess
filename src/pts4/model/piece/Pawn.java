package pts4.model.piece;

import pts4.controller.GameController;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:41
 */

public class Pawn extends Piece {

    public static final String FRONT_MOVE = "front";
    public static final String FRONT_MOVE_DOUBLE = "front_double";
    public static final String EAT_MOVE = "eat";

    public Pawn(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'p');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        int direction = getColor() == ChessColor.WHITE ? 1 : -1;
        //Test si il peut avancer de 2 cases (au premier mouvement)
        if (!isHasMove()){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction*2,this.getCoordinate().getY()).tag(FRONT_MOVE_DOUBLE));
        }
        //Avancer d'une case
        allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()).tag(FRONT_MOVE));

        //Si il n'est pas collé a gauche il peut manger a gauche
        if (getCoordinate().getY() < 7){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()+1).tag(EAT_MOVE));
        }

        //Si il n'est pas collé a droite il peut manger a droite
        if (getCoordinate().getY() > 0){
            allPossibility.add(new Coordinate(this.getCoordinate().getX()+direction,this.getCoordinate().getY()-1).tag(EAT_MOVE));
        }
        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        List<Coordinate> move = allMoveList();

        boolean front = true;
        for(int i = 0; i < 2; i++) {
            Iterator<Coordinate> iterator = move.iterator();
            while (iterator.hasNext()) {
                Coordinate coordinate = iterator.next();
                Piece pieceOn = getBoard().getPiece(coordinate);
                if (coordinate.tag().equals(EAT_MOVE)) {
                    if (pieceOn == null || pieceOn.getColor() == getColor())
                        iterator.remove();
                } else if (coordinate.tag().equals(FRONT_MOVE)) {
                    if (pieceOn != null) {
                        front = false;
                        iterator.remove();
                    }
                } else if(coordinate.tag().equals(FRONT_MOVE_DOUBLE)) {
                    if(!front || pieceOn != null)
                        iterator.remove();
                }
            }
        }

        return move;
    }
}
