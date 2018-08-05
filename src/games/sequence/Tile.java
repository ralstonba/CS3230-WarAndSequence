package games.sequence;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Brendan Ralston
 */
public class Tile extends StackPane {

    private Piece piece = null;
    private Card card = null;

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

    public void addPiece(PieceType p) {
        piece = new Piece(p);
        getChildren().add(piece);
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece() {
        getChildren().remove(this.piece);
        piece = null;
    }
    
    public boolean hasPiece(){
        return piece != null;
    }
    
    public Card getCard(){
        return this.card;
    }

    @Override
    public String toString() {
        return "Tile{" + "piece=" + piece + ", card=" + card.toString() + '}';
    }
}
