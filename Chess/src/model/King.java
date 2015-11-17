package model;

import java.awt.*;

public class King extends Piece{
	
	public King(boolean color, Game game, Point location) {
		super(color,game,location);
		super.setValue(-1);
	}
	
	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		Queen myQueen = new Queen(this.getColor(), this.getGame(), this.getLocation());
		myQueen.setPossibleMoves();
		for(Point p : myQueen.getPossibleMoves()){
			if(Math.abs(p.x-this.getLocation().x)<2 && Math.abs(p.y-this.getLocation().y)<2){
				this.getPossibleMoves().add(p);
			}
		}
		checkCastlingKingside(this.getColor());
		checkCastlingQueenside(this.getColor());
	}
	
	public void checkCastlingKingside(boolean color) {
		Board b = this.getGame().getBoard();
		if(color){
			if(b.getPiece(7,7)!=null && b.getPiece(4,7)!=null){
				if(b.getPiece(7,7).getClass().getName().equals("model.Rook") && b.getPiece(4,7).getClass().getName().equals("model.King")) {
					Rook r = (Rook) b.getPiece(7,7);
					King k = (King) b.getPiece(4,7);
					if(!r.wasMoved() && !k.wasMoved()) {
						if(b.getPiece(5,7)==null && b.getPiece(6,7)==null) {
							this.getPossibleMoves().add(new Point(6,7));
						}
					}
				}
			}
		}
		else {
			if(b.getPiece(7, 0)!=null && b.getPiece(4,0)!=null){
				if(b.getPiece(7,0).getClass().getName().equals("model.Rook") && b.getPiece(4,0).getClass().getName().equals("model.King")) {
					Rook r = (Rook) b.getPiece(7,0);
					King k = (King) b.getPiece(4,0);
					if(!r.wasMoved() && !k.wasMoved()) {
						if(b.getPiece(5,0)==null && b.getPiece(6,0)==null) {
							this.getPossibleMoves().add(new Point(6,0));
						}
					}
				}
			}
		}
	}
	
	public void checkCastlingQueenside(boolean color) {
		Board b = this.getGame().getBoard();
		if(color){
			if(b.getPiece(0,7)!=null && b.getPiece(4,7)!=null){
				if(b.getPiece(0,7).getClass().getName().equals("model.Rook") && b.getPiece(4,7).getClass().getName().equals("model.King")) {
					Rook r = (Rook) b.getPiece(0,7);
					King k = (King) b.getPiece(4,7);
					if(!r.wasMoved() && !k.wasMoved()) {
						if(b.getPiece(1,7)==null && b.getPiece(2,7)==null && b.getPiece(3,7)==null) {
							this.getPossibleMoves().add(new Point(2,7));
						}
					}
				}
			}
		}
		else {
			if(b.getPiece(0,0)!=null && b.getPiece(4,0)!=null){
				if(b.getPiece(0,0).getClass().getName().equals("model.Rook") && b.getPiece(4,0).getClass().getName().equals("model.King")) {
					Rook r = (Rook) b.getPiece(0,0);
					King k = (King) b.getPiece(4,0);
					if(!r.wasMoved() && !k.wasMoved()) {
						if(b.getPiece(1,0)==null && b.getPiece(2,0)==null && b.getPiece(3,0)==null) {
							this.getPossibleMoves().add(new Point(2,0));
						}
					}
				}
			}
		}
	}
	
	@Override
	public String getUnicode() {
		return this.getColor() ? "\u2654" : "\u265a" ;
	}
	
	@Override
	public String getSymbol() {
		return "K";
	}
	
}

