package pts4.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import pts4.model.player.Player;

/**
 * Created by Corentin on 27/03/2021 at 15:02
 */

public class Info extends Group {

    private final Player player;
    private final Rectangle background;
    private final Text name;

    public Info(Player player) {
        this.player = player;

        background = new Rectangle();
        background.setFill(Color.web("#FFFFED"));
        background.setWidth(50);
        background.setHeight(100);
        getChildren().add(background);

        name = new Text(player.getName());
        name.setY(13);
        getChildren().add(name);

        revalidate();
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        background.setWidth(width);
        background.setHeight(height);
    }

    public void revalidate() {
        name.setText(player.getName() + (player.isTurn() ? " (En train de jouer)" : ""));
    }
}
