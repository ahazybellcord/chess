package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import model.Piece;

public class InfoWindow extends JPanel{
	private Game _game;
	private JPanel _whitePanel;
	private JPanel _blackPanel;
	private BoardWindow _boardWindow;
	
	public InfoWindow(Game game, BoardWindow boardWindow){
		_game = game;
		_boardWindow = boardWindow;
		_whitePanel = new JPanel();
		this.setLayout(new GridLayout(2,2,15,2));
		_whitePanel.setLayout(new BoxLayout(_whitePanel, BoxLayout.Y_AXIS));
		JLabel myLabel = new JLabel(_game.getNames().get(0) +"   ");
		myLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			_whitePanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		this.add(_whitePanel);
		
		_blackPanel = new JPanel();
		_blackPanel.setLayout(new BoxLayout(_blackPanel, BoxLayout.Y_AXIS));
		JLabel blackLabel = new JLabel(_game.getNames().get(1) +"   ");
		blackLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			_blackPanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		this.add(_blackPanel);
		String[] options = {"Classic","Wooden","Civil War", "Aegean", "Burnt","Tournament", "WinBoard"};
		JComboBox mybox = new JComboBox(options);
		mybox.setSelectedItem(0);
		mybox.addActionListener(new ThemeHandler());
		String[] bmode = {"On", "Off"};
		JComboBox bbox = new JComboBox(bmode);
		bbox.setSelectedItem(0);
		bbox.addActionListener(new BeginnerHandler());
		JLabel themeLabel = new JLabel("Theme");
		JLabel beginnerLabel = new JLabel("Beginner Mode");
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(themeLabel);
		bottomPanel.add(mybox);
		JPanel otherPanel = new JPanel();
		otherPanel.add(beginnerLabel);
		otherPanel.add(bbox);
		this.add(bottomPanel);
		this.add(otherPanel);		
	}
	
	public void update(){
		_whitePanel.removeAll();
		JLabel myLabel = new JLabel(_game.getNames().get(0) +"   ");
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
		JLabel blackLabel = new JLabel(_game.getNames().get(1) +"   ");
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
	
	public class ThemeHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
	        String selected = (String)cb.getSelectedItem();
	        _boardWindow.theme(selected);
		}
	}
	public class BeginnerHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
	        String selected = (String)cb.getSelectedItem();
	        _boardWindow.beginnerMode(selected);
		}
	}
}
