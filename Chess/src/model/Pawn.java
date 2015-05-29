package model;

import java.awt.Point;

public class Pawn extends Piece{
	private boolean _moved;
	public Pawn(boolean color, Game game, Point location) {
		super(color,game,location);
		_moved = false;
	}
    
	@Override
	public void setPossibleMoves() {
		if(this.getColor()){
			if(!_moved){
				if(checkMove(this.getLocation().x, this.getLocation().y-2)){
					if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y-2)){
						this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y-2));
					}
				}
			}
			if(checkMove(this.getLocation().x, this.getLocation().y-1)){
				if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y-1)){
					this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y-1));
				}
			}
			if(this.getLocation().x<7){
				if(checkMove(this.getLocation().x+1, this.getLocation().y-1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+1, this.getLocation().y-1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y-1));
					}
				}
			}
			if(this.getLocation().x>0){
				if(checkMove(this.getLocation().x-1, this.getLocation().y-1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-1, this.getLocation().y-1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y-1));
					}
				}
			}


		}
		else{
			if(!_moved){
				if(checkMove(this.getLocation().x, this.getLocation().y+2)){
					if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y+2)){
						this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y+2));
					}
				}
			}
			if(checkMove(this.getLocation().x, this.getLocation().y+1)){
				if(this.getGame().getBoard().isEmpty(this.getLocation().x, this.getLocation().y+1)){
					this.getPossibleMoves().add(new Point(this.getLocation().x, this.getLocation().y+1));
				}
			}
			if(this.getLocation().x<7){
				if(checkMove(this.getLocation().x+1, this.getLocation().y+1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x+1, this.getLocation().y+1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y+1));
					}
				}
			}
			if(this.getLocation().x>0){
				if(checkMove(this.getLocation().x-1, this.getLocation().y-1)){
					if(!this.getGame().getBoard().isEmpty(this.getLocation().x-1, this.getLocation().y+1)){
						this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y+1));
					}
				}
			}

		}


	}

	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2659"; }
		else { return "\u265f" ; }
	}

	public void setMoved(){
		_moved = true;
	}

	public boolean wasMoved(){
		return _moved;
	}


}

