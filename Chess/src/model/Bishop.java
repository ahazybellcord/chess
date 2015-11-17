package model;

import java.awt.*;

public class Bishop extends Piece{
	
	public Bishop(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(3);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		int x, y;
		//check first quadrant
		for(int i=1; i<8; i++) {
			x = this.getLocation().x+i;
			y = this.getLocation().y-i;
			if(x<8 && y>-1) {
				if(checkMove(x,y)) {
					this.getPossibleMoves().add(new Point(x,y));
					if(!this.getGame().getBoard().isEmpty(x,y)) break;
				} else break;
			} else break;
		}
		//check second quadrant
		for(int i=1; i<8; i++) {
			x = this.getLocation().x-i;
			y = this.getLocation().y-i;
			if(x>-1 && y>-1) {
				if(checkMove(x,y)) {
					this.getPossibleMoves().add(new Point(x,y));
					if(!this.getGame().getBoard().isEmpty(x,y)) break;
				} else break;
			} else break;
		}
		//check third quadrant
		for(int i=1; i<8; i++) {
			x = this.getLocation().x-i;
			y = this.getLocation().y+i;
			if(x>-1 && y<8) {
				if(checkMove(x,y)) {
					this.getPossibleMoves().add(new Point(x,y));
					if(!this.getGame().getBoard().isEmpty(x,y)) break;
				} else break;
			} else break;
		}
		//check fourth quadrant
		for(int i=1; i<8; i++) {
			x = this.getLocation().x+i;
			y = this.getLocation().y+i;
			if(x<8 && y<8) {
				if(checkMove(x,y)) {
					this.getPossibleMoves().add(new Point(x,y));
					if(!this.getGame().getBoard().isEmpty(x,y)) break;
				} else break;
			} else break;
		}
	}
	
	@Override
	public String getUnicode() {
		return this.getColor() ? "\u2657" : "\u265d";
	}
	
	@Override
	public String getSymbol() {
		return "B";
	}

}
