package pts4.model.player;

import pts4.model.piece.ChessColor;

import java.rmi.Remote;

/**
 * Created by Corentin on 30/03/2021 at 16:32
 */

public class RemotePlayer extends Player {

    public RemotePlayer(ChessColor color) {
        super("Joueur", color);
    }

}
