package games.sequence;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ralst
 */
public class Piece extends Circle {
    
    private final PieceType type;
    
    public Piece(PieceType type) {
        this.type = type;
        
        if (!type.equals(PieceType.BOTH)) {
            setRadius(30);
            setFill(type == PieceType.BLUE ? Color.BLUE : Color.GREEN);
            setStroke(Color.BLACK);
        }else{
            
        }
    }
    
    public PieceType getType() {
        return type;
    }
}
