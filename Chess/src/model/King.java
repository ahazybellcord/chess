package model;

import java.awt.Point;

public class King extends Piece{
	
	public King(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2654"; }
		else { return "\u265a" ; }
	}
	

}

