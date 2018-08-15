package games.sequence;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView implements Comparable<Card> {

    protected final Suit suit;
    protected final Rank rank;
    protected boolean faceUp = false;

    public Card(Suit suit, Rank rank)
    {
	this.suit = suit;
	this.rank = rank;
	setImage(new Image("file:resources/images/back.bmp"));
    }

    public Suit getSuit()
    {
	return suit;
    }

    public Rank getRank()
    {
	return rank;
    }

    public boolean isFaceUp()
    {
	return faceUp;
    }

    public void setFaceUp(boolean faceUp)
    {
	this.faceUp = faceUp;
	if ( faceUp )
	{
	    setImage(new Image("file:resources/images/" + suit + String.format("%02d", rank.ordinal() + 2) + ".bmp"));
	} else
	{
	    setImage(new Image("file:resources/images/back.bmp"));
	}
    }

    @Override
    public String toString()
    {
	return rank + " of " + suit; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode()
    {
	int hash = 3;
	hash = 17 * hash + Objects.hashCode(this.suit);
	hash = 17 * hash + Objects.hashCode(this.rank);
	return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
	if ( this == obj )
	{
	    return true;
	}
	if ( obj == null )
	{
	    return false;
	}
	if ( getClass() != obj.getClass() )
	{
	    return false;
	}
	final Card other = (Card) obj;
	if ( this.suit != other.suit )
	{
	    return false;
	}
	if ( this.rank != other.rank )
	{
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(Card t)
    {
	if ( suit == t.suit && rank == t.rank )
	{
	    return 0;
	} else if ( suit.ordinal() < t.suit.ordinal() || rank.ordinal() < t.rank.ordinal() )
	{
	    return -1;
	} else
	{
	    return 1;
	}
    }
    
    public double getCenterX(){
	return getImage().getWidth()/2;
    }
    
    public double getCenterY(){
	return getImage().getHeight()/2;
    }
}