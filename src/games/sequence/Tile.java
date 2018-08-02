package games.sequence;

import javafx.scene.image.Image;

/**
 *
 * @author Brendan Ralston 
 */
public class Tile extends Card{
    
    public Tile(Suit suit, Rank rank)
    {
	super(suit, rank);
	setImage(new Image("file:resources/images/" + suit + String.format("%02d", rank.ordinal() + 2) + ".bmp"));
	setRotate(90);
	//setOnMouseEntered(e->System.out.println("Mouse over: " + toString()));
    }

    public Tile(Card c){
	super(c.getSuit(), c.getRank());
	setImage(new Image("file:resources/images/" + suit + String.format("%02d", rank.ordinal() + 2) + ".bmp"));
	setRotate(90);
    }
}
