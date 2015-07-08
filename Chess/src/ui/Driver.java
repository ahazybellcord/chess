package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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
		int n = -1;
		while(n==-1){
			Object[] options = {"New Game",
					"Load Game"};
			        n = JOptionPane.showOptionDialog(_frame,
					"Please select one ",
					"Chess",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					options,
					options[0]);
		}
//		Haven't implemented Load Game yet
		if(n==1){
			System.exit(1);
		}
		int o = -1;
		while(o==-1){
			Object[] options = {"Human vs. Human",
			"Human vs. Computer"};
	        o = JOptionPane.showOptionDialog(_frame,
			"Please select one ",
			"Chess",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			options,
			options[0]);
		}
		if(o==1){
			System.exit(1);
		}
		JTextField white = new JTextField();
		JTextField black = new JTextField();
		Object[] message = {
		    "White:", white,
		    "Black:", black
		};
        UIManager.put("OptionPane.minimumSize", new Dimension(400,200));
		int option = JOptionPane.showConfirmDialog(null, message, "Please enter the names or click cancel to skip", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
		    while(white.getText() == null || black.getText() == null){
		    	option = JOptionPane.showConfirmDialog(null, message, "Please enter the names", JOptionPane.OK_CANCEL_OPTION);
		    	if(option == JOptionPane.CANCEL_OPTION){
		    		break;
		    	}
		    }
		    _game.setNames(white.getText(), black.getText());
		}
		else if(option == JOptionPane.CANCEL_OPTION){
			_game.setNames("White", "Black");
		}
		else{
			System.exit(1);
		}
		
		
		
		_boardWindow = new BoardWindow(_game);
		_infoWindow = new InfoWindow(_game, _boardWindow);
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
			_game.setCheckmateFalse();
			_frame.setEnabled(false);
			
			
		}
		else if(_game.isStalemate()){
			_frame.setTitle("Chess - Stalemate!");
			JOptionPane.showMessageDialog(_frame,
			        "Stalemate, the game ends.",
			        "Stalemate",
			        JOptionPane.INFORMATION_MESSAGE);
			_game.setCheckmateFalse();
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
		if(_game.isEndGame()){
			if(_game.getCurrentPlayer()){
				_frame.setTitle("Chess - White Wins!");
			}
			else{
				_frame.setTitle("Chess - Black Wins!");
			}
		}
		
		_frame.pack();
	}

}
