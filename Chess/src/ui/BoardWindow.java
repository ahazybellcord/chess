package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Game;
import model.Piece;

public class BoardWindow extends JPanel {
	private Game _game;
	private BoardButton[][] _buttons;
	
	public BoardWindow(Game game) {
		_game = game;
		_buttons = new BoardButton[8][8];
		this.setLayout(new GridLayout(8,8,2,2));
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				_buttons[j][i] = new BoardButton(_game,j,i);
				_buttons[j][i].setPreferredSize(new Dimension(100,100));
				_buttons[j][i].setBorder(new LineBorder(Color.GRAY, 1));
				this.add(_buttons[j][i]);
				
			}
		}
	}
	
	public void update(){
		if(_game.getCurrentPlayer()){
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(!_game.getBoard().isEmpty(j, i)){
								
				       _buttons[j][i].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[j][i].setFont(new Font("Arial", Font.PLAIN, 40));
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
						_buttons[k.x][k.y].setBackground(Color.DARK_GRAY);
						_buttons[k.x][k.y].setOpaque(true);


					}
				}
			}
		}
		else{
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(!_game.getBoard().isEmpty(j, i)){
								
				       _buttons[j][Math.abs(i-7)].setText(_game.getBoard().getPiece(j, i).getUnicode());
				       _buttons[j][Math.abs(i-7)].setFont(new Font("Arial", Font.PLAIN, 40));
				       _buttons[j][Math.abs(i-7)].setForeground(Color.DARK_GRAY);
				       _buttons[j][Math.abs(i-7)].setBackground(Color.gray);
				       _buttons[j][Math.abs(i-7)].setOpaque(false);
				       
								
					}
					else{
						_buttons[j][Math.abs(i-7)].setText("");
						_buttons[j][Math.abs(i-7)].setBackground(Color.gray);
						_buttons[j][Math.abs(i-7)].setOpaque(false);
						
					}
					
				}
			}
			if(_game.getPreviousClick()!=null){
				Piece p = _game.getPreviousClick();
				_buttons[p.getLocation().x][Math.abs(p.getLocation().y-7)].setForeground(Color.BLUE);
				for(Point k: p.getPossibleMoves()){
					_buttons[k.x][Math.abs(k.y-7)].setForeground(Color.RED);
					if(_game.getBoard().isEmpty(k.x, k.y)){
						_buttons[k.x][Math.abs(k.y-7)].setBackground(Color.DARK_GRAY);
						_buttons[k.x][Math.abs(k.y-7)].setOpaque(true);


					}
				}
			}
		}
			
		
		
			
		
		
		
		
	}

}
