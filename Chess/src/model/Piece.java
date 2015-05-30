package model;

import java.awt.Point;
import java.util.HashSet;

public class Piece {
	private boolean _color;
	private Game _game;
	private Point _location;
	private HashSet<Point> _possibleMoves;
	
	public Piece(boolean color, Game game, Point location) {
		_color = color;
		_game = game;
		_location = location;
		_possibleMoves = new HashSet<Point>();
	}
	
	public boolean moveIsValid(Point location) {
		for(Point point : _possibleMoves) {
			if(point.equals(location)) { return true; }
		}
		return false;
	}
	
	public void setPossibleMoves(){
		_possibleMoves = new HashSet<Point>();
	}
	
	public boolean getColor() {
		return _color;
	}
	
	public Point getLocation() {
		return _location;
	}
	
	public void setLocation(int x, int y) {
		_location = new Point(x,y);
	}
	
	public HashSet<Point> getPossibleMoves() {
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
		else return b.getPiece(x, y).getColor()!=this.getColor();
	}
	
	//checks for an opponent's piece on the board at (x,y)
	public boolean checkOpponent(int x, int y) {
		Board b = this.getGame().getBoard();
		if(!b.isEmpty(x, y)){
			return b.getPiece(x,y).getColor()!=this.getColor();
		}
		else return false; 
	}

}
