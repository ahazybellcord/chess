package model;

import java.awt.Point;

public class Queen extends Piece{
	
	public Queen(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	@Override
	public void setPossibleMoves() {
		Bishop b = new Bishop(this.getColor(), super.getGame(),this.getLocation());
		Rook r = new Rook(this.getColor(), super.getGame(),this.getLocation());
		b.setPossibleMoves();
		r.setPossibleMoves();
		for(Point p : b.getPossibleMoves()) {
			this.getPossibleMoves().add(p);
		}
		for(Point p : r.getPossibleMoves()) {
			this.getPossibleMoves().add(p);
		}
	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2655"; }
		else { return "\u265b" ; }
	}

}

