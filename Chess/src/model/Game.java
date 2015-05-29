package model;

public class Game {
	private Board _board;
	
	public Game() {
		_board = new Board(this);
	}
	
	public Board getBoard() {
		return _board;
	}

	public void handleClick(int x, int y) {
		if(!_board.isEmpty(x, y)){
			Piece p = _board.getPiece(x, y);
			p.setPossibleMoves();
		}
		
	}
	

}
