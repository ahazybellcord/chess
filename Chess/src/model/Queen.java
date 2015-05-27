package model;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;

public class Queen extends Piece{
	private String _name;
	
	public Queen(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "queen";
	}
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		Bishop b = new Bishop(this.getColor(),this.getGame(),this.x(),this.y());
//		b.setLocation(this.x(),this.y());
		b.setPossibleMoves();
		Rook r = new Rook(this.getColor(),this.getGame(),this.x(),this.y());
//		r.setLocation(this.x(),this.y());
		r.setPossibleMoves();
		new Queen(this.getColor(),this.getGame(),this.x(),this.y());
		HashSet<Point> queenList = new HashSet<Point>();
		for(Point move : b.getPossibleMoves()) {
			queenList.add(move); }
		for(Point move : r.getPossibleMoves()) {
			queenList.add(move);
		}
		this.setPossibleMoves(queenList);
		
	}

}
