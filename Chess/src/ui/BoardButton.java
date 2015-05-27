package ui;

import java.awt.Point;
import java.util.List;

import javax.swing.JButton;

import model.*;

public class BoardButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6529210452312641928L;
	private JButton _button;
	private Piece _piece;
	private Point _location;
	private int _x;
	private int _y;
	
	public BoardButton(Piece piece){
		_piece = piece;
		if(piece!=null){
			_button = new JButton(Piece.getName(piece));
			_location = piece.getLocation();
			_x = (int)_location.getX(); _y = (int)_location.getY();
		}else{
			_piece = null;
			_button = new JButton(" ");
		}
		
	}

	public void update(Piece piece) {
		_piece = piece;
		if(piece!=null){
			_button.setText(Piece.getName(piece));
			_location = piece.getLocation();
			_x = (int)_location.getX(); _y = (int)_location.getY();
		}else{
			_piece = null;
			_button = new JButton(" ");
		}
	}
	
	public int x() {
		return _x;
	}
	
	public int y() {
		return _y;
	}
	
	public Point getLocation(){
		return _location;
		
	}
	
	public JButton getButton() {
		return _button;
	}
	
	public Piece getPiece() {
		return _piece;
	}
	
	public void setPiece(Piece piece) {
		_piece = piece;
	}

}
