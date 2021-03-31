package pts4.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Corentin on 20/02/2021 at 12:47
 */

@EqualsAndHashCode (exclude = {"tag"})
@RequiredArgsConstructor
@ToString (exclude = {"tag"})
public class Coordinate implements Serializable {

    @NonNull @Getter private final int x, y;
    @Accessors(fluent = true) @Getter @Setter private String tag;

    public int getRealX() {
        return 7-x;
    }

    public int getRealY() {
        return y;
    }

}
