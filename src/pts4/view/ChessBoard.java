package pts4.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;
import pts4.model.piece.ChessColor;
import pts4.model.piece.King;

/**
 * Created by Corentin on 20/02/2021 at 12:44
 */

public class ChessBoard extends Pane {

    @Getter @Setter private King whiteKing, blackKing;

    private final Rectangle background;
    private final Square[][] squares;

    public ChessBoard() {
        background = new Rectangle();
        background.setFill(Color.WHITE);

        getChildren().add(background);

        squares = new Square[8][8];

        for(int x = 0; x < 8; x++) {
            ChessColor color = ChessColor.BLACK;
            if(x % 2 == 0)
                color = ChessColor.WHITE;
            for(int y = 0; y < 8; y++) {
                squares[x][y] = new Square(color);
                if (color == ChessColor.BLACK)
                    color = ChessColor.WHITE;
                else
                    color = ChessColor.BLACK;

                getChildren().add(squares[x][y]);
            }
        }

    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        background.setWidth(width);
        background.setHeight(height);

        double cellWidth = width / 8.0;
        double cellHeight = height / 8.0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j].relocate(i * cellWidth, j * cellHeight);
                squares[i][j].resize(cellWidth, cellHeight);
            }
        }
    }
}
