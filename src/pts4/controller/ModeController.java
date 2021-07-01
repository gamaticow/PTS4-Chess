package pts4.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import pts4.ChessApplication;
import pts4.ChessServer;
import pts4.model.ChessBoard;
import pts4.model.piece.ChessColor;
import pts4.model.player.LocalPlayer;
import pts4.model.player.Player;
import pts4.model.player.RemotePlayer;
import pts4.model.socket.LanServer;
import pts4.model.socket.SocketClient;

import java.io.IOException;

/**
 * Created by Corentin on 29/03/2021 at 20:16
 */

public class ModeController {

    public AnchorPane stage;
    public TextField lanIp;
    public TextField serverIp;
    public TextField name;

    public void local(MouseEvent mouseEvent) {
        Player p1 = new LocalPlayer("Joueur 1", ChessColor.WHITE);
        Player p2 = new LocalPlayer("Joueur 2", ChessColor.BLACK);
        GameController.startView((Stage) stage.getScene().getWindow(), p1, p2);
    }

    @SneakyThrows
    public void host(MouseEvent mouseEvent) {
        Player p1 = new LocalPlayer("Joueur 1", ChessColor.WHITE);
        RemotePlayer p2 = new RemotePlayer(ChessColor.BLACK);

        WaitRoom.server = new LanServer(new ChessBoard(p1, p2));

        WaitRoom.startView((Stage) stage.getScene().getWindow(), WaitRoom.Mode.LAN);
    }

    public void join(MouseEvent mouseEvent) {
        SocketClient client = new SocketClient(lanIp.getText(), ChessApplication.PORT);

        GameController.startView((Stage) stage.getScene().getWindow(), ChessBoard.from(client.getBoard()), client);
    }

    @SneakyThrows
    public void connect(MouseEvent mouseEvent) {
        SocketClient client = new SocketClient(serverIp.getText(), ChessServer.PORT);

        String name = this.name.getText();
        if(name == null || name.isEmpty())
            name = "Joueur";

        client.sendName(name);

        String board = client.getBoard();
        if(board.equals("wait")) {
            WaitRoom.client = client;
            WaitRoom.startView((Stage) stage.getScene().getWindow(), WaitRoom.Mode.SERVER);
        } else {
            GameController.startView((Stage) stage.getScene().getWindow(), ChessBoard.from(board), client);
        }
    }

    public static ModeController startView(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(ModeController.class.getResource("/pts4/view/mode.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        return loader.getController();
    }
}
