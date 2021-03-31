package pts4.model.piece;

import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class King extends Piece {

    public King(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'k');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();

        //Ajout des possibilité de roque
        /*if(!isHasMove()) {
            allPossibility.add(new Coordinate(getCoordinate().getX(), getCoordinate().getY()+2));
            allPossibility.add(new Coordinate(getCoordinate().getX(), getCoordinate().getY()-2));
        }*/

        //Ajout de tous le mouvements possible
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                allPossibility.add(new Coordinate(getCoordinate().getX()+x, getCoordinate().getY()+y));
            }
        }

        //On enlève tous les cases négatives
        allPossibility.removeIf(coordinate -> coordinate.getX() < 0 || coordinate.getX() > 7 || coordinate.getY() < 0 || coordinate.getY() > 7);
        //On enlève la case courante de la pièce
        allPossibility.removeIf(coordinate -> coordinate.equals(getCoordinate()));
        //On enlève les case dupliquer dans la liste
        return allPossibility.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Coordinate> moveList() {
        List<Coordinate> move = allMoveList();
        Iterator<Coordinate> iterator = move.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            Piece pieceOn = getBoard().getPiece(coordinate);
            if(pieceOn != null && pieceOn.getColor() == getColor())
                iterator.remove();
        }
        return move;
        //rajoute en cas d'échec
    }

    /**
     * Méthode pour savoir si le roi est en échec
     * @return Si le roi est en échec
     */
    public boolean isCheck() {

        return false;
    }

}
