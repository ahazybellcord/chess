package model;

import java.awt.Point;

public class Queen extends Piece{
	
	public Queen(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(9);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
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
		return this.getColor() ? "\u2655" : "\u265b";
	}
	
	@Override
	public String getSymbol() {
		return "Q";
	}

}

