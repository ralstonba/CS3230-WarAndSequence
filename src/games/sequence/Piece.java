/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.sequence;

import javafx.scene.image.ImageView;

/**
 *
 * @author ralst
 */
public class Piece extends ImageView{
    private PieceType type;
    private int posX = -1, posY = -1;
    
    public Piece(PieceType type){
	this.type = type;
    }

    public PieceType getType()
    {
	return type;
    }

    public void movePiece(int x, int y){
	posX = x;
	posY = y;
    }
}
