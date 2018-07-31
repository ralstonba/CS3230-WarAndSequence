package games.war;


import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView implements Comparable<Card> {

    private final Suit suit;
    private final Rank rank;
    private boolean faceUp = false;

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
	return rank + " of " + suit + (faceUp ? " fu" : " fd"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object o)
    {
	if ( o instanceof Card )
	{
	    Card other = (Card) o;

	    return suit == other.suit && rank == other.rank;

	} else
	{
	    return false;
	}
    }

    @Override
    public int hashCode()
    {
	int hash = 3;
	hash = 53 * hash + Objects.hashCode(this.suit);
	hash = 53 * hash + Objects.hashCode(this.rank);
	return hash;
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
