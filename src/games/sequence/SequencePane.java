package games.sequence;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    
    public void dealCards() {
        player1Hand = new VBox();
        player1Hand.setAlignment(Pos.CENTER_LEFT);
        
        player2Hand = new VBox();
        player2Hand.setAlignment(Pos.CENTER_RIGHT);
        
        getChildren().addAll(player1Hand, player2Hand);
        
        SequentialTransition st = new SequentialTransition();
//        for (int i = 0; i < 14; i++) {
//            Card cardToDeal = deck.dealCard();
//            cardToDeal.setFaceUp(true);
//            if (i % 2 == 0) {
//                bluePlayer.addCard(cardToDeal);
//                //getChildren().remove(cardToDeal);
//                player1Hand.getChildren().add(cardToDeal);
//                player1Hand.getChildren().add(new Card(Suit.CLUBS, Rank.JACK));
//            } else {
//                greenPlayer.addCard(cardToDeal);
//                getChildren().remove(cardToDeal);
//                player2Hand.getChildren().add(cardToDeal);
//            }
//            //st.getChildren().add(st)
//            
//        }
        System.out.println(deck.cards.size());
        Card c = deck.dealCard();
        c.setFaceUp(true);
        getChildren().remove(c);
        c.setRotate(90);
        player1Hand.getChildren().add(new Group(c));
        
        c = deck.dealCard();
        c.setFaceUp(true);
        getChildren().remove(c);
        c.setRotate(90);
        player1Hand.getChildren().add(c);
    }
}
