package model;

import java.awt.Point;

public class Rook extends Piece {
	private String _name;
	private boolean _moved;

	public Rook(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "rook";
		_moved = false;
	}
	@Override
	public String getName() {
		return _name;
	}
	
	public void setMoved() {
		_moved = true;
	}
	
	public boolean wasMoved() {
		return _moved;
	}

	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		//check up
		for(int i=1; i<(8-super.y()); i++){
			if(!checkMove(super.x(),super.y()+i)) { break; }
			else {
				super.getPossibleMoves().add(new Point(super.x(),super.y()+i));
			}
		}
		//check down
		for(int i=1; i<super.y()+1; i++){
			if(!checkMove(super.x(),super.y()-i)) { break; }
			else {
				super.getPossibleMoves().add(new Point(super.x(),super.y()-i));
			}
		}
		//check left
		for(int i=1; i<super.x()+1; i++){
			if(!checkMove(super.x()-i,super.y())) { break; }
			else {
				super.getPossibleMoves().add(new Point(super.x()-i,super.y()));
			}
		}
		//check right
		for(int i=1; i<(8-super.x()); i++){
			if(!checkMove(super.x()+i,super.y())) { break; }
			else {
				super.getPossibleMoves().add(new Point(super.x()+i,super.y()));
			}
		}
	}

}
