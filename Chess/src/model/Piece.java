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
	}
	
	public void setPossibleMoves() {
		
	}
	
	public boolean moveIsValid(Point location) {
		for(Point point : _possibleMoves) {
			if(point.equals(location)) { return true; }
		}
		return false;
	}

	

}
