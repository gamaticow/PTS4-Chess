package pts4.model.piece;

import pts4.controller.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Bishop extends Piece {


    public Bishop(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'b');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        for (int i = -7; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX() + i, getCoordinate().getY() + i));
            allPossibility.add(new Coordinate(getCoordinate().getX() + i, getCoordinate().getY() - i));

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
        List<Coordinate> move = allMoveList();
        int vF = 7;
        int vB = 7;
        int hL = 0;
        int hR = 7;

        for (Coordinate coordinate : move) {
            Piece pieceOn = getBoard().getCoordinate(coordinate);
            if (pieceOn == null)
                continue;
            if (getCoordinate().getX() < coordinate.getX() && getCoordinate().getY() < coordinate.getY()) {//Front right
                if (coordinate.getX() <= vF) {
                    if (pieceOn.getColor() == getColor())
                        vF = coordinate.getX() - 1;
                    else
                        vF = coordinate.getX();
                }
            }
            if (getCoordinate().getX() < coordinate.getX() && getCoordinate().getY() > coordinate.getY()) {//Front left
                if (coordinate.getX() <= vB) {
                    if (pieceOn.getColor() == getColor())
                        vB = coordinate.getX() - 1;
                    else
                        vB = coordinate.getX();
                }
            }
            if (getCoordinate().getX() > coordinate.getX() && getCoordinate().getY() < coordinate.getY()) {//Back right
                if (coordinate.getY() <= hR) {
                    if (pieceOn.getColor() == getColor())
                        hR = coordinate.getY() - 1;
                    else
                        hR = coordinate.getY();
                }
            }
            if (getCoordinate().getX() > coordinate.getX() && getCoordinate().getY() > coordinate.getY()) {//Back left
                if (coordinate.getY() >= hL) {
                    if (pieceOn.getColor() == getColor())
                        hL = coordinate.getY() + 1;
                    else
                        hL = coordinate.getY();
                }
            }
        }
        Iterator<Coordinate> iterator = move.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            if (getCoordinate().getX() < coordinate.getX() && getCoordinate().getY() < coordinate.getY()) {
                if (coordinate.getX() > vF)
                    iterator.remove();
            } else if (getCoordinate().getX() < coordinate.getX() && getCoordinate().getY() > coordinate.getY()) {
                if (coordinate.getX() > vB)
                    iterator.remove();
            } else if (getCoordinate().getX() > coordinate.getX() && getCoordinate().getY() < coordinate.getY()) {
                if (coordinate.getY() > hR)
                    iterator.remove();
            } else if (getCoordinate().getX() > coordinate.getX() && getCoordinate().getY() > coordinate.getY()) {
                if (coordinate.getY() < hL)
                    iterator.remove();
            }


        }
        return move;
    }
}
