package model;

import java.awt.Point;

public class Bishop extends Piece{
	
	public Bishop(boolean color, Game game, Point location) {
		super(color,game,location);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		//check first quadrant
		for(int i=1; i<8; i++) {
			if(this.getLocation().x+i<8 && this.getLocation().y-i>-1) {
				if(checkMove(this.getLocation().x+i,this.getLocation().y-i)) {
					this.getPossibleMoves().add(new Point(this.getLocation().x+i,this.getLocation().y-i));
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+i, this.getLocation().y-i)){
						break;
					}
				}
				else{
					break;
				}
			} else break;
		}
		//check second quadrant
		for(int i=1; i<8; i++) {
			if(this.getLocation().x-i>-1 && this.getLocation().y-i>-1) {
				if(checkMove(this.getLocation().x-i,this.getLocation().y-i)) {
					this.getPossibleMoves().add(new Point(this.getLocation().x-i,this.getLocation().y-i));
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-i, this.getLocation().y-i)){
						break;
					}
				}
				else{
					break;
				}
			} else break;
		}
		//check third quadrant
		for(int i=1; i<8; i++) {
			if(this.getLocation().x-i>-1 && this.getLocation().y+i<8) {
				if(checkMove(this.getLocation().x-i,this.getLocation().y+i)) {
					this.getPossibleMoves().add(new Point(this.getLocation().x-i,this.getLocation().y+i));
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-i, this.getLocation().y+i)){
						break;
					}
				}
				else{
					break;
				}
			} else break;
		}
		//check fourth quadrant
		for(int i=1; i<8; i++) {
			if(this.getLocation().x+i<8 && this.getLocation().y+i<8) {
				if(checkMove(this.getLocation().x+i,this.getLocation().y+i)) {
					this.getPossibleMoves().add(new Point(this.getLocation().x+i,this.getLocation().y+i));
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+i, this.getLocation().y+i)){
						break;
					}
				}
				else{
					break;
				}
			} else break;
		}

	}

	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2657"; }
		else { return "\u265d" ; }
	}
	
	@Override
	public String getSymbol() {
		return "B";
	}

	

}
