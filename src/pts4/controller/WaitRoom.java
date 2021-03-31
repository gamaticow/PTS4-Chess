package pts4.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import pts4.model.socket.LanServer;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Corentin on 30/03/2021 at 16:58
 */

public class WaitRoom {

    public AnchorPane stage;
    public Text text;

    public static LanServer server;

    public WaitRoom() {
        if(server.isReady())
            unlock();
        else
            server.setCallback(this::unlock);
    }

    @SneakyThrows
    public void loadText(Mode mode) {
        if(mode == Mode.LAN) {
            text.setText("Adresse ip locale : " + InetAddress.getLocalHost().getHostAddress());
        }
    }

    public void unlock() {
        Platform.runLater(() -> GameController.startView((Stage) stage.getScene().getWindow(), server.getBoard()));
    }

    public static WaitRoom startView(Stage primaryStage, Mode mode) throws IOException {
        FXMLLoader loader = new FXMLLoader(ModeController.class.getResource("/pts4/view/waiting.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        WaitRoom controller = loader.getController();
        controller.loadText(mode);
        return loader.getController();
    }

    public enum Mode {
        LAN, SERVER;
    }

}
