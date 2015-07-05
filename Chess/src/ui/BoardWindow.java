package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Game;
import model.Piece;

public class BoardWindow extends JPanel {
	private Game _game;
	private BoardButton[][] _buttons;
	private JButton[][] _sideButtons;
	private JButton[][] _bottomButtons;
	private Color _borderColor;
	private Color _altColor;
	private Color _mainColor;
	private Color _possibleMovesColor;
	private Color _highlightColor;
	private Color _possibleCaptureColor;
	private Boolean _beginnerMode;
	
	public BoardWindow(Game game) {
		_game = game;
		_beginnerMode = true;
		_buttons = new BoardButton[8][8];
		_mainColor = new JButton().getBackground();
		_borderColor = Color.GRAY;
		_altColor = new Color(209,236,237);
		_possibleMovesColor = Color.GRAY;
		_highlightColor = Color.BLUE;
		_possibleCaptureColor = new Color(131,146,159);
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(8,1,2,2));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_sideButtons = new JButton[1][8];
		for(int i = 0; i<8; i++){
			_sideButtons[0][i] = new JButton("" + (8-i));
			_sideButtons[0][i].setPreferredSize(new Dimension(20,80));
			_sideButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
			_sideButtons[0][i].setBorder(new EmptyBorder(1, 1, 1, 1));
			sidePanel.add(_sideButtons[0][i]);
		}
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(8,8,0,0));
		boolean backgroundColor = true;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				_buttons[j][i] = new BoardButton(_game,j,i);
				_buttons[j][i].setPreferredSize(new Dimension(80,80));
				_buttons[j][i].setBorder(new LineBorder(_borderColor, 0));
				if(backgroundColor){
					backgroundColor = !backgroundColor;
				}
				else{
					_buttons[j][i].setBackground(_altColor);
					backgroundColor = !backgroundColor;
				}
				if(j==7){
					backgroundColor = !backgroundColor;
				}
				_buttons[j][i].setOpaque(true);
				boardPanel.add(_buttons[j][i]);
			}
		}
		
		JPanel topHalf = new JPanel();
		topHalf.add(sidePanel);
		topHalf.add(boardPanel);
		
		JPanel bottomHalf = new JPanel();
		JButton emptyButton = new JButton();
		emptyButton.setPreferredSize(new Dimension(20,20));
		emptyButton.setBorder(new EmptyBorder(1,1,1,1));
	    bottomHalf.add(emptyButton);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,8,2,2));
		_bottomButtons = new JButton[1][8];
		for(int i = 0; i<8; i++){
			_bottomButtons[0][i] = new JButton("" + (char)('a'+i));
			_bottomButtons[0][i].setPreferredSize(new Dimension(80,30));
			_bottomButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
			_bottomButtons[0][i].setBorder(new EmptyBorder(1,1,1,1));
			bottomPanel.add(_bottomButtons[0][i]);
		}
		bottomHalf.add(bottomPanel);
		
		this.add(topHalf);
		this.add(bottomHalf);
	}
	
	public void update(){
		if(_game.getCurrentPlayer()){
			boolean backgroundColor = true;
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(!_game.getBoard().isEmpty(j, i)){								
				       _buttons[j][i].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[j][i].setFont(new Font("Times New Roman", Font.PLAIN, 40));
				       _buttons[j][i].setForeground(Color.BLACK);
					}
					else{
						_buttons[j][i].setText("");
					}
					if(backgroundColor){
						_buttons[j][i].setBackground(_mainColor);
						backgroundColor = !backgroundColor;
					}
					else{
						_buttons[j][i].setBackground(_altColor);
						backgroundColor = !backgroundColor;
					}
					if(j==7){
						backgroundColor = !backgroundColor;
					}
				}
			}
			
			if(_game.getPreviousClick()!=null){
				Piece p = _game.getPreviousClick();
				_buttons[p.getLocation().x][p.getLocation().y].setForeground(_highlightColor);
				if(_beginnerMode){
					for(Point k: p.getPossibleMoves()){
						_buttons[k.x][k.y].setForeground(_possibleCaptureColor);
						if(_game.getBoard().isEmpty(k.x, k.y)){
							_buttons[k.x][k.y].setText("\u2B24");
							_buttons[k.x][k.y].setFont(new Font("Times New Roman", Font.PLAIN, 12));
							_buttons[k.x][k.y].setOpaque(true);
							_buttons[k.x][k.y].setForeground(Color.BLACK);
						}
					}
				}
				
			}
			
			for(int i = 0; i<8; i++){
				_bottomButtons[0][i].setText("" + (char)('a'+i));
				_bottomButtons[0][i].setPreferredSize(new Dimension(80,30));
				_bottomButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
				_bottomButtons[0][i].setBorder(new EmptyBorder(1,1,1,1));
			}
			for(int i = 0; i<8; i++){
				_sideButtons[0][i].setText("" + (8-i));
				_sideButtons[0][i].setPreferredSize(new Dimension(20,80));
				_sideButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
				_sideButtons[0][i].setBorder(new EmptyBorder(1, 1, 1, 1));
			}
		}
		else{
			boolean backgroundColor = false; 
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(j==0){
						backgroundColor = !backgroundColor;
					}
					if(backgroundColor){
						_buttons[7-j][7-i].setBackground(_mainColor);
						backgroundColor = !backgroundColor;
					}
					else{
						_buttons[7-j][7-i].setBackground(_altColor);
						backgroundColor = !backgroundColor;
					}
					
					if(!_game.getBoard().isEmpty(j, i)){		
				       _buttons[7-j][7-i].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[7-j][7-i].setFont(new Font("Times New Roman", Font.PLAIN, 40));
				       _buttons[7-j][7-i].setForeground(Color.BLACK);
					}
					else{
						_buttons[7-j][7-i].setText("");
					}
				}
			}
			
			if(_game.getPreviousClick()!=null){
				Piece p = _game.getPreviousClick();
				_buttons[7-p.getLocation().x][7-p.getLocation().y].setForeground(_highlightColor);
				if(_beginnerMode){
					for(Point k: p.getPossibleMoves()){
						_buttons[7-k.x][7-k.y].setForeground(_possibleCaptureColor);
						if(_game.getBoard().isEmpty(k.x, k.y)){
							_buttons[7-k.x][7-k.y].setText("\u2B24");
							_buttons[7-k.x][7-k.y].setFont(new Font("Times New Roman", Font.PLAIN, 12));
							_buttons[7-k.x][7-k.y].setForeground(Color.BLACK);
							_buttons[7-k.x][7-k.y].setOpaque(true);
						}
					}
				}
				
			}
			
			for(int i = 0; i<8; i++){
				_bottomButtons[0][i].setText("" + (char)('h'-i));
				_bottomButtons[0][i].setPreferredSize(new Dimension(80,30));
				_bottomButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
				_bottomButtons[0][i].setBorder(new EmptyBorder(1,1,1,1));
			}
			for(int i = 0; i<8; i++){
				_sideButtons[0][i].setText("" + (i+1));
				_sideButtons[0][i].setPreferredSize(new Dimension(20,80));
				_sideButtons[0][i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
				_sideButtons[0][i].setBorder(new EmptyBorder(1, 1, 1, 1));
			}
		}
	}
	
	public void theme(String s){
		if(s.equals("Wooden")){
			_altColor = new Color(222, 185, 119);
			_mainColor = new Color(247, 206, 132);
		}
		else if(s.equals("Civil War")){
			_altColor = new Color(92, 129, 152);
			_mainColor = new Color(140, 150, 155);
		}
		else if(s.equals("Aegean")){
			_altColor = new Color(94, 147, 207);
			_mainColor = new Color(127, 176, 231);
		}
		else if(s.equals("Burnt")){
			_altColor = new Color(153, 142, 17);
			_mainColor = new Color(203, 189, 22);
		}
		else if(s.equals("WinBoard")){
			_altColor = new Color(112, 160, 104);
			_mainColor = new Color(200, 192, 96);
		}
		else if(s.equals("Tournament")){
			_altColor = new Color(103, 134, 66);
			_mainColor = new Color(236, 233, 200);
		}
		else{
			_altColor = new Color(209,236,237);
			_mainColor = new JButton().getBackground();
		}
		update();
	}
	public void beginnerMode(String s){
		if(s.equals("Off")){
			_beginnerMode = false;
		}
		else{
			_beginnerMode = true;
		}
		update();
	}

}
