package ui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.Game;

public class Driver implements Runnable, Observer {
	private JFrame _frame;
	private Game _game;
	private BoardWindow _window;
	private InfoWindow _infoWindow;
	
	public Driver() {
		_game = new Game();
		_game.addObserver(this);
		
	}

	@Override
	public void run() {
		_frame = new JFrame("Chess " + "(White to move)");
		_window = new BoardWindow(_game);
		_infoWindow = new InfoWindow(_game);
		_frame.add(_window, BorderLayout.WEST);
		_frame.add(_infoWindow, BorderLayout.EAST);
		_frame.pack();
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Driver());
	}

	@Override
	public void update(Observable o, Object arg) {
		_window.update();
		_infoWindow.update();
		if(_game.getCurrentPlayer()){
			_frame.setTitle("Chess (White to move)" );
		}
		else{
			_frame.setTitle("Chess (Black to move)");
		}
		_frame.pack();
		
	}

}
