package ui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import model.Piece;

public class InfoWindow extends JPanel{
	private Game _game;
	private JPanel _whitePanel;
	private JPanel _blackPanel;
	
	public InfoWindow(Game game){
		_game = game;
		_whitePanel = new JPanel();
		this.setLayout(new GridLayout(1,2,15,2));
		_whitePanel.setLayout(new BoxLayout(_whitePanel, BoxLayout.Y_AXIS));
		JLabel myLabel = new JLabel("White   ");
		myLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			_whitePanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		this.add(_whitePanel);
		
		_blackPanel = new JPanel();
		_blackPanel.setLayout(new BoxLayout(_blackPanel, BoxLayout.Y_AXIS));
		JLabel blackLabel = new JLabel("Black   ");
		blackLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			_blackPanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		this.add(_blackPanel);
		
	}
	
	public void update(){
		_whitePanel.removeAll();
		JLabel myLabel = new JLabel("White   ");
		myLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		String whitePieces = "\n";
		for(Piece p: _game.getCapturedPieces()){
			if(p.getColor()){
				whitePieces = whitePieces + p.getUnicode() + " ";
			}
		}
		_whitePanel.add(new JLabel(whitePieces));

		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			_whitePanel.add(new JLabel(_game.getMoves().get(i)));
		}
		
		
		_blackPanel.removeAll();
		JLabel blackLabel = new JLabel("Black   ");
		blackLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		String blackPieces = "\n";
		for(Piece p: _game.getCapturedPieces()){
			if(!p.getColor()){
				blackPieces = blackPieces + p.getUnicode() + " ";
			}
		}
		_blackPanel.add(new JLabel(blackPieces));
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			_blackPanel.add(new JLabel(_game.getMoves().get(i)));
		}
		
	}
}
