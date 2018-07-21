package cs3230_finalproject;

import java.util.ArrayList;
import java.util.Objects;

public abstract class GroupOfCards {

    protected ArrayList<Card> cards;

    public GroupOfCards()
    {
	cards = new ArrayList<>();
    }
    
   @Override 
   public String toString()
    {
	StringBuilder sb = new StringBuilder();
	
	for ( Card card : cards )
	{
	    sb.append(card.toString());
	    sb.append("\n");
	}
	
	return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
	if ( o instanceof GroupOfCards )
	{
	    GroupOfCards otherCards = (GroupOfCards) o;
	    
	    for ( Card card : cards )
	    {
		for ( Card other : otherCards.cards)
		{
		    if ( card.equals(other))
		    {
			continue;
		    }
		    return false;
		}
	    }
	}else{
	    return false;
	}
	
	return true;
    }

    @Override
    public int hashCode()
    {
	int hash = 7;
	hash = 47 * hash + Objects.hashCode(this.cards);
	return hash;
    }

    public int getSize()
    {
	return cards.size();
    }
    
    public void setFaceUp(boolean faceUp){
	for ( Card card : cards )
	{
	    card.setFaceUp(faceUp);
	}
    }
}
