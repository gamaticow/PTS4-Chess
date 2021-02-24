package pts4.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pts4.view.ChessBoard;

/**
 * Created by Corentin on 24/02/2021 at 11:47
 */

public class ChessApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(new ChessBoard(), 600, 600));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
