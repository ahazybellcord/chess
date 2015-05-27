package model;

import java.awt.Point;
import java.util.*;

public class Board {
	private HashMap<Point, Piece> _board;
	
	public Board() {
		_board = new HashMap<Point,Piece>();
	}

	public Piece getPiece(int x, int y) {
		Point location = new Point(x,y);
		if(_board.containsKey(location)) {
			return _board.get(location);
		}
		else return null;
	}
	public void setPiece(Piece piece, int x, int y) {
		_board.put(new Point(x,y), piece);
		if(piece!=null){
			System.out.println("Set location to ("+x+","+y+")");
			piece.setLocation(x,y);
		}
	}
	
	public Piece removePiece(Point _location) {
		Piece piece = _board.get(_location);
		setPiece(null, piece.x(), piece.y());
		return piece;
		
	}
	public HashMap<Point, Piece> getBoard() {
		return _board;
	}
	
	public String getChessCoordinate(Point location) {
		return ""+new String("abcdefgh").charAt((int)location.getX())+(location.getY()+1);
	}


}
