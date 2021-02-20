package pts4.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Corentin on 20/02/2021 at 12:47
 */

@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {

    @Getter private final int x, y;

}
