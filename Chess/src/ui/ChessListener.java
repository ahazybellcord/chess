package ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.*;

import javax.swing.*;

import model.Piece;

public class ChessListener implements MouseListener {
	private Game _game;
	private BoardWindow _window;
	private BoardButton _src;
	private BoardButton _dst;
	
	public ChessListener(Game game, BoardWindow window) {
		_game = game;
		_window = window;
		_src = null; _dst = null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JButton clicked = (JButton) e.getComponent();
		for(int i=0; i<7; i++) {
			for(int j=0; j<7; j++){
				if(_window.getBoardButtons()[i][j].getButton().equals(clicked)) {
					System.out.println("CLICKED i="+i+" j="+j+"");
					//need to figure out how to implement empty square on board
					//color is not white/black, true/false
					//how to check for null color? use String to represent color?
					//use "white" "black" or " ".
					if(!_window.getBoardButtons()[i][j].getPiece().getColor().equals(" ")){
						System.out.println("color is "+_window.getBoardButtons()[i][j].getPiece().getColor());
						if(_window.getBoardButtons()[i][j].getPiece().getColor().equals(_game.getCurrentPlayer())) {
							if(_src==null) {
								_src = _window.getBoardButtons()[i][j];
								System.out.println("Source set to "+_window.getBoardButtons()[i][j].getButton().getText());
							}else{
								_dst = _window.getBoardButtons()[i][j];
								_src.getPiece().tryMove((int)_dst.getLocation().getX(), (int)_dst.getLocation().getY());
								System.out.println("tried move");
								_window.update(_game);
								_src = null; _dst = null;
							}
						}
					}else{
						System.out.println("this doesn't happen");
						if(_src!=null) {
							_dst = _window.getBoardButtons()[i][j];
							_src.getPiece().tryMove((int)_dst.getLocation().getX(), (int)_dst.getLocation().getY());
							System.out.println("tried move");
							_window.update(_game);
							_src = null; _dst = null;
						}
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
