package pts4.model.player;

import lombok.Getter;
import lombok.Setter;
import pts4.model.ChessBoard;
import pts4.model.piece.ChessColor;

import java.io.Serializable;

/**
 * Created by Corentin on 27/03/2021 at 13:53
 */

public abstract class Player {

    @Getter @Setter protected ChessBoard board;
    @Getter protected final ChessColor color;

    @Getter @Setter private String name;
    @Getter @Setter private boolean turn;

    public Player(String name, ChessColor color) {
        this.name = name;
        this.color = color;
    }

}
