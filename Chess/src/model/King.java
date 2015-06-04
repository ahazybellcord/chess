package model;

import java.awt.Point;

public class King extends Piece{
	
	public King(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		Queen myQueen = new Queen(this.getColor(), this.getGame(), this.getLocation());
		myQueen.setPossibleMoves();
		for(Point p : myQueen.getPossibleMoves()){
			if(Math.abs(p.x-this.getLocation().x)<2 && Math.abs(p.y-this.getLocation().y)<2){
				this.getPossibleMoves().add(p);
			}
		}
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2654"; }
		else { return "\u265a" ; }
	}
	
	@Override
	public String getSymbol() {
		return "K";
	}
	
}

