package cs3230_finalproject;

public class Hand extends GroupOfCards {

    private String name;

    public Hand()
    {
	super();
	this.name = "Default Hand";
    }

    public Hand(String name)
    {
	super();
	this.name = name;
    }

    protected Card play()
    {
	return super.cards.remove(0);
    }

    protected void addPile(Hand pile)
    {
	super.cards.addAll(pile.cards);
	pile.cards.clear();
    }

    protected void addCard(Card card)
    {
	super.cards.add(card);
    }

//    @Override
//    public String toString()
//    {
//	ArrayList<Card> temp = new ArrayList<>();
//	for ( Card card : super.cards )
//	{
//	    temp.add(card);
//	}
//	Collections.sort(temp);
//	return temp.toString();
//    }
    public String getName()
    {
	return name;
    }
}
