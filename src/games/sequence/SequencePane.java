package games.sequence;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private GridPane boardLayout;
    private Player bluePlayer;
    private Player greenPlayer;
    private Deck deck;
    private boolean bluePlayerTurn;
    private VBox player1Hand;
    private VBox player2Hand;

    public void initilize() {
        boardLayout = new BoardPane();
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
            System.out.println("Drag detected on: " + cardToDeal.toString());
            Dragboard db = cardToDeal.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(cardToDeal.getImage());
            db.setContent(content);
            event.consume();
        });
        
        cardToDeal.setOnDragDone((DragEvent event) -> {
            System.out.println("DragDone on: " + cardToDeal.toString());
            event.consume();
            //Remove card from hand, place in discard pile
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

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), cardToDeal);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        if (p.getType() == PieceType.BLUE) {
            tt.setToX(BLUE_HAND_X);
            pt.setOnFinished(e -> {
                getChildren().remove(cardToDeal);
                cardToDeal.setTranslateX(0);
                player1Hand.getChildren().add(cardToDeal);
            });
        } else {
            tt.setToX(GREEN_HAND_X);
            pt.setOnFinished(e -> {
                getChildren().remove(cardToDeal);
                cardToDeal.setTranslateX(0);
                player2Hand.getChildren().add(cardToDeal);
            });
        }

        pt.getChildren().addAll(tt, rt, fadeOut);
        st.getChildren().addAll(pt, fadeIn);
        return st;
    }
}
