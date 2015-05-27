package model;

import java.awt.Point;

public class Board {
	private Piece[][] _pieces;
	private Game _game;
	
	public Board(Game game) {
		_game = game;
		_pieces = new Piece[8][8];
		makePieces();
	}
	
	public void makePieces() {
		Boolean color = true;
		int position = 0;
		for(int i = 0; i<2; i++){
			//create Pawns
			for(int j=0; j<8; j++){
				if(i==1){
					_pieces[j][position-1] = new Pawn(color,_game,new Point(j,position-1));
				}
				else{
					_pieces[j][position+1] = new Pawn(color,_game,new Point(j,position+1));
				}
			}
			//create Bishops
			_pieces[2][position] = new Bishop(color,_game,new Point(2,position));
			_pieces[5][position] = new Bishop(color,_game,new Point(5,position));
			//create Knights
			_pieces[1][position] = new Knight(color,_game,new Point(1,position));
			_pieces[6][position] = new Knight(color,_game,new Point(6,position));
			//create Rooks
			_pieces[0][position] = new Rook(color,_game,new Point(0,position));
			_pieces[7][position] = new Rook(color,_game,new Point(7,position));
			//create Queen
			_pieces[3][position] = new Queen(color,_game,new Point(3,position));
			//create King
			_pieces[4][position] = new King(color,_game,new Point(4,position));
			
			color = false;
			position = 7;
		}
		
	}
	
	public Piece getPiece(int x, int y) {
		return _pieces[x][y];
	}
	
	public Point getLocation(Piece piece) {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_pieces[i][j].equals(piece)) { return new Point(i,j); }
			}
		}
		return null;
	}
	
	public boolean isEmpty(int x, int y){
		return _pieces[x][y]==null;
	}
	
	

}
