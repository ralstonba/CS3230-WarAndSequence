package games.sequence;

import java.util.Stack;

/**
 *
 * @author ralst
 */
public class Player {
    public Stack<Piece> piecePile;
    private Hand playerHand;
    private PieceType type;

    public Player(PieceType type) {
        piecePile = new Stack<>();
        playerHand = new Hand();
        this.type = type;
        for (int i = 0; i < 50; i++) {
            piecePile.add(new Piece(type));
        }
    }
    
    public void addCard(Card c){
        playerHand.addCard(c);
    }    
    
    public Card playCard(){
        return playerHand.play();
    }
    
    public PieceType getType(){
        return type;
    }
}
