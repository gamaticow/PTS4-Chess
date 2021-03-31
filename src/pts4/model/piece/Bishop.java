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

public class Bishop extends Piece {

    private int y, x;
    public static final String LEFT = "1";
    public static final String RIGHT = "2";
    public static final String FRONT = "3";
    public static final String BACK = "4";

    public static final String BACK_LEFT = LEFT + BACK;
    public static final String FRONT_RIGHT = RIGHT + FRONT;
    public static final String BACK_RIGHT = RIGHT + BACK;
    public static final String FRONT_LEFT = LEFT + FRONT;

    public Bishop(ChessBoard board, ChessColor color, Coordinate coordinate) {
        super(board, color, coordinate, 'b');
    }

    @Override
    public List<Coordinate> allMoveList() {
        ArrayList<Coordinate> allPossibility = new ArrayList<>();
        for (int i = -7; i < 8; i++) {
            allPossibility.add(new Coordinate(getCoordinate().getX() + i, getCoordinate().getY() + i).tag(getCoordinate().getY() < i ? BACK_LEFT : FRONT_RIGHT));
            allPossibility.add(new Coordinate(getCoordinate().getX() + i, getCoordinate().getY() - i).tag(getCoordinate().getX() < i ? BACK_RIGHT : FRONT_LEFT));

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
        int fR = 7;
        int fL = 7;
        int bL = 0;
        int bR = 7;

        for (Coordinate coordinate : move) {
            Piece pieceOn = getBoard().getCoordinate(coordinate);
            if (pieceOn == null)
                continue;
            if (coordinate.tag().contains(FRONT)) {
                if (coordinate.tag().contains(RIGHT)) {
                    if (coordinate.getX() <= fR) {
                        if (pieceOn.getColor() == getColor())
                            fR = coordinate.getX() - 1;
                        else
                            fR = coordinate.getX();
                    }
                } else if (coordinate.tag().contains(LEFT)) {
                    if (coordinate.getX() <= fL) {
                        if (pieceOn.getColor() == getColor())
                            fL = coordinate.getX() - 1;
                        else
                            fL = coordinate.getX();
                    }
                }
            } else if (coordinate.tag().contains(BACK)) {
                if (coordinate.tag().contains(RIGHT)) {
                    if (coordinate.getY() <= bR) {
                        if (pieceOn.getColor() == getColor())
                            bR = coordinate.getY() - 1;
                        else
                            bR = coordinate.getY();
                    }
                } else if (coordinate.tag().contains(LEFT)) {
                    if (coordinate.getY() >= bL) {
                        if (pieceOn.getColor() == getColor())
                            bL = coordinate.getY() + 1;
                        else
                            bL = coordinate.getY();
                    }
                }
            }
        }

        System.out.println(fR);

        Iterator<Coordinate> iterator = move.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            if (coordinate.tag().equals(FRONT_RIGHT)) {
                if (coordinate.getX() > fR)
                    iterator.remove();
            } else if (coordinate.tag().equals(FRONT_LEFT)) {
                if (coordinate.getX() > fL)
                    iterator.remove();
            } else if (coordinate.tag().equals(BACK_RIGHT)) {
                if (coordinate.getY() > bR)
                    iterator.remove();
            } else if (coordinate.tag().equals(BACK_LEFT)) {
                if (coordinate.getY() < bL)
                    iterator.remove();
            }
        }
        return move;
    }


}
