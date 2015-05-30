package model;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
		if(!piece.wasMoved()) {
			piece.setMoved();
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
		save();
		System.out.println("Saved the game in the current configuration");
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
	
	public void save() {
		String game = "";
		for(int j=0; j<8; j++) {
			for(int i=0; i<8; i++) {
				Piece currentPiece = this.getBoard().getPiece(i, j);
				if(currentPiece!=null) {
					game += currentPiece.getUnicode();
					//if the piece is a pawn, rook or king, include _moved variable
					if(currentPiece.getClass().getName().equals("model.Pawn")
							||currentPiece.getClass().getName().equals("model.Rook")
							||currentPiece.getClass().getName().equals("model.King")) {
						game += boolToInt(currentPiece.wasMoved());
					}
				}
				game += "\n";
			}
		}
		game += boolToInt(this.getCurrentPlayer());
		try {
			PrintWriter saveFile = new PrintWriter("save.txt");
			saveFile.println(game);
			saveFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// a helper method to create the save file
	private int boolToInt(boolean b) {
		if(b) { return 1; }
		else return 0;
	}



}
