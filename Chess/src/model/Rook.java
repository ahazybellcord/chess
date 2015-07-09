package model;

import java.awt.Point;

public class Rook extends Piece{

	public Rook(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(5);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		//check +y
		for(int i=this.getLocation().y+1; i<8; i++) {
			if(checkMove(this.getLocation().x,i)) {
				this.getPossibleMoves().add(new Point(this.getLocation().x,i));
				if(!this.getGame().getBoard().isEmpty(this.getLocation().x, i)) break;
			} else break;
		}
		//check -y
		for(int i=this.getLocation().y-1; i>-1; i--) {
			if(checkMove(this.getLocation().x,i)) {
				this.getPossibleMoves().add(new Point(this.getLocation().x,i));
				if(!this.getGame().getBoard().isEmpty(this.getLocation().x, i)) break;
			} else break;
		}
		//check +x
		for(int i=this.getLocation().x+1; i<8; i++) {
			if(checkMove(i,this.getLocation().y)) {
				this.getPossibleMoves().add(new Point(i,this.getLocation().y));
				if(!this.getGame().getBoard().isEmpty(i,this.getLocation().y)) break;
			} else break;
		}
		//check -x
		for(int i=this.getLocation().x-1; i>-1; i--) {
			if(checkMove(i,this.getLocation().y)) {
				this.getPossibleMoves().add(new Point(i,this.getLocation().y));
				if(!this.getGame().getBoard().isEmpty(i,this.getLocation().y)) break;
			} else break;
		}
	}

	@Override
	public String getUnicode() {
		return this.getColor() ? "\u2656" : "\u265c";
	}
	
	@Override
	public String getSymbol() {
		return "R";
	}
	

}

