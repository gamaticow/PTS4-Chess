package pts4.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import pts4.model.piece.ChessColor;

/**
 * Created by Corentin on 24/02/2021 at 11:51
 */

public class Square extends Group {

    private final Rectangle background;

    public Square(ChessColor color) {
        Translate pos = new Translate();
        background = new Rectangle();
        background.getTransforms().add(pos);

        if(color == ChessColor.WHITE)
            background.setFill(Color.web("#EEEED2"));
        else if (color == ChessColor.BLACK)
            background.setFill(Color.web("#769656"));

        getChildren().add(background);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        background.setWidth(width);
        background.setHeight(height);
    }
}
