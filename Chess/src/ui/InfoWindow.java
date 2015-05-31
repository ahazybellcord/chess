package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;

public class InfoWindow extends JPanel{
	private Game _game;
	private JPanel _whitePanel;
	private JPanel _blackPanel;
	public InfoWindow(Game game){
		_game = game;
		_whitePanel = new JPanel();
		_whitePanel.setLayout(new BoxLayout(_whitePanel, BoxLayout.Y_AXIS));
		JLabel myLabel = new JLabel("White   ");
		myLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			JLabel whiteMove = new JLabel();
			String whiteMoves = _game.getMoves().get(i);
			whiteMove.setText(whiteMoves);
			_whitePanel.add(whiteMove);	
		}
		this.add(_whitePanel, BorderLayout.WEST);
		_blackPanel = new JPanel();
		_blackPanel.setLayout(new BoxLayout(_blackPanel, BoxLayout.Y_AXIS));
		JLabel blackLabel = new JLabel("Black");
		blackLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			JLabel blackMove = new JLabel();
			String blackMoves = _game.getMoves().get(i);
			blackMove.setText(blackMoves);
			_blackPanel.add(blackMove);	
		}
		this.add(_blackPanel, BorderLayout.EAST);
		
	}
	
	public void update(){
		_whitePanel.removeAll();
		JLabel myLabel = new JLabel("White   ");
		myLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			JLabel whiteLabel = new JLabel();
			String whiteMoves =_game.getMoves().get(i);
			whiteLabel.setText(whiteMoves);
			_whitePanel.add(whiteLabel);
		}
		_blackPanel.removeAll();
		JLabel blackLabel = new JLabel("Black");
		blackLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			JLabel bLabel = new JLabel();
			String blackMoves =_game.getMoves().get(i);
			bLabel.setText(blackMoves);
			_blackPanel.add(bLabel);
		}
		
		
	}
}
