package pts4.model;

import lombok.Getter;
import lombok.Setter;
import pts4.model.piece.King;

/**
 * Created by Corentin on 20/02/2021 at 12:44
 */

public class ChessBoard {

    @Getter @Setter private King white, black;

}
