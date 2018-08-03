package games.sequence;

import javafx.animation.FadeTransition;
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
    
    private void showBoard(){
        getChildren().add(boardLayout);
        FadeTransition ft = new FadeTransition(Duration.millis(500), boardLayout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}
