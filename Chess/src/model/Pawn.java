package model;

import java.awt.Point;

public class Pawn extends Piece{
	boolean _special;
	boolean _justMoved;

	public Pawn(boolean color, Game game, Point location) {
		super(color,game,location);
		_special = false;
		_justMoved = false;
	}
    
	public boolean wasJustMoved() {
		return _justMoved;
	}

	public void setJustMoved(boolean b) {
		_justMoved = b;
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
		if(this.getColor() && this.getLocation().y == 3)
		{
			if(this.getLocation().x>0 && this.getLocation().x<7){
				if(checkOpponent(this.getLocation().x-1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y-1));
				}
				if(checkOpponent(this.getLocation().x+1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y-1));
				}
			}
			else if(this.getLocation().x == 0){
				if(checkOpponent(this.getLocation().x+1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y-1));
				}
			}
			else if(this.getLocation().x == 7){
				if(checkOpponent(this.getLocation().x-1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y-1));
				}
			}
		}
		else if (!this.getColor() && this.getLocation().y == 4)
		{
			if(this.getLocation().x>0 && this.getLocation().x<7){
				if(checkOpponent(this.getLocation().x-1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y+1));
				}
				if(checkOpponent(this.getLocation().x+1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y+1));
				}
			}
			else if(this.getLocation().x == 0){
				if(checkOpponent(this.getLocation().x+1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x+1, this.getLocation().y+1));
				}
			}
			else if(this.getLocation().x == 7){
				if(checkOpponent(this.getLocation().x-1,this.getLocation().y) && this.getGame().getBoard().getPiece(this.getLocation().x-1,this.getLocation().y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(this.getLocation().x+1,this.getLocation().y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(this.getLocation().x-1, this.getLocation().y+1));
				}
			}
		}
	}
	
	public boolean checkEmpty(int x, int y) {
		return this.getGame().getBoard().isEmpty(x, y);
	}
	
	void setSpecial() {
		_special = true;
	}
	
	boolean getSpecial() {
		return _special;
	}

	@Override
	public String getUnicode() {
		if(this.getColor()) { return "\u2659"; }
		else { return "\u265f" ; }
	}

}

