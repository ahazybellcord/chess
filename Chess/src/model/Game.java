package model;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;

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
	private boolean _stalemate;
	private boolean _ai;
	private boolean _aiColor;
	private Point _source;
	private Point _destination;
	private AI _aiLogic;

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
		_stalemate = false;
		_ai = false;
		_capturedPieces = new ArrayList<Piece>();
		getNumberOfPossibleMoves(this.getCurrentPlayer());
	}

	public Game(Board board){
		_board = board;
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

	public Piece getPreviousClick(){
		return _previousClick;
	}

	public boolean isInCheck() {
		return _inCheck;
	}

	public boolean getCurrentPlayer(){
		return _currentPlayer;
	}

	private char getRank(int x) {
		return (char)(x + 'a');
	}
	
	public Point getSource(){
		return _source;
	}
	
	public Point getDestination(){
		return _destination;
	}

	private String getChessCoordinate(int x, int y) {
		String coordinate = "";
		coordinate += getRank(x);   //rank
		coordinate += (8-y);       //file
		return coordinate;
	}

	public boolean isEndGame(){
		return _endGame;
	}

	public void putInCheck() {
		_inCheck = true;
	}

	public void removeCheck() {
		_inCheck = false;
	}

	public ArrayList<Piece> getCapturedPieces(){
		return _capturedPieces;
	}

	public void changePlayers() {
		_currentPlayer = !_currentPlayer;
	}

	public void setPromotionChoice(int n){
		_promotionChoice = n;
	}

	public void setCheckmateFalse(){
		_checkmate = false;
		_stalemate = false;
		_endGame = true;
	}

	public void setNames(String white, String black){
		_names = new ArrayList<String>();
		_names.add(white);
		_names.add(black);
	}
	
	public AI getAI(){
		return _aiLogic;
	}

	public void handleClick(int x, int y) {
		if(_previousClick == null){
			if(!_board.isEmpty(x, y) && _board.getPiece(x, y).getColor() == this.getCurrentPlayer()){
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				selfCheck(_previousClick, _board);
			}
		}
		else{
			if(_board.isEmpty(x, y)){
				if(_previousClick.moveIsValid(new Point(x,y))){
					move(_previousClick, x, y);
				}
			}
			else if(_board.getPiece(x, y).getColor()!=this.getCurrentPlayer()){
				if(_previousClick.moveIsValid(new Point(x,y))){
					move(_previousClick, x, y);
				}
			}
			else if(_previousClick.getLocation().x == x && _previousClick.getLocation().y == y) {
				_previousClick = null;
			}
			else{
				_previousClick = _board.getPiece(x, y);
				_previousClick.setPossibleMoves();
				selfCheck(_previousClick, _board);
			}	
		}
		setChanged();
		notifyObservers();
	}

	void selfCheck(Piece piece, Board board) {
		HashSet<Point> possibleMoves = new HashSet<Point>();
		Board myBoard = new Board();
		Game game = new Game(myBoard);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j<8; j++){
				if(board.getPiece(i, j) !=null){
					myBoard.setPiece(board.getPiece(i, j), i, j);
					myBoard.getPiece(i, j).setGame(game);
				}
			}
		}
		Point loc = piece.getLocation();
		for(Point p: piece.getPossibleMoves()){
			if(piece.getClass().getName().equals("model.King") && Math.abs(p.x-piece.getLocation().x)>1){
				ArrayList<Point> queenSide = new ArrayList<Point>();
				queenSide.add(new Point(3,p.y));
				queenSide.add(new Point(2,p.y));
				queenSide.add(new Point(1,p.y));
				ArrayList<Point> kingSide = new ArrayList<Point>();
				kingSide.add(new Point(5,p.y));
				kingSide.add(new Point(6,p.y));
				ArrayList<Point> defaultSide = queenSide;
				if(p.x>4){
					defaultSide = kingSide;
				}
				boolean canCastle = true;
				for(Point q: defaultSide){
					Piece temperory = null;
					myBoard.setPiece(null, loc.x, loc.y);
					myBoard.setPiece(piece, q.x, q.y);
					piece.setLocation(q.x, q.y);
					checkCheck(myBoard);
					if(isInCheck()){
						canCastle = false;
					}
					myBoard.setPiece(temperory, q.x, q.y);
					myBoard.setPiece(piece, loc.x, loc.y);
					piece.setLocation(loc.x, loc.y);
				}
				if(canCastle){
					possibleMoves.add(p);
				}

			}
			else{
				Piece temperory = null;
				myBoard.setPiece(null, loc.x, loc.y);
				if(!myBoard.isEmpty(p.x, p.y)){
					temperory = myBoard.getPiece(p.x, p.y);
				}
				myBoard.setPiece(piece, p.x, p.y);
				piece.setLocation(p.x, p.y);
				checkCheck(myBoard);
				if(!isInCheck()){
					possibleMoves.add(p);
				}

				myBoard.setPiece(temperory, p.x, p.y);
				myBoard.setPiece(piece, loc.x, loc.y);
				piece.setLocation(loc.x, loc.y);	
			}

		}
		piece.setPossibleMoves(possibleMoves);
		checkCheck(board);

	}

	void move(Piece piece, int x, int y) {
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(!_board.isEmpty(i, j) && _board.getPiece(i, j).getColor() == _currentPlayer && _board.getPiece(i, j).getClass().getName().equals("model.Pawn")){
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
		_source = new Point(piece.getLocation().x, piece.getLocation().y);
		_destination = new Point(x,y);
		boolean captured = false;
		if(_board.getPiece(x, y)!=null){
			_capturedPieces.add(_board.getPiece(x, y));
			captured = true;
		}
		char rank = getRank(piece.getLocation().x);
		_board.setPiece(piece, x, y);
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
		if(piece.getClass().getName().equals("model.Pawn") && piece.getColor() == true && y == 0){
			handlePawnPromotion(x, y);
		}
		else if(piece.getClass().getName().equals("model.Pawn") && piece.getColor() == false && y == 7){
			handlePawnPromotion(x, y);
		}
		this.changePlayers();
		checkCheck(_board);
		if(isInCheck()){
			System.out.println("IN CHECK!");
			if(checkEndGame()){
				this.changePlayers();
				System.out.println("CHECKMATE!");

			}
		}
		_previousClick = null;
		// notate the move
		notate(piece,rank,castling,captured,en_passant,x,y);
		printBoard();
		getNumberOfPossibleMoves(_currentPlayer);
		if(getNumberOfPossibleMoves(_currentPlayer) == 0){
			_stalemate = true;
			_endGame = true;
		}
		System.out.println("Stalemate: " + _stalemate);
		if(_ai){
			_aiLogic.move();
		}
		setChanged();
		notifyObservers();
	}

	private boolean checkEndGame() {
		for(int i = 0; i < 8; i++){
			for(int j = 0; j<8; j++){
				if(!_board.isEmpty(i, j)){
					if(_board.getPiece(i, j).getColor()==this.getCurrentPlayer()){
						_board.getPiece(i, j).setPossibleMoves();
						selfCheck(_board.getPiece(i, j), _board);
						if(_board.getPiece(i, j).getPossibleMoves().size() !=0){
							return false;
						}
					}
				}

			}
		}
		if(!isInCheck()){
			_stalemate = true;
		}
		else{
			_checkmate = true;
		}
		return true;

	}

	public boolean isCheckmate(){
		return _checkmate;
	}

	public boolean isStalemate(){
		return _stalemate;
	}

	private void handlePawnPromotion(int x, int y) {
		_pawnPromotion = true;
		setChanged();
		notifyObservers();
		if(_promotionChoice != -1){
			if(_promotionChoice == 0){
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
	
	public void setAI(boolean aiColor){
		_ai = true;
		_aiLogic = new AI(this, aiColor);
		_aiColor = aiColor;
	}

	public int getNumberOfPossibleMoves(boolean color) {
		int count=0;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(!_board.isEmpty(i, j)) {
					if(_board.getPiece(i, j).getColor()==color) {
						//count all the possible moves of each piece of the given color
						_board.getPiece(i, j).setPossibleMoves();
						selfCheck(_board.getPiece(i, j), _board);
						if(_board.getPiece(i, j).getPossibleMoves().size()!=0){
							System.out.println("Possible moves for " + _board.getPiece(i, j).getUnicode() + " are " + _board.getPiece(i, j).getPossibleMoves().toString());
						}
						count += _board.getPiece(i, j).getPossibleMoves().size();
					}
				}
			}
		}
		System.out.println("There are "+count+" possible moves for the current player");
		return count;
	}

	public void checkCheck(Board board) {
		removeCheck();
		// find current player's king
		Point kingLocation = new Point(0,0);
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(!board.isEmpty(i, j)) {
					if(board.getPiece(i,j).getColor()==_currentPlayer && board.getPiece(i,j).getClass().getName().equals("model.King")) {
						kingLocation = new Point(i,j);
						break;
					}
				}
			}
		}

		// now generate possible moves for the opponent and see if they intersect the king
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(!board.isEmpty(i, j)) {
					if(board.getPiece(i,j).getColor()!=this._currentPlayer) {
						board.getPiece(i,j).setPossibleMoves();
						for(Point p : board.getPiece(i,j).getPossibleMoves()) {
							if(p.equals(kingLocation)) {
								putInCheck();
								return;
							}
						}
					}
				}
			}
		}

	}

	// a cute text representation of the board to test model-UI updating
	private void printBoard() {
		for(int i = 0; i < 8; i++){
			System.out.print(8-i + " ");
			for(int j = 0; j<8; j++){
				if(!_board.isEmpty(j, i)){
					System.out.print(_board.getPiece(j, i).getUnicode()+' ');
				}
				else{
					System.out.print('\u3000');
					System.out.print('\u2006');

				}
			}
			System.out.println("");
		}
		System.out.print('\u3000');
		System.out.print('\u2006');
		for(int i =0; i<8; i++){
			System.out.print((char)('a'+i));
			System.out.print('\u3000');
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

	public void save(File f) {
		String game = "";
		game += _names.get(0) + "\n";
		game += _names.get(1) + "\n";
		System.out.println("Moves: " + _moves.toString());
		for(int i = 0; i<_moves.size(); i++){
			game += _moves.get(i);
			game += " ";
		}


		//add board configuration encoded as String to game history
		_gameHistory.add(game);

		//		game += boolToInt(_currentPlayer);
		try {
			PrintWriter saveFile = new PrintWriter(f);
			saveFile.println(game);
			saveFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// a helper method to create the save file
	private int boolToInt(boolean b) {
		if(b) { return 1; }
		else return 0;
	}


}
