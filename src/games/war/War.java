package games.war;

/**
 *
 * @author ralst
 */
public class War {

    Deck deck;
    Hand player1Hand;
    Hand player2Hand;
    Hand player1Pile;
    Hand player2Pile;
    Hand warPile;

    public void initilize()
    {
	deck = new Deck();
	player1Hand = new Hand("Player 1");
	player2Hand = new Hand("Player 2");
	player1Pile = new Hand("Player 1's Pile");
	player2Pile = new Hand("Player 2's Pile");
	warPile = new Hand("War Pile");
    }

    public void shuffle()
    {
	System.out.println("The deck prior to shuffling:\n" + deck.toString());
	deck.shuffle();
	System.out.println("The deck after shuffling:\n" + deck.toString());
    }

    public void dealCards()
    {
	for ( int i = 0; i < deck.getSize(); i++ )
	{
	    if ( i % 2 == 0 )
	    {
		player1Hand.addCard(deck.dealCard());
	    } else
	    {
		player2Hand.addCard(deck.dealCard());
	    }
	}
	System.out.println("Player 1's hand is:\n" + player1Hand.toString());
	System.out.println("Player 2's hand is:\n" + player2Hand.toString());
    }
    

}
