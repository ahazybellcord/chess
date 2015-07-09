package model;

import java.awt.Point;

public class Pawn extends Piece{
	boolean _special;
	boolean _justMoved;

	public Pawn(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(1);
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
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		super.setPossibleMoves();
		if(this.getColor()){
			if(!this.wasMoved()){
				if(checkEmpty(x, y-2)){
					if(this.getGame().getBoard().isEmpty(x,y-2) && this.getGame().getBoard().isEmpty(x,y-1)){
						this.getPossibleMoves().add(new Point(x,y-2));
					}
				}
			}
			if(checkEmpty(x, y-1)){
				if(this.getGame().getBoard().isEmpty(x,y-1)){
					this.getPossibleMoves().add(new Point(x,y-1));
				}
			}
			if(x<7){
				if(checkOpponent(x+1,y-1)){
					if(!this.getGame().getBoard().isEmpty(x+1,y-1)){
						this.getPossibleMoves().add(new Point(x+1,y-1));
					}
				}
			}
			if(x>0){
				if(checkOpponent(x-1, y-1)){
					if(!this.getGame().getBoard().isEmpty(x-1,y-1)){
						this.getPossibleMoves().add(new Point(x-1,y-1));
					}
				}
			}
		}
		else{
			if(!this.wasMoved()){
				if(checkEmpty(x,y+2)){
					if(this.getGame().getBoard().isEmpty(x,y+2) && this.getGame().getBoard().isEmpty(x,y+1)){
						this.getPossibleMoves().add(new Point(x,y+2));
					}
				}
			}
			if(checkEmpty(x,y+1)){
				if(this.getGame().getBoard().isEmpty(x,y+1)){
					this.getPossibleMoves().add(new Point(x,y+1));
				}
			}
			if(x<7){
				if(checkOpponent(x+1,y+1)){
					if(!this.getGame().getBoard().isEmpty(x+1,y+1)){
						this.getPossibleMoves().add(new Point(x+1,y+1));
					}
				}
			}
			if(x>0){
				if(checkOpponent(x-1, y+1)){
					if(!this.getGame().getBoard().isEmpty(x-1,y+1)){
						this.getPossibleMoves().add(new Point(x-1,y+1));
					}
				}
			}
		}
		if(this.getColor() && y == 3)
		{
			if(x>0 && x<7){
				if(checkOpponent(x-1,y) && this.getGame().getBoard().getPiece(x-1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x-1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x-1, y-1));
				}
				if(checkOpponent(x+1,y) && this.getGame().getBoard().getPiece(x+1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x+1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x+1, y-1));
				}
			}
			else if(x == 0){
				if(checkOpponent(x+1,y) && this.getGame().getBoard().getPiece(x+1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x+1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x+1, y-1));
				}
			}
			else if(x == 7){
				if(checkOpponent(x-1,y) && this.getGame().getBoard().getPiece(x-1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x-1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x-1, y-1));
				}
			}
		}
		else if (!this.getColor() && y == 4)
		{
			if(x>0 && x<7){
				if(checkOpponent(x-1,y) && this.getGame().getBoard().getPiece(x-1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x-1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x-1, y+1));
				}
				if(checkOpponent(x+1,y) && this.getGame().getBoard().getPiece(x+1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x+1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x+1, y+1));
				}
			}
			else if(x == 0){
				if(checkOpponent(x+1,y) && this.getGame().getBoard().getPiece(x+1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x+1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x+1, y+1));
				}
			}
			else if(x == 7){
				if(checkOpponent(x-1,y) && this.getGame().getBoard().getPiece(x-1,y).getClass().getName().equals("model.Pawn") && ((Pawn)(this.getGame().getBoard().getPiece(x-1,y))).wasJustMoved()){
					this.getPossibleMoves().add(new Point(x-1, y+1));
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
		return this.getColor() ? "\u2659" : "\u265f";
	}

}

