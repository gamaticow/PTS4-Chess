package pts4.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import pts4.model.Coordinate;
import pts4.model.piece.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corentin on 20/02/2021 at 12:44
 */

public class ChessBoard extends Pane {

    @Getter private King whiteKing, blackKing;
    @Getter private final List<Piece> pieces;

    private final Rectangle background;
    private final Board board;

    public ChessBoard() {
        this.pieces = new ArrayList<>();
        fill();

        background = new Rectangle();
        background.setFill(Color.WHITE);

        getChildren().add(background);

        board = new Board(this);
        getChildren().add(board);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        background.setWidth(width);
        background.setHeight(height);

        double size = Math.min(width, height-200);
        double space = (width-size)/2;
        board.relocate(space, 0);
        board.resize(width, height-200);
    }

    private void fill(){
        //WHITE
        ChessColor color = ChessColor.WHITE;
        // - PAWN
        int row = 1;
        for(int i = 0; i < 8; i++)
            pieces.add(new Pawn(this, color, new Coordinate(row, i)));
        // - ROOK
        row = 0;
        pieces.add(new Rook(this, color, new Coordinate(row, 0)));
        pieces.add(new Rook(this, color, new Coordinate(row, 7)));
        // - KNIGHT
        pieces.add(new Knight(this, color, new Coordinate(row, 1)));
        pieces.add(new Knight(this, color, new Coordinate(row, 6)));
        // - BISHOP
        pieces.add(new Bishop(this, color, new Coordinate(row, 2)));
        pieces.add(new Bishop(this, color, new Coordinate(row, 5)));
        // - QUEEN
        pieces.add(new Queen(this, color, new Coordinate(row, 3)));
        // - KING
        whiteKing = new King(this, color, new Coordinate(row, 4));
        pieces.add(whiteKing);

        //BLACK
        color = ChessColor.BLACK;
        // - PAWN
        row = 6;
        for(int i = 0; i < 8; i++)
            pieces.add(new Pawn(this, color, new Coordinate(row, i)));
        // - ROOK
        row = 7;
        pieces.add(new Rook(this, color, new Coordinate(row, 0)));
        pieces.add(new Rook(this, color, new Coordinate(row, 7)));
        // - KNIGHT
        pieces.add(new Knight(this, color, new Coordinate(row, 1)));
        pieces.add(new Knight(this, color, new Coordinate(row, 6)));
        // - BISHOP
        pieces.add(new Bishop(this, color, new Coordinate(row, 2)));
        pieces.add(new Bishop(this, color, new Coordinate(row, 5)));
        // - QUEEN
        pieces.add(new Queen(this, color, new Coordinate(row, 3)));
        // - KING
        blackKing = new King(this, color, new Coordinate(row, 4));
        pieces.add(blackKing);

    }

}
