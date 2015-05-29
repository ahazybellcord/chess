package model;

import java.awt.Point;

public class Knight extends Piece{
	
	public Knight(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	@Override
	public void setPossibleMoves() {
		int[] ek = {-1, 1};
		int[] dusra = {-2,2};
		for(int i : ek) {
			for(int j : dusra) {
				if(this.getLocation().x+i>-1 && this.getLocation().x+i<8 && this.getLocation().y+j>-1 && this.getLocation().y+j<8) {
					if(checkMove(this.getLocation().x+i,this.getLocation().y+j)) {
						this.getPossibleMoves().add(new Point(this.getLocation().x+i,this.getLocation().y+j));
					}
				}
				if(this.getLocation().x+j>-1 && this.getLocation().x+j<8 && this.getLocation().y+i>-1 && this.getLocation().y+i<8) {
					if(checkMove(this.getLocation().x+j,this.getLocation().y+i)) {
						this.getPossibleMoves().add(new Point(this.getLocation().x+j,this.getLocation().y+i));
					}
				}
			}
		}

	}
	
	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2658"; }
		else { return "\u265e" ; }
	}
	

}
