package pts4;

import javafx.application.Application;
import javafx.stage.Stage;
import pts4.controller.ModeController;

/**
 * Created by Corentin on 24/02/2021 at 11:47
 */

public class ChessApplication extends Application {

    public static final int PORT = 20202;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chess");
        ModeController.startView(primaryStage);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
