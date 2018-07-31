package games.war;

import games.MainApp;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ralst
 */
public class WarGamePane extends Pane {

    Deck deck;
    Hand player1;
    Hand player2;
    Hand p1Pile;
    Hand p2Pile;
    Hand pile;
    Card p1Card;
    Card p2Card;
    public boolean cardsHaveBeenDealt;
    int p1HandX = -240;
    int p2HandX = 240;
    int p1PlayedX = 400;
    int p2PlayedX = 250;

    public WarGamePane()
    {
	setPrefSize(640, 480);
	//setStyle("-fx-background-color: rgba(0,100,0,1)");
    }

    public void initialize()
    {
	deck = new Deck();
	player1 = new Hand("Player 1");
	player2 = new Hand("Player 2");
	p1Pile = new Hand("Player 1's Pile");
	p2Pile = new Hand("Player 2's Pile");
	pile = new Hand("Pile");
	cardsHaveBeenDealt = false;

	for ( Card card : deck.cards )
	{
	    getChildren().add(card);
	    moveCard(card, getWidth() / 2, getHeight() / 2);
	}

	shuffle();
    }

    public void shuffle()
    {
	System.out.println("The deck prior to shuffling:\n" + deck.toString());
	deck.shuffle();
	System.out.println("The deck after shuffling:\n" + deck.toString());
    }

    public void dealCards()
    {
	Queue<TranslateTransition> dealTransitions = new LinkedList<>();
	for ( int i = 0; i < 52; i++ )
	{
	    Card cardToDeal = deck.dealCard();
	    TranslateTransition tt = new TranslateTransition(Duration.millis(50), cardToDeal);
	    //tt.setFromX(-110);
	    if ( i % 2 == 0 )
	    {
		tt.setToX(p1HandX - cardToDeal.getImage().getWidth() / 2);
		player1.addCard(cardToDeal);
	    } else
	    {
		tt.setToX(p2HandX + cardToDeal.getImage().getWidth() / 2);
		player2.addCard(cardToDeal);
	    }
	    dealTransitions.add(tt);
	}
	SequentialTransition s = new SequentialTransition();
	s.getChildren().addAll(dealTransitions);
	s.play();
	System.out.println("Player 1's hand is:\n" + player1.toString());
	System.out.println("Player 2's hand is:\n" + player2.toString());
	cardsHaveBeenDealt = true;
    }

    public void playCards()
    {
	p1Card = playCard(player1, p1Pile);
	p2Card = playCard(player2, p2Pile);

	p1Card.toFront();
	p2Card.toFront();

	ScaleTransition hideBackP1 = new ScaleTransition(Duration.millis(500), p1Card);
	hideBackP1.setFromX(1);
	hideBackP1.setToX(0);

	ScaleTransition showFrontP1 = new ScaleTransition(Duration.millis(500), p1Card);
	showFrontP1.setFromX(0);
	showFrontP1.setToX(1);

	TranslateTransition ttP1 = new TranslateTransition(Duration.millis(300), p1Card);
	ttP1.setByX(100);
	ttP1.setOnFinished(e
		-> 
		{
		    hideBackP1.play();
		    p1Card.setScaleX(0);
		    p1Card.setFaceUp(true);
		    showFrontP1.play();
	});

	ScaleTransition hideBackP2 = new ScaleTransition(Duration.millis(500), p2Card);
	hideBackP2.setFromX(1);
	hideBackP2.setToX(0);

	ScaleTransition showFrontP2 = new ScaleTransition(Duration.millis(500), p2Card);
	showFrontP2.setFromX(0);
	showFrontP2.setToX(1);

	TranslateTransition ttP2 = new TranslateTransition(Duration.millis(300), p2Card);
	ttP2.setByX(-100);
	ttP2.setOnFinished(e
		-> 
		{
		    hideBackP2.play();
		    p2Card.setScaleX(0);
		    p2Card.setFaceUp(true);
		    showFrontP2.play();
	});

	ParallelTransition flipCards = new ParallelTransition();
	flipCards.getChildren().addAll(ttP1, ttP2);
	flipCards.play();

	PauseTransition pt = new PauseTransition(Duration.millis(1000));
	pt.setOnFinished(e -> compareCards());
	pt.play();
    }

    public void compareCards()
    {
	if ( p1Card.getRank().ordinal() == p2Card.getRank().ordinal() )
	{
	    TranslateTransition moveP1CardTrans = new TranslateTransition(Duration.millis(300), p1Card);
	    moveP1CardTrans.setFromX(p1HandX - p1Card.getCenterX() + 100);
	    moveP1CardTrans.setByX(165);

	    TranslateTransition moveP2CardTrans = new TranslateTransition(Duration.millis(300), p2Card);
	    moveP2CardTrans.setFromX(p2HandX + p2Card.getCenterX() - 100);
	    moveP2CardTrans.setByX(-165);

	    ParallelTransition pt = new ParallelTransition();
	    pt.getChildren().addAll(moveP1CardTrans, moveP2CardTrans);
	    pt.setOnFinished(e
		    -> 
		    {
			System.out.println("WAR!");
			pile.addCard(p1Card);
			pile.addCard(p2Card);

			SequentialTransition stDiscard = new SequentialTransition();

			ParallelTransition ptDiscard = null;
			for ( int i = 0; i < 3; i++ )
			{
			    Card tempP1 = playCard(player1, p1Pile);
			    Card tempP2 = playCard(player2, p2Pile);

			    tempP1.toFront();
			    tempP2.toFront();

			    TranslateTransition ttP1 = new TranslateTransition(Duration.millis(300), tempP1);
			    ttP1.setFromX(p1HandX - tempP1.getCenterX());
			    ttP1.setByX(275);

			    TranslateTransition ttP2 = new TranslateTransition(Duration.millis(300), tempP2);
			    ttP2.setFromX(p2HandX + p2Card.getCenterX());
			    ttP2.setByX(-275);

			    ptDiscard = new ParallelTransition();
			    ptDiscard.getChildren().addAll(ttP1, ttP2);
			    ptDiscard.setOnFinished(event
				    -> 
				    {
					pile.addCard(tempP1);
					pile.addCard(tempP2);
			    });
			    stDiscard.getChildren().add(ptDiscard);
			}
			stDiscard.setOnFinished(event -> playCards());
			stDiscard.play();
	    });
	    pt.play();
	} else if ( p1Card.getRank().ordinal() > p2Card.getRank().ordinal() )
	{
	    System.out.println("Player1 wins!");
	    moveCards(1);
	    p1Pile.addPile(pile);
	    p1Pile.addCard(p2Card);
	    p1Pile.addCard(p1Card);
	    System.out.println("Player1's pile has:\n" + p1Pile.toString());

	} else
	{
	    System.out.println("Player2 wins!");
	    moveCards(2);
	    p2Pile.addPile(pile);
	    p2Pile.addCard(p1Card);
	    p2Pile.addCard(p2Card);
	    System.out.println("Player2's pile has:\n" + p2Pile.toString());

	}
    }

    public void moveCards(int i)
    {
	System.out.println("Should be moving cards to :" + i);
	TranslateTransition moveP1CardTrans = new TranslateTransition(Duration.millis(300), p1Card);
	moveP1CardTrans.setFromX(p1HandX - p1Card.getCenterX() + 100);
	moveP1CardTrans.setFromY(p1Card.getY());

	TranslateTransition moveP2CardTrans = new TranslateTransition(Duration.millis(300), p2Card);
	moveP2CardTrans.setFromX(p2HandX + p2Card.getCenterX() - 100);
	moveP2CardTrans.setFromY(p2Card.getY());

	TranslateTransition moveWarPileTrans = new TranslateTransition(Duration.millis(300));

	ScaleTransition hideFrontP1 = new ScaleTransition(Duration.millis(300), p1Card);
	hideFrontP1.setFromX(1);
	hideFrontP1.setToX(0);

	ScaleTransition showBackP1 = new ScaleTransition(Duration.millis(300), p1Card);
	showBackP1.setFromX(0);
	showBackP1.setToX(1);

	ScaleTransition hideFrontP2 = new ScaleTransition(Duration.millis(300), p2Card);
	hideFrontP2.setFromX(1);
	hideFrontP2.setToX(0);

	ScaleTransition showBackP2 = new ScaleTransition(Duration.millis(300), p2Card);
	showBackP2.setFromX(0);
	showBackP2.setToX(1);

	Queue<TranslateTransition> warPileTransitions = new LinkedList<>();
	for ( Card c : pile.cards )
	{
	    TranslateTransition tt = new TranslateTransition(Duration.millis(300), c);
	    warPileTransitions.add(tt);
	}

	ParallelTransition ptMoveCards = new ParallelTransition();
	switch ( i )
	{
	    case 1:
		p1Card.toFront();
		moveP1CardTrans.setToX(p1HandX - p1Card.getCenterX());
		moveP1CardTrans.setToY(100);
		moveP2CardTrans.setToX(p1HandX - p1Card.getCenterX());
		moveP2CardTrans.setToY(100);
		for ( TranslateTransition wpt : warPileTransitions )
		{
		    wpt.setToX(p1HandX - p1Card.getCenterX());
		    wpt.setToY(100);
		}
		break;
	    case 2:
		p2Card.toFront();
		moveP1CardTrans.setToX(p2HandX + p2Card.getCenterX());
		moveP1CardTrans.setToY(100);
		moveP2CardTrans.setToX(p2HandX + p2Card.getCenterX());
		moveP2CardTrans.setToY(100);
		for ( TranslateTransition wpt : warPileTransitions )
		{
		    wpt.setToX(p2HandX + p2Card.getCenterX());
		    wpt.setToY(100);
		}
		break;
	    default:
		System.out.println("you broke shit");
	}

	ptMoveCards.getChildren().addAll(moveP1CardTrans, moveP2CardTrans);
	ptMoveCards.getChildren().addAll(warPileTransitions);
	ptMoveCards.play();
    }

    private void moveCard(Card c, double x, double y)
    {
	c.relocate(x - c.getCenterX(), y - c.getCenterY());
    }

    private Card playCard(Hand playerHand, Hand playerPile)
    {
	if ( playerHand.getSize() < 1 )
	{
	    if ( playerPile.getSize() < 1 )
	    {
		displayWinner();
		return null;
	    } else
	    {
		//playerHand.addPile(playerPile);

		ParallelTransition movePileTransition = new ParallelTransition();
		for ( Card c : playerPile.cards )
		{
		    c.setFaceUp(false);
		    TranslateTransition ttPile = new TranslateTransition(Duration.millis(300), c);
		    if ( playerHand.getName().equals("Player 1") )
		    {
			ttPile.setFromX(p1HandX - c.getCenterX());
			ttPile.setFromY(100);

			ttPile.setToY(0);
		    } else
		    {
			ttPile.setFromX(p2HandX + c.getCenterX());
			ttPile.setFromY(100);

			ttPile.setToY(0);
		    }
		    movePileTransition.getChildren().add(ttPile);
		}
		//movePileTransition.setOnFinished(e -> );
		playerHand.addPile(playerPile);
		movePileTransition.play();
		return playerHand.play();
	    }
	} else
	{
	    return playerHand.play();
	}
    }

    public void movePile(int player)
    {
	Queue<TranslateTransition> dealTransitions = new LinkedList<>();
	SequentialTransition s = new SequentialTransition();
	switch ( player )
	{
	    case 1:
		for ( Card c : p1Pile.cards )
		{
		    TranslateTransition ttPile = new TranslateTransition(Duration.millis(50), c);
		    ttPile.setFromX(p1PlayedX);
		    ttPile.setToX(p1HandX);
		    dealTransitions.add(ttPile);
		    s.setOnFinished(e -> player1.addPile(p1Pile));
		}
		break;
	    case 2:
		for ( Card c : p2Pile.cards )
		{
		    TranslateTransition ttPile = new TranslateTransition(Duration.millis(50), c);
		    ttPile.setFromX(p2PlayedX);
		    ttPile.setToX(p2HandX);
		    dealTransitions.add(ttPile);
		    s.setOnFinished(e -> player2.addPile(p2Pile));
		}
		break;
	}
	s.getChildren().addAll(dealTransitions);
	s.play();
    }

    public void displayWinner()
    {
	Alert alert = new Alert(Alert.AlertType.NONE);
	alert.setTitle("Game Over!");
	alert.setHeaderText(null);
	if ( (player1.getSize() + p1Pile.getSize()) > (player2.getSize() + p2Pile.getSize()) )
	{
	    alert.setContentText("Player 1 won the game!");
	} else if ( (player1.getSize() + p1Pile.getSize()) < (player2.getSize() + p2Pile.getSize()) )
	{
	    alert.setContentText("Player 2 won the game!");
	} else
	{
	    alert.setContentText("The game ended in a tie!");
	}

	ButtonType playAgainButton = new ButtonType("Play again?");

	alert.getButtonTypes().setAll(playAgainButton);

	Optional<ButtonType> result = alert.showAndWait();

	if ( result.get() == playAgainButton )
	{
	    getChildren().clear();
	    this.initialize();
	}
    }
}
