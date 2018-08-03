package games.sequence;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Brendan Ralston
 */
public class SequencePane extends StackPane {

    private GridPane boardLayout;
    private Player bluePlayer;
    private Player greenPlayer;
    private Deck deck;

    public void initilize()
    {
	boardLayout = new BoardPane();
        bluePlayer = new Player(PieceType.BLUE);
        greenPlayer = new Player(PieceType.GREEN);
	
	//boardLayout.relocate(getWidth()/2, getHeight()/2);
	getChildren().add(boardLayout);
    }
}
