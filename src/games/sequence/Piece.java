package games.sequence;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author ralst
 */
public class Piece extends StackPane {
    
    private final PieceType type;
    
    public Piece(PieceType type) {
        this.type = type;
        
        if (!type.equals(PieceType.BOTH)) {
            
            Ellipse bg = new Ellipse(25, 30);
            bg.setFill(Color.BLACK);
            bg.setStroke(Color.BLACK);
            bg.setTranslateX(5);
            
            Ellipse fg = new Ellipse(25, 30);
            fg.setFill(type == PieceType.BLUE ? Color.BLUE : Color.GREEN);
            fg.setStroke(Color.BLACK);
            
            getChildren().addAll(bg, fg);
        } else {
            
        }
    }
    
    public PieceType getType() {
        return type;
    }
}
