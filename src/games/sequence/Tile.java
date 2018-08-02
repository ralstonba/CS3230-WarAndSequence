package games.sequence;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Brendan Ralston
 */
public class Tile extends StackPane {

    Piece piece = null;
    Card card = null;

    public Tile(Suit suit, Rank rank) {
        card = new Card(suit, rank);
        card.setFaceUp(true);
        setRotate(90);
        getChildren().add(card);
        //setOnMouseEntered(e->System.out.println("Mouse over: " + toString()));
    }

    public Tile(Card c) {
        this(c.getSuit(), c.getRank());
    }

    public void addPiece(Piece p) {
        piece = p;
        getChildren().add(p);
    }

    public void removePiece() {
        getChildren().remove(this.piece);
        piece = null;
    }
}
