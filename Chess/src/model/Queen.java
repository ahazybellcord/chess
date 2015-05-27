package model;

import java.awt.Point;

public class Queen extends Piece{
	
	public Queen(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2655"; }
		else { return "\u265b" ; }
	}

}

