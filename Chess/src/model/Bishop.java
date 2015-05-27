package model;

import java.awt.Point;

public class Bishop extends Piece{
	
	public Bishop(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2657"; }
		else { return "\u265d" ; }
	}

	

}
