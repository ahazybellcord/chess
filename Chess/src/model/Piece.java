package model;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Piece {
	private final boolean _color;
	private Game _game;
	private Point _location;
	private int _value;
	private HashSet<Point> _possibleMoves;
	private boolean _moved;

	
	Piece(boolean color, Game game, Point location) {
		_color = color;
		_game = game;
		_location = location;
		_value = 0;
		_possibleMoves = new HashSet<Point>();
		_moved = false;
	}
	
	Game getGame() {
		return _game;
	}

	public void setGame(Game game){
		_game = game;
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
	
	public int getValue() {
		return _value;
	}

	void setValue(int value) {
		_value = value;
	}
	
	public void setMoved(){
		_moved = true;
	}
	
	public boolean wasMoved(){
		return _moved;
	}
	
	boolean checkMove(int x, int y) {
		Board b = this.getGame().getBoard();
		return b.isEmpty(x, y) || b.getPiece(x, y).getColor() != this.getColor();
	}
	
	//checks for an opponent's piece on the board at (x,y)
	boolean checkOpponent(int x, int y) {
		Board b = this.getGame().getBoard();
		return !b.isEmpty(x, y) && b.getPiece(x, y).getColor() != this.getColor();
	}

	public boolean moveIsValid(Point location) {
		for(Point point : _possibleMoves) {
			if(point.equals(location)) { return true; }
		}
		return false;
	}
	
	public HashSet<Point> getPossibleMoves() {
		return _possibleMoves;
	}
	
	public void setPossibleMoves(){
		_possibleMoves = new HashSet<Point>();
	}
	
	public void setPossibleMoves(HashSet<Point> possibleMoves){
		_possibleMoves = possibleMoves;
	}
	
	public String getUnicode() {
		return "";
	}
	
	public String getSymbol() {
		return "";
	}


}
