package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import model.Game;
import model.Piece;

public class BoardButton extends JButton {
    private Game _game;	
	private Piece _piece;
    private int _x;
    private int _y;
    private static int FONT_SIZE = 40;
    
	public BoardButton(Game game, int x, int y) {
		_game = game;
		_x = x;
		_y = y;
		if(!game.getBoard().isEmpty(x, y)){
			_piece = game.getBoard().getPiece(x, y);
			this.setText(_piece.getUnicode());
			this.setFont(new Font("Times New Roman", Font.PLAIN, FONT_SIZE));
		}

		this.addActionListener(new BoardButtonHandler());
	}

	public Piece getPiece() {
		return _piece;
	}
	
	public class BoardButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(_game.getCurrentPlayer())
				_game.handleClick(_x, _y);
			else
				_game.handleClick(7-_x, 7-_y);
		}
	}
	
}
