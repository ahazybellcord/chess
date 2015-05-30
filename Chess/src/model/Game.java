package model;

import java.awt.Point;
import java.util.Observable;

public class Game extends Observable {
	private Board _board;
	private boolean _currentPlayer;
	private Piece _previousClick;
	public Game() {
		_board = new Board(this);
		_currentPlayer = true;
		_previousClick = null;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public boolean getCurrentPlayer(){
		return _currentPlayer;
	}
	
	public void setCurrentPlayer(boolean b){
		_currentPlayer = b;
	}

	public void handleClick(int x, int y) {
		if(_previousClick == null){
			System.out.println("Previous click is null!");
			if(!_board.isEmpty(x, y) && _board.getPiece(x, y).getColor() == this.getCurrentPlayer()){
				System.out.println("Setting a piece to previous click!");
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				System.out.println("Possible clicks are" + _previousClick.getPossibleMoves().toString());

			}
		}
		else{
			System.out.println("Previous click isn't null!");
			if(_board.isEmpty(x, y)){
				System.out.println("Board is empty and previous click isn't empty!");
				for(Point p: _previousClick.getPossibleMoves()){
					if(p.x == x && p.y == y){
						move(_previousClick, x, y);
						System.out.println("The coordinates match and the piece should move!");
						break;
					}
				}
			}
			else if(_board.getPiece(x, y).getColor()!=this.getCurrentPlayer()){
				System.out.println("There's an opponent piece and previous click isn't empty!");
				for(Point p: _previousClick.getPossibleMoves()){
					if(p.x == x && p.y == y){
						move(_previousClick, x, y);
						System.out.println("The coordinates match, the piece should move and opponent's piece should be disposed!");
						break;
					}
				}
			}
			else{
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				System.out.println("Possible clicks are" + _previousClick.getPossibleMoves().toString());
			}
			
		}
		
		
	}

	private void move(Piece piece, int x, int y) {
		_board.setPiece(null, piece.getLocation().x, piece.getLocation().y);
		System.out.println("Set the position of the piece that's moving on the Board to null.");
		_board.setPiece(null, x, y);
		System.out.println("Set the position on the Board that the piece is moving to, null.");
		_board.setPiece(piece, x, y);
		System.out.println("Moved the piece to the new location!");
		this.setCurrentPlayer(!this.getCurrentPlayer());
		System.out.println("Moved switched.");
		_previousClick = null;
		System.out.println("Set previous click to null again.");
		setChanged();
		notifyObservers();
		System.out.println("Notified the UI.");
	}
	

}
