package pts4.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private final Text text;

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

        text = new Text("");
        text.setFont(Font.font(null, FontWeight.BOLD, 40));
        text.setFill(Color.RED);
        text.setTextAlignment(TextAlignment.CENTER);
        //text.setX(-50);
        text.setY(100);
        getChildren().add(text);

        revalidate();
    }

    public void revalidate() {
        for (Piece piece : board.getChessBoard().getPieces()) {
            setPiece(piece);
        }

        for(Square square : squareList) {
            square.setSelected(false);
        }

        if(board.isEnd()) {
            text.setText("Partie gagnée par " + board.getWinner().getName());
        }
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        double size = Math.min(width, height);
        double cell = size / 8.0;

        text.setY(size / 2.0);

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
