package pts4.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import pts4.model.ChessBoard;
import pts4.model.socket.LanServer;
import pts4.model.socket.SocketClient;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Corentin on 30/03/2021 at 16:58
 */

public class WaitRoom {

    public AnchorPane stage;
    public Text text;

    public static LanServer server;
    public static SocketClient client;
    private Mode mode;

    public void prepareUnlock() {
        if(mode == Mode.LAN) {
            if (server.isReady())
                unlock();
            else
                server.setCallback(this::unlock);
        }else if(mode == Mode.SERVER) {
            new Thread(() -> {
                String board = client.getBoard();
                while (board.equals("wait")) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    board = client.getBoard();
                }
                unlock();
            }).start();
        }
    }

    @SneakyThrows
    public void loadText() {
        if(mode == Mode.LAN) {
            text.setText("Adresse ip locale : " + InetAddress.getLocalHost().getHostAddress());
        } else if(mode == Mode.SERVER) {
            text.setText("En attente d'un second joueur");
        }
    }

    public void unlock() {
        if(mode == Mode.LAN)
            Platform.runLater(() -> GameController.startView((Stage) stage.getScene().getWindow(), server.getBoard()));
        else if (mode == Mode.SERVER)
            Platform.runLater(() -> GameController.startView((Stage) stage.getScene().getWindow(), ChessBoard.from(client.getBoard()), client));
    }

    public static WaitRoom startView(Stage primaryStage, Mode mode) throws IOException {
        FXMLLoader loader = new FXMLLoader(ModeController.class.getResource("/pts4/view/waiting.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        WaitRoom controller = loader.getController();
        controller.mode = mode;
        controller.loadText();
        controller.prepareUnlock();
        return loader.getController();
    }

    public enum Mode {
        LAN, SERVER;
    }

}
