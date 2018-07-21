package cs3230_finalproject;

public class War implements Game {

    Deck deck;
    Hand player1;
    Hand player2;
    Hand p1Pile;
    Hand p2Pile;
    Hand pile;

    final int MAX_ROUNDS = 2000;

    @Override
    public void initialize()
    {
	deck = new Deck();
	player1 = new Hand("Player 1");
	player2 = new Hand("Player 2");
	p1Pile = new Hand("Player 1's Pile");
	p2Pile = new Hand("Player 2's Pile");
	pile = new Hand("Pile");

	System.out.println("The deck prior to shuffling:\n" + deck.toString());

	deck.shuffle();

	System.out.println("The deck after shuffling:\n" + deck.toString());

	for ( int i = 0; i < 52; i++ )
	{
	    if ( i % 2 == 0 )
	    {
		player1.addCard(deck.dealCard());
	    } else
	    {
		player2.addCard(deck.dealCard());
	    }
	}

	System.out.println("Player 1's hand is:\n" + player1.toString());
	System.out.println("Player 2's hand is:\n" + player2.toString());
    }

    @Override
    public void play()
    {
	for ( int i = 0; i < MAX_ROUNDS; i++ )
	{
	    Card p1Card;
	    try
	    {
		p1Card = playCard(player1, p1Pile, true);
	    } catch (Exception e)
	    {
		System.out.println("Player 1 ran out of cards");
		return;
	    }

	    Card p2Card;
	    try
	    {
		p2Card = playCard(player2, p2Pile, true);
	    } catch (Exception e)
	    {
		System.out.println("Player 2 ran out of cards");
		return;
	    }

	    while ( p1Card.getRank().ordinal() == p2Card.getRank().ordinal() )
	    {
		System.out.println("WAR!");

		pile.addCard(p1Card);
		pile.addCard(p2Card);

		for ( int j = 0; j < 3; j++ )
		{
		    try
		    {
			pile.addCard(playCard(player1, p1Pile, false));
		    } catch (Exception e)
		    {
			System.out.println("Player 1 ran out of cards!");
			return;
		    }
		    try
		    {
			pile.addCard(playCard(player2, p2Pile, false));
		    } catch (Exception e)
		    {
			System.out.println("Player 2 ran out of cards!");
			return;
		    }
		}

		try
		{
		    p1Card = playCard(player1, p1Pile, true);
		} catch (Exception e)
		{
		    System.out.println("Player 1 ran out of cards!");
		    return;
		}

		try
		{
		    p2Card = playCard(player2, p2Pile, true);
		} catch (Exception e)
		{
		    System.out.println("Player 2 ran out of cards!");
		    return;
		}
	    }

	    p1Card.setFaceUp(false);
	    p2Card.setFaceUp(false);

	    if ( p1Card.getRank().ordinal() > p2Card.getRank().ordinal() )
	    {
		System.out.println("Player1 wins!");
		p1Pile.addPile(pile);
		p1Pile.addCard(p1Card);
		p1Pile.addCard(p2Card);
		System.out.println("Player1's pile has:\n" + p1Pile.toString());
	    } else
	    {
		System.out.println("Player2 wins!");
		p2Pile.addPile(pile);
		p2Pile.addCard(p1Card);
		p2Pile.addCard(p2Card);
		System.out.println("Player2's pile has:\n" + p2Pile.toString());
	    }
	}
	System.out.println("Reached max number of rounds");
    }

    private Card playCard(Hand playerHand, Hand playerPile, boolean faceUp) throws Exception
    {
	Card temp;
	while ( true )
	{
	    try
	    {
		temp = playerHand.play();
		if ( faceUp )
		{
		    temp.setFaceUp(faceUp);
		    System.out.println(playerHand.getName() + " played " + temp.toString());
		}
		break;
	    } catch (IndexOutOfBoundsException e)
	    {
		if ( playerPile.getSize() == 0 )
		{
		    throw new Exception(playerHand.getName() + " is out of cards");
		}
		playerHand.addPile(playerPile);
		System.out.println(playerHand.getName() + "'s pile was added to their hand");
	    }
	}
	return temp;
    }

    @Override
    public void displayWinner()
    {
	player1.addPile(p1Pile);
	player2.addPile(p2Pile);

	if ( player1.getSize() > player2.getSize() )
	{
	    System.out.println("Player 1 Wins!");
	} else if ( player1.getSize() < player2.getSize() )
	{
	    System.out.println("Player 2 Wins!");
	} else
	{
	    System.out.println("Player 1 and Player 2 TIE!");
	}
    }

}
