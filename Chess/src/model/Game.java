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
	
	public void changePlayers() {
		_currentPlayer = !_currentPlayer;
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
				
					if(_previousClick.moveIsValid(new Point(x,y))){
						move(_previousClick, x, y);
						System.out.println("The coordinates match and the piece should move!");
					}
			}
			else if(_board.getPiece(x, y).getColor()!=this.getCurrentPlayer()){
				System.out.println("There's an opponent piece and previous click isn't empty!");
				
					if(_previousClick.moveIsValid(new Point(x,y))){
						move(_previousClick, x, y);
						System.out.println("The coordinates match, the piece should move and opponent's piece should be disposed!");
					}
			}
			else{
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				System.out.println("Possible clicks are" + _previousClick.getPossibleMoves().toString());
			}	
		}
		setChanged();
		notifyObservers();
	}

	private void move(Piece piece, int x, int y) {
		
		if(new String("model.Pawn").equals(piece.getClass().getName())) {
			((Pawn) piece).setMoved();
			System.out.println("Piece is a pawn. Set moved to "+((Pawn) piece).wasMoved());
		}
		if(new String("model.Rook").equals(piece.getClass().getName())) {
			((Rook) piece).setMoved();
			System.out.println("Piece is a rook. Set moved to "+((Rook) piece).wasMoved());
		}
		
		_board.setPiece(null, piece.getLocation().x, piece.getLocation().y);
		System.out.println("Set the position of the piece that's moving on the Board to null.");
		_board.setPiece(piece, x, y);
		System.out.println("Moved the piece to the new location!");
		piece.setLocation(x,y);
		System.out.println("Set location of the piece to new location!");
		this.changePlayers();
		System.out.println("Switched players.");
		_previousClick = null;
		System.out.println("Set previous click to null again.");
		printBoard();
		setChanged();
		notifyObservers();
		System.out.println("Notified the UI.");
	}
	
	// a cute text representation of the board to test model-UI updating
	private void printBoard() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++){
				if(this.getBoard().getPiece(j, i)!=null) {
					System.out.print(this.getBoard().getPiece(j, i).getUnicode()+" ");
					if(j==7) { System.out.print("\n");
					}
				}
				else {
					System.out.print("   ");
					if(j==7) { System.out.print("\n");
					}
				}
			}
		}
	}
	
	public Piece getPreviousClick(){
		return _previousClick;
	}


}
