package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import model.Game;
import model.Piece;

public class BoardButton extends JButton {
	private Piece _piece;
    private int _x;
    private int _y;
    private Game _game;
	public BoardButton(Game game, int x, int y) {
		if(!game.getBoard().isEmpty(x, y)){
			_piece = game.getBoard().getPiece(x, y);
			this.setText(_piece.getUnicode());
			this.setFont(new Font("Arial", Font.PLAIN, 40));
		}
		_x = x;
		_y = y;
		_game = game;
		this.addActionListener(new BoardButtonHandler());
	}

	public Piece getPiece() {
		return _piece;
	}
	
	public class BoardButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_game.getCurrentPlayer()){
				_game.handleClick(_x, _y);
			}
			else{
				_game.handleClick(_x, Math.abs(_y-7));
			}
			
		}
		
	}
}
