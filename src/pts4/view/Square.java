package pts4.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import lombok.Getter;
import pts4.model.Coordinate;
import pts4.model.piece.ChessColor;
import pts4.model.piece.Piece;

/**
 * Created by Corentin on 24/02/2021 at 11:51
 */

public class Square extends Group {

    private final ChessColor color;
    @Getter private final Coordinate coordinate;
    private final Rectangle background;
    private Piece piece;

    public Square(ChessColor color, Coordinate coordinate) {
        this.color = color;
        this.coordinate = coordinate;
        Translate pos = new Translate();
        background = new Rectangle();
        background.getTransforms().add(pos);

        setSelected(false);

        getChildren().add(background);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        background.setWidth(width);
        background.setHeight(height);

        if(piece != null) {
            piece.getImage().setFitHeight(height);
            piece.getImage().setFitWidth(width);
        }
    }

    public void setPiece(Piece piece) {
        removePiece();
        this.piece = piece;
        getChildren().add(piece.getImage());
    }

    public void removePiece() {
        if(piece == null)
            return;

        getChildren().remove(piece.getImage());
        piece = null;
    }

    @Getter private boolean selected = false;

    public void setSelected(boolean value) {
        this.selected = value;
        if(value)
            background.setFill(Color.RED);
        else {
            if(color == ChessColor.WHITE)
                background.setFill(Color.web("#EEEED2"));
            else if (color == ChessColor.BLACK)
                background.setFill(Color.web("#769656"));
        }
    }

}
