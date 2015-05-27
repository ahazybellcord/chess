package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private Board _board;
	private String _currentPlayer;

	public Game(){
		_board = new Board();
		
		Pawn wp0 = new Pawn("white",this, 0,1);
		Pawn wp1 = new Pawn("white", this, 1,1);
		Pawn wp2 = new Pawn("white", this, 2,1);
		Pawn wp3 = new Pawn("white", this, 3,1);
		Pawn wp4 = new Pawn("white", this, 4,1);
		Pawn wp5 = new Pawn("white", this, 5,1);
		Pawn wp6 = new Pawn("white", this, 6,1);
		Pawn wp7 = new Pawn("white", this, 7,1);
		Rook wr0 = new Rook("white", this, 0,0);
		Knight wk0 = new Knight("white",this, 1,0);
		Bishop wb1 = new Bishop("white",this, 2,0);
		Queen wq = new Queen("white", this, 3,0);
		King wk = new King("white", this, 4,0);
		Bishop wb0 = new Bishop("white",this, 5,0);
		Knight wk1 = new Knight("white",this, 6,0);
		Rook wr1 = new Rook("white", this, 7,0);
		
		Pawn bp0 = new Pawn("black", this, 0,6);
		Pawn bp1 = new Pawn("black", this, 1,6);
		Pawn bp2 = new Pawn("black", this, 2,6);
		Pawn bp3 = new Pawn("black", this, 3,6);
		Pawn bp4 = new Pawn("black", this, 4,6);
		Pawn bp5 = new Pawn("black", this, 5,6);
		Pawn bp6 = new Pawn("black", this, 6,6);
		Pawn p7 = new Pawn("black", this, 7,6);
		Rook br0 = new Rook("black", this, 0,7);
		Knight bk0 = new Knight("black",this, 1,7);
		Bishop bb1 = new Bishop("black",this, 2,7);
		Queen bq = new Queen("black", this, 3,7);
		King bk = new King("black", this, 4,7);
		Bishop bb0 = new Bishop("black",this, 5,7);
		Knight bk1 = new Knight("black",this, 6,7);
		Rook br1 = new Rook("black", this, 7,7);
		wp3.setPossibleMoves();
		System.out.println(wp3.getPossibleMoves());
		
		_currentPlayer = "white";
//		
	}
	
	public String getCurrentPlayer() {
		return _currentPlayer;
	}
	
	public void changePlayers() {
		if (_currentPlayer.equals("white")) {
			_currentPlayer = "black";
		}else{
			_currentPlayer = "white";
		}
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void setBoard(Board board) {
		_board = board;
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
	

}
