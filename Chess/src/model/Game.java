package model;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {
	private Board _board;
	private boolean _currentPlayer;
	private Piece _previousClick;
	private String _notation;
	private ArrayList<String> _moves;
	private ArrayList<String> _gameHistory;
	private boolean _inCheck;
	
	public Game() {
		_board = new Board(this);
		_currentPlayer = true;
		_previousClick = null;
		_notation = "";
		_moves = new ArrayList<String>();
		_gameHistory = new ArrayList<String>();
		_inCheck = false;
		save();
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public ArrayList<String> getMoves(){
		return _moves;
	}
	
	public ArrayList<String> getGameHistory() {
		return _gameHistory;
	}
	
	public void putInCheck() {
		_inCheck = true;
	}
	
	public void removeCheck() {
		_inCheck = false;
	}
	
	public boolean isInCheck() {
		return _inCheck;
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
		//notate starting position
		notate(piece,piece.getLocation().x, piece.getLocation().y);
		_board.setPiece(piece, x, y);
		System.out.println("Moved the piece to the new location!");
		//notate where it moved to
		notate(x,y);
		piece.setLocation(x,y);
		System.out.println("Set location of the piece to new location!");
		this.changePlayers();
		System.out.println("Switched players.");
		checkCheck();
		_previousClick = null;
		System.out.println("Set previous click to null again.");
		printBoard();
		setChanged();
		notifyObservers();
		System.out.println("Notified the UI.");
		save();
		System.out.println("Saved the game in the current configuration");
		// at this point the move has been made and next player is up
				// we must check to see if the current player is in check
				// if so then we need to restrict his moves such that
				// each move must force _inCheck = false.
				// so when the current player attempts to move, when we generate possible moves
				// for the piece he has chosen we need to see whether those moves result
				// in _inCheck = false.
				// when _inCheck = true we need to call checkCheckmate()
				// which sets the possible moves of all the current player's pieces
				// and sees if the union is empty

	}
	
	public void checkCheck() {
		// find current player's king
		Point kingLocation = new Point(0,0);
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_board.getPiece(i,j)!=null) {
					if(_board.getPiece(i,j).getColor()==_currentPlayer && _board.getPiece(i,j).getClass().getName().equals("model.King")) {
						kingLocation = new Point(i,j);
						break;
					}
				}
			}
		}
		
		// now generate possible moves for the opponent and see if they intersect the king
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_board.getPiece(i,j)!=null) {
					if(_board.getPiece(i,j).getColor()!=this._currentPlayer) {
						_board.getPiece(i,j).setPossibleMoves();
						for(Point p : _board.getPiece(i,j).getPossibleMoves()) {
							if(p.equals(kingLocation)) {
								putInCheck();
								break;
							}
						}
					}
				}
			}
 		}
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

	//notate which piece is moving and its source
	private void notate(Piece piece, int x, int y) {
		_notation += (_moves.size() + 1) + ". ";
		if(!piece.getClass().getName().equals("model.Pawn")) {
			_notation += piece.getUnicode() + " " + getChessCoordinate(x,y) + " ";
		}
	}

	//notate the destination of a move and add it to the list of moves
	private void notate(int x, int y) {
		_notation += getChessCoordinate(x,y);
		_moves.add(_notation);
		System.out.println(_notation);
		_notation = "";
		System.out.println("All moves so far");
		System.out.println("----------------");
		for(int i=0; i<_moves.size(); i++) {
			System.out.println(_moves.get(i));
		}
	}

	private String getChessCoordinate(int x, int y) {
		String coordinate = "";
		for(int i=0; i<8; i++) {
			if(i==x) { 
				coordinate += (char)('a'+i); 
			} 
		}
		for(int j=0; j<8; j++) {
			if(j==y) { 
				coordinate += (8-y); 
			} 
		}
		return coordinate;
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
		
		//add board configuration encoded as String to game history
		_gameHistory.add(game);
		
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
