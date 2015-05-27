package model;

import java.awt.Point;

public class Knight extends Piece{
	
	public Knight(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2658"; }
		else { return "\u265e" ; }
	}
	

}
