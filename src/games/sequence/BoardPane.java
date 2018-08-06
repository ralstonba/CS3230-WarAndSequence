package games.sequence;

import java.util.Stack;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Brendan Ralston
 */
public class BoardPane extends GridPane {

    private SequencePane sp;
    Stack<Card> boardCards = new Stack<>();

    public BoardPane() {
        for (int i = 0; i < 2; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    if (rank == Rank.WILD || suit == Suit.WILD || rank == Rank.JACK) {
                        continue;
                    }
                    boardCards.add(new Card(suit, rank));
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile;
                if ((i == 0 && j == 0) || (i == 9 && j == 0) || (i == 0 && j == 9) || (i == 9 && j == 9)) {
                    tile = new Tile(Suit.WILD, Rank.WILD);
                } else {
                    Card cardToAdd = boardCards.pop();
                    tile = new Tile(cardToAdd);
                }
                add(new Group(tile), i, j);
            }
        }
        setAlignment(Pos.CENTER);
        setHgap(5);
        setVgap(5);

        getChildren().forEach(item -> {

            item.setOnDragOver((DragEvent event) -> {
                event.acceptTransferModes(TransferMode.MOVE);
            });

            item.setOnDragEntered((DragEvent event) -> {
                if (event.getGestureSource() != item) {
                    System.out.println("DragEntered on: " + ((Group) item).getChildren().get(0).toString());
                }
                event.consume();
            });

            item.setOnDragDropped((DragEvent event) -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                System.out.println("DragDropped on: " + item.toString());
                if (db.hasImage()) {
                    Group thisGroup = ((Group) item);
                    Tile thisTile = (Tile) thisGroup.getChildren().get(0);
                    Card tileCard = thisTile.getCard();
                    Card sourceCard = (Card) event.getGestureSource();
                    System.out.println("Dropped tileCard: " + tileCard.toString());
                    System.out.println("Source: " + event.getGestureSource().toString());
                    if (sourceCard.equals(tileCard)) {
                        if (!thisTile.hasPiece()) {
                            success = true;

                            thisTile.addPiece(sp.isBluesTurn() ? PieceType.BLUE : PieceType.GREEN);
                            sp.checkBoard(item);

                            System.out.println("Cards matched!");
                        }
                    } else if (sourceCard.getRank() == Rank.JACK) {
                        if (sourceCard.getSuit() == Suit.SPADES || sourceCard.getSuit() == Suit.CLUBS) {
                            if ((sp.isBluesTurn() && thisTile.getPiece().getType() == PieceType.GREEN) || (!sp.isBluesTurn() && thisTile.getPiece().getType() == PieceType.BLUE)) {
                                success = true;
                                thisTile.removePiece();
                            }
                        } else {
                            if (!thisTile.hasPiece()) {
                                success = true;
                                thisTile.addPiece(sp.isBluesTurn() ? PieceType.BLUE : PieceType.GREEN);
                                //SequencePane.checkBoard(item);
                            }
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });
        });
    }
    
    public void setSequencePane(SequencePane sp){
        this.sp = sp;
    }

    public Tile getTile(int r, int c) {
        for (Node n : getChildren()) {
            if (GridPane.getColumnIndex(n) == c && GridPane.getRowIndex(n) == r) {
                return (Tile) ((Group) n).getChildren().get(0);
            }
        }
        return null;
    }
}
