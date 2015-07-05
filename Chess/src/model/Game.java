package model;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

public class Game extends Observable {
	private Board _board;
	private boolean _currentPlayer;
	private Piece _previousClick;
	private String _notation;
	private ArrayList<String> _moves;
	private ArrayList<String> _gameHistory;
	private boolean _inCheck;
	private ArrayList<Piece> _capturedPieces;
	private ArrayList<String> _names;
	private boolean _pawnPromotion;
	private int _promotionChoice;
	private boolean _checkmate;
	private boolean _endGame;

	public Game(String[] args) {
		_names = new ArrayList<String>();
		if(args.length!=0){
			_names.add(args[0]);
			_names.add(args[1]);

		}
		else{
			_names.add("White");
			_names.add("Black");
		}
		_board = new Board(this);
		_promotionChoice = -1;
		_currentPlayer = true;
		_endGame = false;
		_previousClick = null;
		_notation = "";
		_moves = new ArrayList<String>();
		_gameHistory = new ArrayList<String>();
		_inCheck = false;
		_pawnPromotion = false;
		_checkmate = false;
		_capturedPieces = new ArrayList<Piece>();
		save();
		getNumberOfPossibleMoves(this.getCurrentPlayer());
	}

	public Board getBoard() {
		return _board;
	}

	public ArrayList<String> getMoves(){
		return _moves;
	}

	public ArrayList<String> getNames(){
		return _names;
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

	public ArrayList<Piece> getCapturedPieces(){
		return _capturedPieces;
	}

	public boolean getCurrentPlayer(){
		return _currentPlayer;
	}

	public void changePlayers() {
		_currentPlayer = !_currentPlayer;
	}

	public void setPromotionChoice(int n){
		_promotionChoice = n;
	}

	public void handleClick(int x, int y) {
		if(_previousClick == null){
			System.out.println("Previous click is null!");
			if(!_board.isEmpty(x, y) && _board.getPiece(x, y).getColor() == this.getCurrentPlayer()){
				System.out.println("Setting a piece to previous click!");
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				selfCheck(_previousClick);
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
				selfCheck(_previousClick);
				System.out.println("Possible clicks are" + _previousClick.getPossibleMoves().toString());
			}	
		}
		setChanged();
		notifyObservers();
	}

	private void selfCheck(Piece piece) {
		HashSet<Point> possibleMoves = new HashSet<Point>();
		for(Point p: piece.getPossibleMoves()){
			Piece temperory = null;
			if(!_board.isEmpty(p.x, p.y)){
				temperory = _board.getPiece(p.x, p.y);
			}
			Point originalLocation = piece.getLocation();
			if(piece.getClass().getName().equals("model.King")){
				if(Math.abs(originalLocation.x - p.x)>1){
					if(piece.getColor()){
						if(p.x == 6){
							_board.setPiece(piece, 5, 7);
							_board.setPiece(null, 4, 7);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 5, 7);
								_board.setPiece(piece, 4, 7);
								checkCheck();
								break;
							}
							_board.setPiece(null, 5, 7);
							_board.setPiece(piece, 6, 7);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 6, 7);
								_board.setPiece(piece, 4, 7);
								checkCheck();
								break;
							}
							else{
								_board.setPiece(null, 6, 7);
								_board.setPiece(piece, 4, 7);
								possibleMoves.add(p);
							}
						}
						else if(p.x == 1){
							_board.setPiece(piece, 3, 7);
							_board.setPiece(null, 4, 7);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(piece, 2, 7);
								_board.setPiece(null, 3, 7);
								checkCheck();
								break;
							}
							_board.setPiece(piece, 2, 7);
							_board.setPiece(null, 3, 7);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 2, 7);
								_board.setPiece(piece, 4, 7);
								checkCheck();
								break;
							}
							_board.setPiece(null, 2, 7);
							_board.setPiece(piece, 1, 7);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 1, 7);
								_board.setPiece(piece, 4, 7);
								checkCheck();
								break;
							}
							else{
								_board.setPiece(null, 1, 7);
								_board.setPiece(piece, 4, 7);
								possibleMoves.add(p);
							}
							
						}
					}
					else{
						if(p.x == 6){
							_board.setPiece(piece, 5, 0);
							_board.setPiece(null, 4, 0);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 5, 0);
								_board.setPiece(piece, 4, 0);
								checkCheck();
								break;
							}
							_board.setPiece(null, 5, 0);
							_board.setPiece(piece, 6, 0);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 6, 0);
								_board.setPiece(piece, 4, 0);
								checkCheck();
								break;
							}
							else{
								_board.setPiece(null, 6, 0);
								_board.setPiece(piece, 4, 0);
								possibleMoves.add(p);
							}
						}
						else if(p.x == 1){
							_board.setPiece(piece, 3, 0);
							_board.setPiece(null, 4, 0);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(piece, 2, 0);
								_board.setPiece(null, 3, 0);
								checkCheck();
								break;
							}
							_board.setPiece(piece, 2, 0);
							_board.setPiece(null, 3, 0);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 2, 0);
								_board.setPiece(piece, 4, 0);
								checkCheck();
								break;
							}
							_board.setPiece(null, 2, 0);
							_board.setPiece(piece, 1, 0);
							checkCheck();
							if(isInCheck()){
								_board.setPiece(null, 1, 0);
								_board.setPiece(piece, 4, 0);
								checkCheck();
								break;
							}
							else{
								_board.setPiece(null, 1, 0);
								_board.setPiece(piece, 4, 0);
								possibleMoves.add(p);
							}
						}
					}
				}
			}
			_board.setPiece(null, p.x, p.y);
			_board.setPiece(null, originalLocation.x, originalLocation.y);
			_board.setPiece(piece, p.x, p.y);
			checkCheck();
			if(!isInCheck()){
				possibleMoves.add(p);
			}
			else{
				this.putInCheck();
			}
			if(temperory==null){
				_board.setPiece(null, p.x, p.y);
				_board.setPiece(piece, originalLocation.x, originalLocation.y);
			}
			else{
				_board.setPiece(null, p.x, p.y);
				_board.setPiece(temperory, p.x, p.y);
				_board.setPiece(piece, originalLocation.x, originalLocation.y);
			}
		}
		piece.overridePossibleMoves(possibleMoves);
	}

	private void move(Piece piece, int x, int y) {

		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(_board.getPiece(i, j)!=null && _board.getPiece(i, j).getColor() == _currentPlayer && _board.getPiece(i, j).getClass().getName().equals("model.Pawn")){
					((Pawn) _board.getPiece(i, j)).setJustMoved(false);
				}
			}
		}
		boolean en_passant = false;
		if(piece.getClass().getName().equals("model.Pawn") && piece.getLocation().x!=x && _board.getPiece(x, y) == null){
			en_passant = true;
		}
		// castling 0: no, 1: kingside, 2: queenside
		int castling = 0;
		//check if castling kingside - white
		if(piece.getLocation().equals(new Point(4,7)) && x==6 && y==7) {
			Rook r = (Rook) _board.getPiece(7,7);
			_board.setPiece(null, 7, 7);
			_board.setPiece(r,5,7);
			r.setLocation(5, 7);
			r.setMoved();
			castling = 1;
		}
		//check if castling kingside - black
		if(piece.getLocation().equals(new Point(4,0)) && x==6 && y==0) {
			Rook r = (Rook) _board.getPiece(7,0);
			_board.setPiece(null, 7, 0);
			_board.setPiece(r,5,0);
			r.setLocation(5, 0);
			r.setMoved();
			castling = 1;
		}
		//check if castling queenside - white
		if(piece.getLocation().equals(new Point(4,7)) && x==2 && y==7) {
			Rook r = (Rook) _board.getPiece(0,7);
			_board.setPiece(null, 0, 7);
			_board.setPiece(r,3,7);
			r.setLocation(3, 7);
			r.setMoved();
			castling = 2;
		}
		//check if castling queenside - black
		if(piece.getLocation().equals(new Point(4,0)) && x==2 && y==0) {
			Rook r = (Rook) _board.getPiece(0,0);
			_board.setPiece(null, 0, 0);
			_board.setPiece(r,3,0);
			r.setLocation(3, 0);
			r.setMoved();
			castling = 2;
		}
		if(!piece.wasMoved()) {
			piece.setMoved();
		}
		if(piece.getClass().getName().equals("model.Pawn") && Math.abs(piece.getLocation().y - y) == 2) {
			((Pawn) piece).setSpecial();
			((Pawn) piece).setJustMoved(true);
		}
		_board.setPiece(null, piece.getLocation().x, piece.getLocation().y);
		System.out.println("Set the position of the piece that's moving on the Board to null.");
		boolean captured = false;
		if(_board.getPiece(x, y)!=null){
			_capturedPieces.add(_board.getPiece(x, y));
			captured = true;
		}
		char rank = getRank(piece.getLocation().x);

		_board.setPiece(piece, x, y);
		System.out.println("Moved the piece to the new location!");

		piece.setLocation(x,y);
		if(en_passant){
			if(piece.getColor()){
				_capturedPieces.add(_board.getPiece(x, y+1));
				_board.setPiece(null, x, y+1);
				captured = true;
			}
			else{
				_capturedPieces.add(_board.getPiece(x, y-1));
				_board.setPiece(null, x, y-1);
				captured = true;
				
			}
		}
		System.out.println("Set location of the piece to new location!");
		if(piece.getClass().getName().equals("model.Pawn") && piece.getColor() == true && y == 0){
			System.out.println("Pawn Promotion Method called!");
			handlePawnPromotion(x, y);
		}
		else if(piece.getClass().getName().equals("model.Pawn") && piece.getColor() == false && y == 7){
			System.out.println("Pawn Promotion Method called!");
			handlePawnPromotion(x, y);
		}
		this.changePlayers();
		System.out.println("Switched players.");
		checkCheck();
		if(isInCheck()){
			System.out.println("IN CHECK!");
			if(checkCheckmate()){
				this.changePlayers();
				System.out.println("CHECKMATE!");
				
			}
		}
		_previousClick = null;
		System.out.println("Set previous click to null again.");
		// notate the move
		notate(piece,rank,castling,captured,en_passant,x,y);
		printBoard();
		setChanged();
		notifyObservers();
		System.out.println("Notified the UI.");
		save();
		getNumberOfPossibleMoves(_currentPlayer);
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

	private boolean checkCheckmate() {
		for(int i = 0; i < 8; i++){
			for(int j = 0; j<8; j++){
				if(_board.getPiece(i, j)!=null){
					if(_board.getPiece(i, j).getColor()==this.getCurrentPlayer()){
						_board.getPiece(i, j).setPossibleMoves();
						selfCheck(_board.getPiece(i, j));
						System.out.println("Check Checkmate:" +_board.getPiece(i, j).getPossibleMoves().toString());
						if(_board.getPiece(i, j).getPossibleMoves().size() !=0){
							return false;
						}
					}
				}

			}
		}
		_checkmate = true;
		return true;

	}

	public boolean isCheckmate(){
		return _checkmate;
	}

	private void handlePawnPromotion(int x, int y) {
		_pawnPromotion = true;
		setChanged();
		notifyObservers();
		if(_promotionChoice != -1){
			if(_promotionChoice == 0){
				System.out.println("The pawn should be promoted to a Knight.");
				_board.setPiece(null, x, y);
				if(y==0){
					Knight myKnight = new Knight(true, this,new Point(x, y));
					_board.setPiece(myKnight, x, y);
				}
				else{
					Knight myKnight = new Knight(false, this,new Point(x, y));
					_board.setPiece(myKnight, x, y);
				}
				_pawnPromotion = false;

			}
			else if(_promotionChoice == 1){
				System.out.println("The pawn should be promoted to a Rook.");
				_board.setPiece(null, x, y);
				if(y==0){
					Rook myRook = new Rook(true, this,new Point(x, y));
					_board.setPiece(myRook, x, y);
				}
				else{
					Rook myRook = new Rook(false, this,new Point(x, y));
					_board.setPiece(myRook, x, y);
				}
				_pawnPromotion = false;
			}
			else if(_promotionChoice == 2){
				System.out.println("The pawn should be promoted to a Queen.");
				_board.setPiece(null, x, y);
				if(y==0){
					Queen myQueen = new Queen(true, this,new Point(x, y));
					_board.setPiece(myQueen, x, y);
				}
				else{
					Queen myQueen = new Queen(false, this,new Point(x, y));
					_board.setPiece(myQueen, x, y);
				}
				_pawnPromotion = false;
			}
			else if(_promotionChoice ==3){
				System.out.println("The pawn should be promoted to a Bishop.");
				if(y==0){
					Bishop myBishop = new Bishop(true, this,new Point(x, y));
					_board.setPiece(myBishop, x, y);
				}
				else{
					Bishop myBishop = new Bishop(false, this,new Point(x, y));
					_board.setPiece(myBishop, x, y);
				}
				_pawnPromotion = false;
			}
		}
		else{
			System.out.println("Promotion choice not selected properly.");
			System.exit(1);
		}


	}

	public boolean getPawnPromotion(){
		return _pawnPromotion;
	}

	public int getNumberOfPossibleMoves(boolean color) {
		int count=0;

		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_board.getPiece(i,j)!=null) {
					if(_board.getPiece(i, j).getColor()==_currentPlayer) {
						//count all the possible moves of each piece of the given color
						_board.getPiece(i, j).setPossibleMoves();
						selfCheck(_board.getPiece(i, j));
						count += _board.getPiece(i, j).getPossibleMoves().size();
					}
				}
			}
		}
		System.out.println("There are "+count+" possible moves for the current player");
		return count;
	}

	public void checkCheck() {
		removeCheck();
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
				if(_board.getPiece(j, i)!=null) {
					System.out.print(_board.getPiece(j, i).getUnicode()+" ");
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
	private void notate(Piece piece, char rank, int castling, boolean captured, boolean en_passant, int x, int y) {
		if(_checkmate && _currentPlayer){
			_notation += (_moves.size()/2 + 1) + ". ";
		}
		else if(_checkmate && !_currentPlayer){
			
		}
		else if(!_currentPlayer) { _notation += (_moves.size()/2 + 1) + ". "; }
		if(castling==1) {
			_notation += "O-O";
		}
		else if(castling==2) {
			_notation += "O-O-O";
		}
		else {
			_notation += piece.getSymbol();
			//if a piece is captured, indicate with 'x'
			if(captured) { 
				if(piece.getClass().getName().equals("model.Pawn")) {
					_notation += rank;
				}
				_notation += "x"; 
			}
			_notation += getChessCoordinate(x,y);
			if(en_passant){
				_notation += "e.p.";
			}
		}
		if(_checkmate){
        	_notation += "#";
        	_moves.add(_notation);
    		_notation = "";
        	return;
        }
		if(this.isInCheck()) {	_notation += "+"; }
		if(_currentPlayer) { _notation += " "; }
        
		_moves.add(_notation);
		_notation = "";
	}

	private char getRank(int x) {
		return (char)(x + 'a');
	}

	private String getChessCoordinate(int x, int y) {
		String coordinate = "";
		coordinate += getRank(x);   //rank
		coordinate += (8-y);       //file
		return coordinate;
	}

	public Piece getPreviousClick(){
		return _previousClick;
	}

	public void save() {
		String game = "";
		for(int j=0; j<8; j++) {
			for(int i=0; i<8; i++) {
				Piece currentPiece = _board.getPiece(i, j);
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

		game += boolToInt(_currentPlayer);
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
	
	public void setCheckmateFalse(){
		_checkmate = false;
		_endGame = true;
	}
	
	public boolean isEndGame(){
		return _endGame;
	}

}
