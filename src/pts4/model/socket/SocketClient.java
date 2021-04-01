package pts4.model.socket;

import lombok.Getter;
import lombok.SneakyThrows;
import pts4.ChessApplication;

import java.io.*;
import java.net.Socket;

/**
 * Created by Corentin on 31/03/2021 at 00:03
 */

public class SocketClient {

    @Getter private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    @SneakyThrows
    public SocketClient(String ip, int port) {
        socket = new Socket(ip, port);

        if(socket.isConnected()) {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }else {
            in = null;
            out = null;
        }
    }

    @SneakyThrows
    public void sendName(String name) {
        out.writeUTF(name);
        out.flush();
    }

    public String getBoard() {
        return send(1);
    }

    public String getTurn() {
        return send(2);
    }

    public String getLastMove() {
        return send(3);
    }

    @SneakyThrows
    public void move(String data) {
        out.writeByte(4);
        out.writeUTF(data);
        out.flush();
    }

    @SneakyThrows
    private String send(int data) {
        out.writeByte(data);
        out.flush();

        return in.readUTF();
    }

}
