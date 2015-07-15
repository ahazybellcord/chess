package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		_frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Object[] options = {"Save", "Don't Save", "Cancel"};
		    	int n = JOptionPane.showOptionDialog(_frame, 
			            "Do you want to save the game?", "Exit", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		        if(n ==1){
		        	System.exit(1);
		        }
		        else if(n == 0){
		        	JFileChooser chooser = new JFileChooser();
		        	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        	int result = chooser.showSaveDialog(_frame);
		        	if(result == chooser.APPROVE_OPTION){
		        		File myFile = chooser.getSelectedFile();
		        		if(myFile.getAbsolutePath().lastIndexOf('.') == -1){
		        			myFile = new File(myFile.getAbsolutePath()+".pgn");
		        			_game.save(myFile);
		        		}
		        	}
		        	else{
		        		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        	}
		        }
		        else{
		        	_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        }
		    }
		});
		int n = -1;
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
		if(n==-1){
			System.exit(1);
		}
		//		Haven't implemented Load Game yet
		else if(n==1){
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PGN and Chess files", "pgn", "chess");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				System.out.println("We need to open this file " + chooser.getSelectedFile().getName());
			}
			else{
				System.exit(1);
			}
		}
		Object[] options1 = {"Human vs. Human",
		"Human vs. Computer", "Computer vs. Human"};
		int o = JOptionPane.showOptionDialog(_frame,
				"Please select one ",
				"Chess",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options1,
				options1[0]);
		if(o==-1){
			System.exit(1);
		}
		else if(o==0){
			JTextField white = new JTextField();
			JTextField black = new JTextField();
			Object[] message = {
					"Please enter the names or click cancel to skip",
					"White:", white,
					"Black:", black
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				while(white.getText().equals("") || black.getText().equals("")){
					option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(option == JOptionPane.CANCEL_OPTION){
						white.setText("White");
						black.setText("Black");
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
		}
		else if (o==1){
			JTextField white = new JTextField();
			Object[] message = {
					"Please enter your name or click cancel to skip",
					"White:", white
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				while(white.getText().equals("")){
					option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(option == JOptionPane.CANCEL_OPTION){
						white.setText("White");
						break;
					}
				}
				_game.setNames(white.getText(), "Computer");
			}
			else if(option == JOptionPane.CANCEL_OPTION){
				_game.setNames("White", "Computer");
			}
			_game.setAI(false);
		}
		else {
			JTextField black = new JTextField();
			Object[] message = {
					"Please enter your name or click cancel to skip",
					"Black:", black
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				while(black.getText().equals("")){
					option = JOptionPane.showConfirmDialog(null, message, "Chess", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if(option == JOptionPane.CANCEL_OPTION){
						black.setText("Black");
						break;
					}
				}
				_game.setNames("Computer", black.getText());
			}
			else if(option == JOptionPane.CANCEL_OPTION){
				_game.setNames("Computer", "Black");
			}
			_game.setAI(true);
		}
		_boardWindow = new BoardWindow(_game);
		_infoWindow = new InfoWindow(_game, _boardWindow);
		_infoWindow.setBounds(0, 0, 400, 800);
		_infoWindow.setPreferredSize(new Dimension(400,800));
		JScrollPane j = new JScrollPane(_infoWindow.getComponent(1), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_frame.add(_boardWindow, BorderLayout.WEST);
		_frame.add(_infoWindow, BorderLayout.EAST);
		_infoWindow.add(j);
		_frame.pack();
		_frame.setVisible(true);
		_frame.setMaximumSize(new Dimension(800,800));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(_game.isAI()){
			_game.getAI().move();
		}
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
			//_game.aiMove();
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
