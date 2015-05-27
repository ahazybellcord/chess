package model;

import java.awt.Point;

public class Bishop extends Piece{
	private String _name;

	public Bishop(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "bishop";
	}
	
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		//check first quadrant
		for(int i=1; i<(8-super.x()); i++){
			if(!checkMove(super.x()+i,super.y()+i)) { break; }
			else {
				super.getPossibleMoves().add(new Point(super.x()+i,super.y()+i));
			}
		}
		//check second quadrant
		for(int i=1; i<Math.min(super.x(),8-super.y()); i++){
			if(!checkMove(super.x()-i,super.y()+i)) { break; }
			else{
				super.getPossibleMoves().add(new Point(super.x()-i,super.y()+i));
			}
		}
		//check third quadrant
		for(int i=1; i<Math.min(super.x(),super.y()); i++){
			if(!checkMove(super.x()-i,super.y()-i)) { break;}
			else {
				super.getPossibleMoves().add(new Point(super.x()-i,super.y()-i));
			}
		}
		//check fourth quadrant
		for(int i=1; i<Math.min(8-super.x(),super.y()); i++){
			if(!checkMove(super.x()+i,super.y()-i)){ break; }
			else{
				super.getPossibleMoves().add(new Point(super.x()+i,super.y()-i));
			}
		}
	}
}
