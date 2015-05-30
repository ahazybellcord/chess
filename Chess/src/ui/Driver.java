package ui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Game;

public class Driver implements Runnable, Observer {
	private JFrame _frame;
	private Game _game;
	private BoardWindow _window;
	
	public Driver() {
		_game = new Game();
		_game.addObserver(this);
		
	}

	@Override
	public void run() {
		_frame = new JFrame("Chess");
		_window = new BoardWindow(_game);
		_frame.add(_window);
		_frame.pack();
		_frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Driver());
	}

	@Override
	public void update(Observable o, Object arg) {
		_window.update();
	}

}
