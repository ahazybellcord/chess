package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Game;
import model.Piece;

public class BoardWindow extends JPanel {
	private Game _game;
	private BoardButton[][] _buttons;
	private JButton[][] _sideButtons;
	private JButton[][] _bottomButtons;
	
	public BoardWindow(Game game) {
		_game = game;
		_buttons = new BoardButton[8][8];
		
		
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
		boardPanel.setLayout(new GridLayout(8,8,2,2));
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				_buttons[j][i] = new BoardButton(_game,j,i);
				_buttons[j][i].setPreferredSize(new Dimension(80,80));
				_buttons[j][i].setBorder(new LineBorder(Color.GRAY, 1));
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
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(!_game.getBoard().isEmpty(j, i)){								
				       _buttons[j][i].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[j][i].setFont(new Font("Times New Roman", Font.PLAIN, 40));
				       _buttons[j][i].setForeground(Color.DARK_GRAY);
				       _buttons[j][i].setBackground(Color.gray);
				       _buttons[j][i].setOpaque(false);			
					}
					else{
						_buttons[j][i].setText("");
						_buttons[j][i].setBackground(Color.gray);
						_buttons[j][i].setOpaque(false);
					}
				}
			}
			
			if(_game.getPreviousClick()!=null){
				Piece p = _game.getPreviousClick();
				_buttons[p.getLocation().x][p.getLocation().y].setForeground(Color.BLUE);
				for(Point k: p.getPossibleMoves()){
					_buttons[k.x][k.y].setForeground(Color.RED);
					if(_game.getBoard().isEmpty(k.x, k.y)){
						_buttons[k.x][k.y].setBackground(Color.LIGHT_GRAY);
						_buttons[k.x][k.y].setOpaque(true);
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
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(!_game.getBoard().isEmpty(j, i)){		
				       _buttons[7-j][7-i].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[7-j][7-i].setFont(new Font("Times New Roman", Font.PLAIN, 40));
				       _buttons[7-j][7-i].setForeground(Color.DARK_GRAY);
				       _buttons[7-j][7-i].setBackground(Color.gray);
				       _buttons[7-j][7-i].setOpaque(false);		
					}
					else{
						_buttons[7-j][7-i].setText("");
						_buttons[7-j][7-i].setBackground(Color.gray);
						_buttons[7-j][7-i].setOpaque(false);
					}
				}
			}
			
			if(_game.getPreviousClick()!=null){
				Piece p = _game.getPreviousClick();
				_buttons[7-p.getLocation().x][7-p.getLocation().y].setForeground(Color.BLUE);
				for(Point k: p.getPossibleMoves()){
					_buttons[7-k.x][7-k.y].setForeground(Color.RED);
					if(_game.getBoard().isEmpty(k.x, k.y)){
						_buttons[7-k.x][7-k.y].setBackground(Color.LIGHT_GRAY);
						_buttons[7-k.x][7-k.y].setOpaque(true);
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

}
