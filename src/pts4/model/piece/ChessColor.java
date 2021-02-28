package pts4.model.piece;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Corentin on 20/02/2021 at 12:45
 */

@AllArgsConstructor
public enum ChessColor {

    WHITE   ('w'),
    BLACK   ('b');

    @Getter private final char prefix;

}
