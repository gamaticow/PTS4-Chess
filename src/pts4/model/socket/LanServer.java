package pts4.model.socket;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pts4.ChessApplication;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;
import pts4.model.piece.Piece;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Corentin on 30/03/2021 at 23:54
 */

public class LanServer implements Runnable {

    @Getter private final ChessBoard board;
    private final ServerSocket server;
    @Getter private boolean ready;
    @Setter private Runnable callback;

    @SneakyThrows
    public LanServer(ChessBoard chessBoard) {
        this.board = chessBoard;
        this.server = new ServerSocket(ChessApplication.PORT);
        this.ready = false;

        new Thread(this).start();
    }

    @SneakyThrows
    @Override
    public void run() {
        Socket client = server.accept();
        ready = true;
        if(callback != null)
            callback.run();

        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        System.out.println("Connected");

        while (true) {
            byte type = in.readByte();

            if(type == 1) {
                DataPacket packet = board.toData();
                packet.write("you", "2");
                out.writeUTF(packet.toString());
            }else if(type == 2) {
                out.writeUTF(board.getP1().isTurn() ? "1" : "2");
            }else if(type == 3) {
                out.writeUTF(Piece.lastMove);
            }else if(type == 4) {
                String data = in.readUTF();
                Coordinate c1 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(0))), Integer.parseInt(String.valueOf(data.charAt(1))));
                Coordinate c2 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(2))), Integer.parseInt(String.valueOf(data.charAt(3))));
                board.getPiece(c1).moveTo(c2);
                board.swapPlaying();
                Platform.runLater(board::revalidate);
            }
        }

    }
}
