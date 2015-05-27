package ui;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Game;

public class Board extends JPanel {
	private Game _game;
	private JButton[][] _buttons;
	
	public Board(Game game) {
		_game = game;
	}

}
