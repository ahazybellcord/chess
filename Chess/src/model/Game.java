package model;

public class Game {
	private Board _board;
	
	public Game() {
		_board = new Board(this);
	}
	
	public Board getBoard() {
		return _board;
	}
	

}
