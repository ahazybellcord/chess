package model;

import java.awt.Point;

public class Knight extends Piece{
	
	public Knight(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(3);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		int[] pehla = {-1, 1};
		int[] dusra = {-2,2};
		int x, y;
		for(int i : pehla) {
			for(int j : dusra) {
				x = this.getLocation().x;
				y = this.getLocation().y;
				if(x+i>-1 && x+i<8 && y+j>-1 && y+j<8) {
					if(checkMove(x+i,y+j)) {
						this.getPossibleMoves().add(new Point(x+i,y+j));
					}
				}
				if(x+j>-1 && x+j<8 && y+i>-1 && y+i<8) {
					if(checkMove(x+j,y+i)) {
						this.getPossibleMoves().add(new Point(x+j,y+i));
					}
				}
			}
		}
	}
	
	@Override
	public String getUnicode() {
		return this.getColor() ? "\u2658" : "\u265e" ;
	}
	
	@Override
	public String getSymbol() {
		return "N";
	}

}
