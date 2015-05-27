package ui;

import java.awt.GridLayout;

import javax.swing.*;

import model.*;

public class BoardWindow {
	private JFrame _frame;
	private BoardButton[][] _squares;
	private Game _game;
	private Board _board;
	private ChessListener _listener;
	
	public BoardWindow(Game game) {
		_game = game;
		_board = game.getBoard();
		_frame = new JFrame("Chess");
		_squares = new BoardButton[8][8];
		_listener = new ChessListener(game,this);
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_board.getPiece(j,7-i)!=null){
					_squares[j][7-i] = new BoardButton(_board.getPiece(j,7-i));
					_squares[j][7-i].getButton().addMouseListener(_listener);
				}else{
					_squares[j][7-i] = new BoardButton(new Piece(" ",this.getGame(),j,7-i));
					_squares[j][7-i].getButton().addMouseListener(_listener);
				}
				_frame.add(_squares[j][7-i].getButton());
			}
		}
		_frame.setLayout(new GridLayout(8,8));
		_frame.pack();
		_frame.setVisible(true);
	}
	
	public Game getGame() {
		return _game;
	}
	
	public JFrame getFrame() {
		return _frame;
	}
	
	public BoardButton[][] getBoardButtons() {
		return _squares;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void update(Game game) {
		_board = game.getBoard();
		_frame.setVisible(false);
		_frame = new JFrame();
		_squares = new BoardButton[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(_board.getPiece(j,7-i)!=null){
					_squares[j][7-i] = new BoardButton(_board.getPiece(j,7-i));
					_squares[j][7-i].getButton().addMouseListener(_listener);
				}else{
					_squares[j][7-i] = new BoardButton(new Piece(null,this.getGame(),j,7-i));
					_squares[j][7-i].getButton().addMouseListener(_listener);
				}
				_frame.add(_squares[j][7-i].getButton());
			}
		}
		_frame.setLayout(new GridLayout(8,8));
		_frame.pack();
		_frame.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		Game game = new Game();
		BoardWindow window = new BoardWindow(game);
		
	}
}
