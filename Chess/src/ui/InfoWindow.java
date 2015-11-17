package ui;

import model.Game;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InfoWindow extends JPanel{
	private final Game _game;
	private final JPanel _whitePanel;
	private final JPanel _blackPanel;
	private final BoardWindow _boardWindow;
	
	public InfoWindow(Game game, BoardWindow boardWindow){
		_game = game;
		_boardWindow = boardWindow;
		_whitePanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_whitePanel.setLayout(new BoxLayout(_whitePanel, BoxLayout.Y_AXIS));
		JPanel topPanel = new JPanel(new GridLayout(1,2,15,2));
		JLabel myLabel = new JLabel(_game.getNames().get(0) +"   ");
		myLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_whitePanel.add(myLabel);
		for(int i = 0; i<_game.getMoves().size(); i = i+2){
			_whitePanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		topPanel.add(_whitePanel);
		
		
		_blackPanel = new JPanel();
		_blackPanel.setLayout(new BoxLayout(_blackPanel, BoxLayout.Y_AXIS));
		JLabel blackLabel = new JLabel(_game.getNames().get(1) +"   ");
		blackLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		_blackPanel.add(blackLabel);
		for(int i = 1; i<_game.getMoves().size(); i = i+2){
			_blackPanel.add(new JLabel(_game.getMoves().get(i)));	
		}
		topPanel.add(_blackPanel);
		JScrollPane myScroller = new JScrollPane(new JPanel());
		myScroller.add(topPanel);
		String[] options = {"Classic","Wooden","Civil War", "Aegean", "Burnt","Tournament", "WinBoard"};
		JComboBox mybox = new JComboBox(options);
		mybox.setSelectedItem(0);
		mybox.addActionListener(new ThemeHandler());
		String[] bmode = {"On", "Off"};
		JComboBox bbox = new JComboBox(bmode);
		bbox.setSelectedItem(0);
		bbox.addActionListener(new BeginnerHandler());
		JPanel optionPanel = new JPanel(new GridLayout(1,2,15,2));
		JLabel themeLabel = new JLabel("Theme");
		JLabel beginnerLabel = new JLabel("Beginner Mode");
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(themeLabel);
		bottomPanel.add(mybox);
		JPanel otherPanel = new JPanel();
		otherPanel.add(beginnerLabel);
		otherPanel.add(bbox);
		optionPanel.add(bottomPanel);
		optionPanel.add(otherPanel);
		optionPanel.setMaximumSize(new Dimension(450,350));
		this.add(optionPanel);
		this.add(topPanel);
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
	
	private class ThemeHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
	        String selected = (String)cb.getSelectedItem();
	        _boardWindow.theme(selected);
		}
	}
	private class BeginnerHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
	        String selected = (String)cb.getSelectedItem();
	        _boardWindow.beginnerMode(selected);
		}
	}
}
