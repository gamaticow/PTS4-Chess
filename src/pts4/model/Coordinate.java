package pts4.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Corentin on 20/02/2021 at 12:47
 */

@EqualsAndHashCode
@AllArgsConstructor
public class Coordinate {

    @Getter private final int x, y;

    public int getRealX() {
        return 7-x;
    }

    public int getRealY() {
        return y;
    }

}
