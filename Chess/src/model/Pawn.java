package model;

import java.awt.Point;

public class Pawn extends Piece{
	
	public Pawn(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2659"; }
		else { return "\u265f" ; }
	}
	

}

