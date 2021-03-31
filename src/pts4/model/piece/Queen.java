package pts4.model.piece;

import pts4.controller.GameController;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 20/02/2021 at 12:42
 */

public class Queen extends Piece {

    private static final String H = "1";
    private static final String V = "2";
    private static final String FRONT = "3";
    private static final String RIGHT = FRONT;
    private static final String BACK = "4";
    private static final String LEFT = BACK;

    private static final String H_RIGHT = H + RIGHT;
    private static final String H_LEFT = H + LEFT;
    private static final String V_FRONT = V + FRONT;
    private static final String V_BACK = V + BACK;
    private static final String DIAG = "5";

    public Queen(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'q');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();

        //Mouvement en ligne droite
        for(int i = 0; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX(), i).tag(getCoordinate().getY() < i ? H_RIGHT : H_LEFT));
            allPossibility.add(new Coordinate(i, getCoordinate().getY()).tag(getCoordinate().getX() < i ? V_FRONT : V_BACK));
        }

        //Mouvement en diagonale
        for(int i = -7; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX()+i, getCoordinate().getY()+i).tag(DIAG));
            allPossibility.add(new Coordinate(getCoordinate().getX()+i, getCoordinate().getY()-i).tag(DIAG));
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

        int F = 7,B = 0,L = 0,R = 7;
        int vF = 7,vB = 7,hL = 0,hR = 7;

        for(Coordinate coordinate : move) {
            Piece pieceOn = getBoard().getPiece(coordinate);
            if(pieceOn == null)
                continue;
            if(coordinate.tag().contains(V)) {
                if(coordinate.tag().contains(FRONT)) {
                    if(coordinate.getX() <= F) {
                        if(pieceOn.getColor() == getColor())
                            F = coordinate.getX() - 1;
                        else
                            F = coordinate.getX();
                    }
                }else if(coordinate.tag().contains(BACK)) {
                    if(coordinate.getX() >= B) {
                        if(pieceOn.getColor() == getColor())
                            B = coordinate.getX() + 1;
                        else
                            B = coordinate.getX();
                    }
                }
            } else if(coordinate.tag().contains(H)) {
                if(coordinate.tag().contains(RIGHT)) {
                    if(coordinate.getY() <= R) {
                        if(pieceOn.getColor() == getColor())
                            R = coordinate.getY() - 1;
                        else
                            R = coordinate.getY();
                    }
                }else if(coordinate.tag().contains(LEFT)) {
                    if(coordinate.getY() >= L) {
                        if(pieceOn.getColor() == getColor())
                            L = coordinate.getY() + 1;
                        else
                            L = coordinate.getY();
                    }
                }
            }else if (coordinate.tag().equals(DIAG)){
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
        }
        Iterator<Coordinate> iterator = move.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            if(coordinate.tag().equals(V_FRONT)) {
                if(coordinate.getX() > F)
                    iterator.remove();
            } else if(coordinate.tag().equals(V_BACK)) {
                if(coordinate.getX() < B)
                    iterator.remove();
            } else if(coordinate.tag().equals(H_RIGHT)) {
                if(coordinate.getY() > R)
                    iterator.remove();
            } else if(coordinate.tag().equals(H_LEFT)) {
                if(coordinate.getY() < L)
                    iterator.remove();
            }else if (coordinate.tag().equals(DIAG)){
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
        }
        return move;
    }
}


