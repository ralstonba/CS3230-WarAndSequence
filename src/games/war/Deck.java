package games.war;

import java.util.Collections;

/**
 *
 * @author ralst
 */
public class Deck extends GroupOfCards {

    public Deck()
    {
	for ( Suit suit : Suit.values() )
	{
	    for ( Rank rank : Rank.values() )
	    {
		Card c = new Card(suit, rank);
		cards.add(c);
	    }
	}
    }
    
    public void shuffle(){
	Collections.shuffle(cards);
    }
    
    public Card dealCard() throws IndexOutOfBoundsException{
	return cards.remove(cards.size()-1);
    }

}
