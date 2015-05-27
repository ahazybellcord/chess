package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Game;

public class Driver implements Runnable {
	private JFrame _frame;
	private Game _game;
	
	public Driver() {
		_game = new Game();
		
	}

	@Override
	public void run() {
		_frame = new JFrame("Chess");
		BoardWindow window = new BoardWindow(_game);
		_frame.add(window);
		_frame.pack();
		_frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Driver());
	}

}
