package games.sequence;

import java.util.Stack;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Brendan Ralston
 */
public class BoardPane extends GridPane {

    Stack<Card> boardCards = new Stack<>();

    public BoardPane()
    {
	for ( int i = 0; i < 2; i++ )
	{
	    for ( Suit suit : Suit.values() )
	    {
		for ( Rank rank : Rank.values() )
		{
		    if ( rank == Rank.WILD || suit == Suit.WILD || rank == Rank.JACK )
		    {
			continue;
		    }
		    boardCards.add(new Card(suit, rank));
		}
	    }
	}

	for ( int i = 0; i < 10; i++ )
	{
	    for ( int j = 0; j < 10; j++ )
	    {
		Tile tile;
		if ( (i == 0 && j == 0) || (i == 9 && j == 0) || (i == 0 && j == 9) || (i == 9 && j == 9) )
		{
		    tile = new Tile(Suit.WILD, Rank.WILD);
		} else
		{
		    Card cardToAdd = boardCards.pop();
		    tile = new Tile(cardToAdd);
		}
		add(new Group(tile), i, j);
	    }
	}
	setAlignment(Pos.CENTER);
	setHgap(5);
	setVgap(5);
    }
}
