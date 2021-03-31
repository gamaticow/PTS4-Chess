package pts4.controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.Getter;
import pts4.model.ChessBoard;
import pts4.model.Coordinate;
import pts4.model.piece.*;
import pts4.model.player.LocalPlayer;
import pts4.model.player.Player;
import pts4.model.socket.SocketClient;
import pts4.view.Board;
import pts4.view.Info;
import pts4.view.Square;

/**
 * Created by Corentin on 20/02/2021 at 12:44
 */

public class GameController extends Pane {
    @Getter private final ChessBoard chessBoard;

    private final Info infoP1, infoP2;

    private final Rectangle background;
    private final Board board;
    private Piece selected;

    private SocketClient client;

    private GameController(Player player1, Player player2) {
        this(new ChessBoard(player1, player2));
    }

    private GameController(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        chessBoard.setRevalidate(this);

        background = new Rectangle();
        background.setFill(Color.WHITE);

        getChildren().add(background);

        board = new Board(this);

        for(Piece piece : chessBoard.getPieces()) {
            piece.getImage().setOnMouseClicked(event -> {
                click(piece.getCoordinate());
            });
        }

        for (Square square : board.getSquareList()) {
            square.setOnMouseClicked(event -> {
                click(square.getCoordinate());
            });
        }

        this.infoP1 = new Info(chessBoard.getP1());
        this.infoP2 = new Info(chessBoard.getP2());

        getChildren().add(infoP1);
        getChildren().add(board);
        getChildren().add(infoP2);
    }

    private void click(Coordinate coordinate) {
        Player player = getPlaying();
        if(!(player instanceof LocalPlayer))
            return;
        Piece pieceOn = chessBoard.getPiece(coordinate);
        if(pieceOn != null && pieceOn.getColor() == player.getColor()) {
            selected = pieceOn;
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    board.getSquares()[i][j].setSelected(false);
                }
            }
            for(Coordinate c : pieceOn.moveList()) {
                board.getSquares()[c.getRealY()][c.getRealX()].setSelected(true);
            }
        } else {
            if(selected != null) {
                if(selected.moveTo(coordinate)) {
                    if(client != null)
                        client.move(Piece.lastMove);
                    selected = null;
                    chessBoard.swapPlaying();
                    revalidate();
                }
            }
        }
    }

    private Player getPlaying() {
        return chessBoard.getP1().isTurn() ? chessBoard.getP1() : chessBoard.getP2();
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);

        final int INFO_HEIGHT = 75;

        background.setWidth(width);
        background.setHeight(height);

        double size = Math.min(width, height-(INFO_HEIGHT*2));
        double space = (width-size)/2;

        board.relocate(space, INFO_HEIGHT);
        infoP1.relocate(0, INFO_HEIGHT+size);

        infoP1.resize(width, INFO_HEIGHT);
        board.resize(width, height-(INFO_HEIGHT*2));
        infoP2.resize(width, INFO_HEIGHT);
    }

    public void revalidate() {
        selected = null;
        board.revalidate();
        infoP1.revalidate();
        infoP2.revalidate();
    }

    private void runRefreshThread(SocketClient client) {
        this.client = client;
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String turn = client.getTurn();
                if(turn.equals("1") && !chessBoard.getP1().isTurn() || turn.equals("2") && !chessBoard.getP2().isTurn()) {
                    String data = client.getLastMove();
                    Coordinate c1 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(0))), Integer.parseInt(String.valueOf(data.charAt(1))));
                    Coordinate c2 = new Coordinate(Integer.parseInt(String.valueOf(data.charAt(2))), Integer.parseInt(String.valueOf(data.charAt(3))));

                    chessBoard.swapPlaying();
                    chessBoard.getPiece(c1).moveTo(c2);

                    Platform.runLater(this::revalidate);
                }
            }
        }).start();
    }

    public static GameController startView(Stage primaryStage, Player p1, Player p2) {
        GameController gameController = new GameController(p1, p2);
        primaryStage.setScene(new Scene(gameController));

        return gameController;
    }

    public static GameController startView(Stage primaryStage, ChessBoard chessBoard) {
        GameController gameController = new GameController(chessBoard);
        primaryStage.setScene(new Scene(gameController));

        return gameController;
    }

    public static GameController startView(Stage primaryStage, ChessBoard chessBoard, SocketClient client) {
        GameController gameController = new GameController(chessBoard);
        primaryStage.setScene(new Scene(gameController));

        gameController.runRefreshThread(client);

        return gameController;
    }

}
