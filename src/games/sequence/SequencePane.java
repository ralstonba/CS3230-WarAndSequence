package games.sequence;

import java.util.Optional;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Brendan Ralston
 */
public class SequencePane extends BorderPane {

    private final int BLUE_HAND_X = -900;
    private final int GREEN_HAND_X = 900;
    private final int BLUE_PILE_X = -920;
    private final int GREEN_PILE_X = 840;
    private final int PILE_Y = -450;

    private BoardPane boardLayout;
    private Player bluePlayer;
    private Player greenPlayer;
    private Deck deck;
    private static boolean bluePlayerTurn = true;
    private VBox player1Hand;
    private VBox player2Hand;

    public void initialize() {
        boardLayout = new BoardPane();
        boardLayout.setSequencePane(this);
        bluePlayer = new Player(PieceType.BLUE);
        greenPlayer = new Player(PieceType.GREEN);
        deck = new Deck();

        for (Card c : deck.cards) {
            getChildren().add(c);
            moveCard(c, getWidth() / 2, getHeight() / 2);
        }
    }

    private void moveCard(Card c, double x, double y) {
        c.relocate(x - c.getCenterX(), y - c.getCenterY());
    }

    private void showBoard() {
        boardLayout.setOpacity(0);
        boardLayout.relocate(960, 500);
        setCenter(boardLayout);
        FadeTransition ft = new FadeTransition(Duration.millis(500), boardLayout);
        ft.setFromValue(0);
        ft.setToValue(1);

        for (Piece p : bluePlayer.piecePile) {
            p.setCenterX(getWidth() / 2);
            getChildren().add(p);
            p.setTranslateX(BLUE_PILE_X + Math.random() * 80);
            p.setTranslateY(PILE_Y + Math.random() * 80);
        }

        for (Piece p : greenPlayer.piecePile) {
            p.setCenterX(getWidth() / 2);
            getChildren().add(p);
            p.setTranslateX(GREEN_PILE_X + Math.random() * 80);
            p.setTranslateY(PILE_Y + Math.random() * 80);
        }
        ft.play();
    }

    public void shuffle() {
        ParallelTransition pt = new ParallelTransition();
        Random r = new Random();
        for (Card c : deck.cards) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(600), c);
            tt.setByX(r.nextInt(1920 / 2 - 50) * (r.nextBoolean() ? 1 : -1));
            tt.setByY(r.nextInt(1080 / 2 - 50) * (r.nextBoolean() ? 1 : -1));

            RotateTransition rt = new RotateTransition(Duration.millis(600), c);
            rt.setByAngle(r.nextInt(180));
            pt.getChildren().addAll(tt, rt);
        }
        pt.setOnFinished(e -> {
            deck.shuffle();
            SequentialTransition st = new SequentialTransition();
            for (Card c : deck.cards) {
                c.toFront();
                ParallelTransition pt2 = new ParallelTransition();

                TranslateTransition tt = new TranslateTransition(Duration.millis(50), c);
                tt.setToX(0);
                tt.setToY(450);

                RotateTransition rt = new RotateTransition(Duration.millis(50), c);
                rt.setToAngle(0);

                pt2.getChildren().addAll(tt, rt);
                st.getChildren().add(pt2);
            }
            st.play();
            st.setOnFinished(event -> {
                showBoard();
                boardLayout.toBack();
            });
        });
        pt.play();
    }

    public void dealCards() {
        player1Hand = new VBox();
        player1Hand.setAlignment(Pos.CENTER_LEFT);
        player1Hand.setPadding(new Insets(0, 0, 0, 50));

        player2Hand = new VBox();
        player2Hand.setAlignment(Pos.CENTER_RIGHT);
        player2Hand.setPadding(new Insets(0, 50, 0, 0));

        setLeft(player1Hand);
        setRight(player2Hand);
        //getChildren().addAll(player1Hand, player2Hand);

        SequentialTransition st = new SequentialTransition();
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                st.getChildren().add(dealCard(bluePlayer));
            } else {
                st.getChildren().add(dealCard(greenPlayer));
            }
        }
        st.play();
    }

    public Transition dealCard(Player p) {

        Card cardToDeal = deck.dealCard();

        cardToDeal.setOnDragDetected((MouseEvent event) -> {
            if ((bluePlayerTurn && ((VBox) cardToDeal.getParent()) == player2Hand) || (!bluePlayerTurn && ((VBox) cardToDeal.getParent()) == player1Hand)) {
                return;
            }

            System.out.println("Drag detected on: " + cardToDeal.toString());
            Dragboard db = cardToDeal.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(cardToDeal.getImage());
            db.setContent(content);
            cardToDeal.setVisible(false);
            event.consume();
        });

        cardToDeal.setOnDragDone((DragEvent event) -> {
            System.out.println("DragDone on: " + cardToDeal.toString());
            if (event.getTransferMode() == TransferMode.MOVE) {
                //Card was drop was successful
                ((VBox) cardToDeal.getParent()).getChildren().remove(cardToDeal);
                bluePlayerTurn = !bluePlayerTurn;

                SequentialTransition dealTransition = new SequentialTransition();
                if (p.getType() == PieceType.BLUE) {
                    dealTransition.getChildren().add(dealCard(bluePlayer));
                } else {
                    dealTransition.getChildren().add(dealCard(greenPlayer));
                }
                dealTransition.play();

            } else {
                cardToDeal.setVisible(true);
            }
            event.consume();
        });

        SequentialTransition st = new SequentialTransition();
        ParallelTransition pt = new ParallelTransition();
        TranslateTransition tt = new TranslateTransition(Duration.millis(350), cardToDeal);
        tt.setToY(0);

        RotateTransition rt = new RotateTransition(Duration.millis(350), cardToDeal);
        rt.setByAngle(360 * 3 + 90);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(350), cardToDeal);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> cardToDeal.setFaceUp(true));

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), cardToDeal);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        if (p.getType() == PieceType.BLUE) {
            tt.setToX(BLUE_HAND_X);
            pt.setOnFinished(e -> {
                //getChildren().remove(cardToDeal);
                cardToDeal.setTranslateX(0);
                player1Hand.getChildren().add(cardToDeal);
            });
        } else {
            tt.setToX(GREEN_HAND_X);
            pt.setOnFinished(e -> {
                //getChildren().remove(cardToDeal);
                cardToDeal.setTranslateX(0);
                player2Hand.getChildren().add(cardToDeal);
            });
        }

        pt.getChildren().addAll(tt, rt, fadeOut);
        st.getChildren().addAll(pt, fadeIn);
        return st;
    }

    public void checkBoard(Node n) {
        Integer row = BoardPane.getRowIndex(n);
        Integer col = BoardPane.getColumnIndex(n);

        int rowIndex = row == null ? 0 : row.intValue();
        int colIndex = col == null ? 0 : col.intValue();

        if (checkRow(row) || checkCol(col)) {
            displayWinner();
        }
    }

    private void displayWinner() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Game Over!");
        alert.setHeaderText(null);

        if (bluePlayerTurn) {
            alert.setContentText("Green won the game!");
        } else {
            alert.setContentText("Blue won the game!");
        }

        ButtonType playAgainButton = new ButtonType("Play again?");

        alert.getButtonTypes().setAll(playAgainButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == playAgainButton) {
            getChildren().clear();
            this.initialize();
        }
    }

    public boolean isBluesTurn() {
        return bluePlayerTurn;
    }

    private boolean checkRow(int r) {
        int cnt = 0;

        for (int i = 0; i < 10; i++) {

            Tile thisTile = boardLayout.getTile(r, i);

            if (!thisTile.hasPiece()) {
                cnt = 0;
                continue;
            }

            PieceType thisType = (thisTile.getPiece()).getType();

            if (!bluePlayerTurn) {
                if (thisType.equals(PieceType.GREEN) || thisType.equals(PieceType.BOTH)) {
                    cnt++;
                } else {
                    cnt = 0;
                }
            } else {
                if (thisType.equals(PieceType.BLUE) || thisType.equals(PieceType.BOTH)) {
                    cnt++;
                } else {
                    cnt = 0;
                }
            }

            if (cnt == 5) {
                break;
            }
        }
        return cnt >= 5;
    }

    private boolean checkCol(int c) {
        int cnt = 0;

        for (int i = 0; i < 10; i++) {

            Tile thisTile = boardLayout.getTile(i, c);

            if (!thisTile.hasPiece()) {
                cnt = 0;
                continue;
            }

            PieceType thisType = (thisTile.getPiece()).getType();

            if (!bluePlayerTurn) {
                if (thisType.equals(PieceType.GREEN) || thisType.equals(PieceType.BOTH)) {
                    cnt++;
                } else {
                    cnt = 0;
                }
            } else {
                if (thisType.equals(PieceType.BLUE) || thisType.equals(PieceType.BOTH)) {
                    cnt++;
                } else {
                    cnt = 0;
                }
            }

            if (cnt == 5) {
                break;
            }
        }
        return cnt >= 5;
    }
}
