package ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Game;

public class BoardWindow extends JPanel {
	private Game _game;
	private BoardButton[][] _buttons;
	
	public BoardWindow(Game game) {
		_game = game;
		_buttons = new BoardButton[8][8];
		this.setLayout(new GridLayout(8,8,2,2));
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				_buttons[i][j] = new BoardButton(_game,i,j);
				this.add(_buttons[i][j]);
			}
		}
	}

}
