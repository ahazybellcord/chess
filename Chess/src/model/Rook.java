package model;

import java.awt.Point;

public class Rook extends Piece{
	
	public Rook(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	public void setPossibleMoves() {
		
		
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2656"; }
		else { return "\u265c" ; }
	}
	

}

