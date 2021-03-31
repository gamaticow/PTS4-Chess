package pts4.view;

import javafx.scene.layout.Pane;
import lombok.Getter;
import pts4.controller.GameController;
import pts4.model.Coordinate;
import pts4.model.piece.ChessColor;
import pts4.model.piece.Piece;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Corentin on 26/02/2021 at 17:31
 */

public class Board extends Pane {

    @Getter private final Square[][] squares;
    @Getter private final List<Square> squareList;
    private final GameController board;

    public Board(GameController board) {
        this.board = board;
        squares = new Square[8][8];

        for(int x = 0; x < 8; x++) {
            ChessColor color = ChessColor.BLACK;
            if(x % 2 == 0)
                color = ChessColor.WHITE;
            for(int y = 0; y < 8; y++) {
                squares[x][y] = new Square(color, new Coordinate(7-y, x));
                if (color == ChessColor.BLACK)
                    color = ChessColor.WHITE;
                else
                    color = ChessColor.BLACK;

                getChildren().add(squares[x][y]);
            }
        }

        squareList = Arrays.stream(squares).flatMap(Arrays::stream).collect(Collectors.toList());

        revalidate();
    }

    public void revalidate() {
        for (Piece piece : board.getChessBoard().getPieces()) {
            setPiece(piece);
        }

        for(Square square : squareList) {
            square.setSelected(false);
        }
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        double size = Math.min(width, height);
        double cell = size / 8.0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].relocate(i * cell, j * cell);
                squares[i][j].resize(cell, cell);
            }
        }
    }

    private void setPiece(Piece piece) {
        squares[piece.getCoordinate().getRealY()][piece.getCoordinate().getRealX()].setPiece(piece);
    }
}
