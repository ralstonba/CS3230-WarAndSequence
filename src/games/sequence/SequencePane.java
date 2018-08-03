package games.sequence;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Brendan Ralston
 */
public class SequencePane extends StackPane {

    private GridPane boardLayout;
    private Player bluePlayer;
    private Player greenPlayer;
    private Deck deck;
    private boolean bluePlayerTurn;

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
        getChildren().add(boardLayout);
        FadeTransition ft = new FadeTransition(Duration.millis(500), boardLayout);
        ft.setFromValue(0);
        ft.setToValue(1);
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
                tt.setToY(0);

                RotateTransition rt = new RotateTransition(Duration.millis(50), c);
                rt.setToAngle(0);

                pt2.getChildren().addAll(tt, rt);
                st.getChildren().add(pt2);
            }
            st.play();
        });
        pt.play();
    }
}
