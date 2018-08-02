package games.sequence;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Brendan Ralston
 */
public class SequencePane extends StackPane {

    private GridPane boardLayout;

    public void initilize()
    {
	boardLayout = new BoardPane();
	
	//boardLayout.relocate(getWidth()/2, getHeight()/2);
	getChildren().add(boardLayout);
    }
}
