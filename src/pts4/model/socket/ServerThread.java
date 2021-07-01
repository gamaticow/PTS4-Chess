package pts4.model.socket;

import lombok.Setter;
import lombok.SneakyThrows;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;
import pts4.model.piece.ChessColor;
import pts4.model.player.Player;
import pts4.model.player.RemotePlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by Corentin on 01/04/2021 at 11:57
 */

public class ServerThread extends Thread {

    private final Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    private ServerThread opponent;
    private ChessBoard board;
    private ChessColor color;
    private String lastMove = "";

    @Setter private boolean running = true;

    @SneakyThrows
    public ServerThread(Socket client) {
        this.client  = client;
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());

        color = ChessColor.WHITE;

        name = in.readUTF();
    }

    public ServerThread(Socket client, ServerThread opponent) {
        this(client);
        color = ChessColor.BLACK;

        this.opponent = opponent;
        opponent.opponent = this;

        Player p1 = new RemotePlayer(ChessColor.WHITE);
        p1.setName(opponent.name);
        Player p2 = new RemotePlayer(ChessColor.BLACK);
        p2.setName(name);

        board = new ChessBoard(p1, p2);
        opponent.board = this.board;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (running) {
            byte type = in.readByte();

            if(type == 1) {
                if(board == null) {
                    out.writeUTF("wait");
                } else {
                    DataPacket packet = board.toData();
                    packet.write("you", (color == ChessColor.WHITE ? "1" : "2"));
                    out.writeUTF(packet.toString());
                }
            }else if(type == 2) {
                out.writeUTF(board.getP1().isTurn() ? "1" : "2");
            }else if(type == 3) {
                out.writeUTF(opponent.lastMove);
            }else if(type == 4) {
                String data = in.readUTF();
                Coordinate c1 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(0))), Integer.parseInt(String.valueOf(data.charAt(1))));
                Coordinate c2 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(2))), Integer.parseInt(String.valueOf(data.charAt(3))));
                board.getPiece(c1).moveTo(c2);
                lastMove = "" + c1.getX() + c1.getY() + c2.getX() + c2.getY();
                board.swapPlaying();
            }
        }
    }
}
