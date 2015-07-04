package ui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Game;

public class Driver implements Runnable, Observer {
	private JFrame _frame;
	private Game _game;
	private BoardWindow _boardWindow;
	private InfoWindow _infoWindow;

	public Driver(String[] args) {
		if(args.length==0 || args.length==2){
			_game = new Game(args);
			_game.addObserver(this);
		}
		else{
			System.err.println("You can input exactly two names!");
			System.exit(1);
		}

	}

	@Override
	public void run() {
		_frame = new JFrame("Chess (White to move)");
		_boardWindow = new BoardWindow(_game);
		_infoWindow = new InfoWindow(_game);
		_frame.add(_boardWindow, BorderLayout.WEST);
		_frame.add(_infoWindow, BorderLayout.EAST);
		_frame.pack();
		_frame.setVisible(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Driver(args));
	}

	@Override
	public void update(Observable o, Object arg) {
		if(_game.getPawnPromotion()){
			int n = -1;
			while(n==-1){
				Object[] options = {"Knight",
						"Rook",
						"Queen", "Bishop"};
				        n = JOptionPane.showOptionDialog(_frame,
						"Please select one ",
						"Pawn Promotion",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE,
						null,
						options,
						options[2]);
			}
			_game.setPromotionChoice(n);

		}
		_boardWindow.update();
		_infoWindow.update();
		if(_game.isCheckmate()){
			if(_game.getCurrentPlayer()){
				_frame.setTitle("Chess - White Wins!");
				JOptionPane.showMessageDialog(_frame,
				        "White Wins!",
				        "Checkmate",
				        JOptionPane.INFORMATION_MESSAGE);
				
			}
			else{
				_frame.setTitle("Chess - Black Wins!");
				JOptionPane.showMessageDialog(_frame,
				        "Black Wins!",
				        "Checkmate",
				        JOptionPane.INFORMATION_MESSAGE);
				
			}
			_frame.setEnabled(false);
			
			
		}
		else{
			if(_game.isInCheck()){
				if(_game.getCurrentPlayer()){
					_frame.setTitle("Chess (White to move) Check!" );
				}
				else{
					_frame.setTitle("Chess (Black to move) Check!");
				}
			}else{
				if(_game.getCurrentPlayer()){
					_frame.setTitle("Chess (White to move)" );
				}
				else{
					_frame.setTitle("Chess (Black to move)");
				}
			}
			
		}
		
		_frame.pack();
	}

}
