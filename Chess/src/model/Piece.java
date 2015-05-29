package model;

import java.awt.Point;
import java.util.ArrayList;

public class Piece {
	private boolean _color;
	private Game _game;
	private Point _location;
	private ArrayList<Point> _possibleMoves;
	
	public Piece(boolean color, Game game, Point location) {
		_color = color;
		_game = game;
		_location = location;
		_possibleMoves = new ArrayList<Point>();
	}
	
	public boolean moveIsValid(Point location) {
		for(Point point : _possibleMoves) {
			if(point.equals(location)) { return true; }
		}
		return false;
	}
	
	public void setPossibleMoves(){
		
	}
	
	public boolean getColor() {
		return _color;
	}
	
	public Point getLocation() {
		return _location;
	}
	
	public ArrayList<Point> getPossibleMoves() {
		return _possibleMoves;
	}
	
	public String getUnicode() {
		return "";
	}
	
	public Game getGame() {
		return _game;
	}

	public boolean checkMove(int x, int y) {
		Board b = this.getGame().getBoard();
		if(b.isEmpty(x, y)){
			 return true;
		}
		if(b.getPiece(x, y)!=null) {
			if(b.getPiece(x, y).getColor()!=this.getColor()) {
				 return true;
			}
		}
		return false;
	}
	

}
