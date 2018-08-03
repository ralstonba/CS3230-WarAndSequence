package games.sequence;

import java.util.Stack;

/**
 *
 * @author ralst
 */
public class Player {
    Stack<Piece> piecePile;
    Hand playerHand;

    public Player(PieceType type) {
        piecePile = new Stack<>();
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
}
