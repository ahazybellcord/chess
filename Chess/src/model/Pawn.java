package model;

import java.awt.Point;

public class Pawn extends Piece{

	public Pawn(boolean color, Game game, Point location) {
		super(color,game,location);
	}
    
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		if(this.getColor()){
			if(!this.wasMoved()){
				if(checkEmpty(this.getLocation().x, this.getLocation().y-2)){
					if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y-2) && this.getGame().getBoard().isEmpty(this.getLocation().x,  this.getLocation().y-1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y-2));
					}
				}
			}
			if(checkEmpty(this.getLocation().x, this.getLocation().y-1)){
				if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y-1)){
					this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y-1));
				}
			}
			if(this.getLocation().x<7){
				if(checkOpponent(this.getLocation().x+1, this.getLocation().y-1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+1, this.getLocation().y-1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y-1));
					}
				}
			}
			if(this.getLocation().x>0){
				if(checkOpponent(this.getLocation().x-1, this.getLocation().y-1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-1, this.getLocation().y-1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y-1));
					}
				}
			}
		}
		else{
			if(!this.wasMoved()){
				if(checkEmpty(this.getLocation().x, this.getLocation().y+2)){
					if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y+2) && this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y+1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y+2));
					}
				}
			}
			if(checkEmpty(this.getLocation().x, this.getLocation().y+1)){
				if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y+1)){
					this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y+1));
				}
			}
			if(this.getLocation().x<7){
				if(checkOpponent(this.getLocation().x+1, this.getLocation().y+1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+1, this.getLocation().y+1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y+1));
					}
				}
			}
			if(this.getLocation().x>0){
				if(checkOpponent(this.getLocation().x-1, this.getLocation().y+1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-1, this.getLocation().y+1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y+1));
					}
				}
			}
		}
	}
	
	public boolean checkEmpty(int x, int y) {
		return this.getGame().getBoard().isEmpty(x, y);
	}

	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2659"; }
		else { return "\u265f" ; }
	}

}

