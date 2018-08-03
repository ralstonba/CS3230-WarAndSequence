package games.sequence;

import java.util.Collections;

public class Deck extends GroupOfCards {

    public Deck() {
        super();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                if (!(suit == Suit.WILD || rank == Rank.WILD)) {
                    Card c = new Card(suit, rank);
                    super.cards.add(c);
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(super.cards);
    }

    public Card dealCard() throws IndexOutOfBoundsException {
        return super.cards.remove(super.cards.size() - 1);
    }
}
