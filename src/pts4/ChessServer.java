package pts4;

import pts4.model.piece.Piece;
import pts4.model.socket.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Corentin on 01/04/2021 at 11:54
 */

public class ChessServer {

    public static final int PORT = 20222;

    public static void main(String[] args) throws IOException {
        Piece.graphics = false;

        ServerSocket server = new ServerSocket(PORT);

        ServerThread last = null;

        while (true) {
            Socket socket = server.accept();

            if(last == null) {
                last = new ServerThread(socket);
                last.start();
            } else {
                new ServerThread(socket, last).start();
                last = null;
            }


        }
    }

}
