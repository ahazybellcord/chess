package model;

import java.awt.Point;
import java.util.HashSet;

public class Piece {
	private String _color;
	private Point _location;
	private Game _game;
	private Board _board;
	private HashSet<Point> _possibleMoves;
	
	public Piece(String color, Game game, int x, int y) {
		_color = color;
		_game = game;
		_board = game.getBoard();
		_location = new Point(x,y);
		_board.setPiece(this, x, y);
		_possibleMoves = new HashSet<Point>();
	}
	
	/**
	 * Returns the color of the piece
	 * white = true; black = false;
	 * @return color
	 */
	public String getColor(){
		return _color;
	}
	
	public Game getGame() {
		return _game;
	}
	
	public String getName() {
		return "";
	}
	/**
	 * Sets the board location of a piece
	 * @param location (x,y)
	 */
	public void setLocation(int x, int y) {
		_location = new Point(x,y);
	}
	public Point getLocation() {
		return _location;
	}
	public int x(){
		return (int)_location.getX();
	}
	public int y() {
		return (int)_location.getY();
	}
	public HashSet<Point> getPossibleMoves() {
		return _possibleMoves;
	}
	public void setPossibleMoves() {
		_possibleMoves = new HashSet<Point>();
		//unique for each piece
	}
	public void setPossibleMoves(HashSet<Point> moves) {
		_possibleMoves = moves;
	}
	public Board getBoard() {
		return _board;
	}
	
	public static String getName(Piece piece) {
		if(piece.getName().equals("king")) {
			if(piece.getColor().equals("white")) return "\u2654"; 
			else return "\u265a";
		}
		if(piece.getName().equals("queen")) {
			if(piece.getColor().equals("white")) return "\u2655"; 
			else return "\u265b";
		}
		if(piece.getName().equals("rook")) {
			if(piece.getColor().equals("white")) return "\u2656"; 
			else return "\u265c";
		}
		if(piece.getName().equals("bishop")) {
			if(piece.getColor().equals("white")) return "\u2657"; 
			else return "\u265d";
		}
		if(piece.getName().equals("knight")) {
			if(piece.getColor().equals("white")) return "\u2658"; 
			else return "\u265e";
		}
		if(piece.getName().equals("pawn")) {
			if(piece.getColor().equals("white")) return "\u2659"; 
			else return "\u265f";
		}
		return "";
	}
	public boolean checkMove(int x, int y){
		//piece can move to empty space or opponent space
		//valid for all pieces except pawn
		if(_board.getPiece(x,y)==null){
			return true;
		}//if opponent piece (different color)
		else if(_board.getPiece(x,y).getColor()!=this.getColor()){
			return true;
		}
		return false;
	}
	
	//checks square (x+i, y+j)
	//if valid, adds to list of possible moves
	public void squareChecker(int i, int j) {
		System.out.println("Square checker");
		if(i==0 & j>0){
			if(y()<(8-j)) {
				System.out.println("added");
				getPossibleMoves().add(new Point(x()+i,y()+j));
			}
		}
		if(i==0 & j<0){
			if(y()>(-1-j)) {
				System.out.println("added");
				getPossibleMoves().add(new Point(x()+i,y()+j));
			}
		}
		if(i>0 & j==0){
			if(x()<(8-j)) {
				System.out.println("added");
				getPossibleMoves().add(new Point(x()+i,y()+j));
			}
		}
		if(i<0 & j==0){
			if(x()>(-1-j)) {
				System.out.println("added");
				getPossibleMoves().add(new Point(x()+i,y()+j));
			}
		}
		if(i>0 & j>0) {
			if(x()<(8-i) & y()<(8-j)){
				if(checkMove(x()+i,y()+j)){
					System.out.println("added");
					getPossibleMoves().add(new Point(x()+i,y()+j));
				}
			}
		}
		if(i<0 & j<0) {
			if(x()>(-1-i) & y()>(-1-j)){
				if(checkMove(x()+i,y()+j)){
					System.out.println("added");
					getPossibleMoves().add(new Point(x()+i,y()+j));
				}
			}
		}
		if(i>0 & j<0) {
			if(x()<(8-i) & y()>(-1-j)){
				if(checkMove(x()+i,y()+j)){
					System.out.println("added");
					getPossibleMoves().add(new Point(x()+i,y()+j));
				}
			}
		}
		if(i<0 & j>0) {
			if(x()>(-1-i) & y()<(8-j)){
				if(checkMove(x()+i,y()+j)){
					System.out.println("added");
					getPossibleMoves().add(new Point(x()+i,y()+j));

				}
			}
		}
		for(Point p : getPossibleMoves()){
			System.out.print("("+p.getX()+","+p.getY()+") ");
		}
	}
	
	public static boolean colorToBoolean(String color) {
		if(color.equals("white")) {
			return true;
		}
		else return false;
	}
	
	public boolean tryMove(int x, int y){
		setPossibleMoves(); //generate possible moves from initial location
		System.out.println("Possible moves: "+getPossibleMoves().toString());
		for(Point openSquare : getPossibleMoves()) {
			// if desired square is found in the list of possible moves
			if (openSquare.getX()==x & openSquare.getY()==y) {
				//remove piece from board at old location
				// set piece to board at new location
				_board.setPiece(getBoard().removePiece(_location),x,y);
				_game.changePlayers();
				return true;
			}
		}
		return false;
	}
	
}
