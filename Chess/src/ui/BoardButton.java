package ui;

import model.Game;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BoardButton extends JButton {
    private final Game _game;
	private Piece _piece;
    private final int _x;
    private final int _y;

	public BoardButton(Game game, int x, int y) {
		_game = game;
		_x = x;
		_y = y;
		if(!game.getBoard().isEmpty(x, y)){
			_piece = game.getBoard().getPiece(x, y);
			this.setText(_piece.getUnicode());
			int FONT_SIZE = 40;
			this.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
		}

		this.addActionListener(new BoardButtonHandler());
	}

// --Commented out by Inspection START (11/17/15, 4:18 PM):
//	public Piece getPiece() {
//		return _piece;
//	}
// --Commented out by Inspection STOP (11/17/15, 4:18 PM)

	private class BoardButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_game.getCurrentPlayer())
				_game.handleClick(_x, _y);
			else
				_game.handleClick(7-_x, 7-_y);
		}
	}
	
}
