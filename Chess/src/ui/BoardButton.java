package ui;

import java.awt.Font;

import javax.swing.JButton;

import model.Game;
import model.Piece;

public class BoardButton extends JButton {
	private Piece _piece;

	public BoardButton(Game game, int x, int y) {
		if(!game.getBoard().isEmpty(x, y)){
			_piece = game.getBoard().getPiece(x, y);
			this.setText(_piece.getUnicode());
			this.setFont(new Font("Arial", Font.PLAIN, 40));
		}
	}

	public Piece getPiece() {
		return _piece;
	}
}
