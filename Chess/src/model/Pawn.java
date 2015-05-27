package model;

public class Pawn extends Piece{
	private String _name;
	private boolean _moved;

	public Pawn(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "pawn";
	}
	
	public String getName() {
		return _name;
	}
	
	public void setMoved() {
		_moved = true;
	}
	
	public boolean wasMoved() {
		return _moved;
	}
	
	@Override
	public void setPossibleMoves() {
		super.getPossibleMoves();
		//white pawn moves in +y-direction
		if(this.getColor().equals("white")){
			squareChecker(0,1); //check (x,y+1)
			if(!_moved) {
				squareChecker(0,2); //check (x, y+2) if not yet moved
			}
		}//black pawn moves in the -y-direction
		else{
			squareChecker(0,-1); //check (x,y-1)
			if(!_moved) {
				squareChecker(0,-2); //check (x,y-2)
			}
		}
	}
	
	@Override
	public boolean checkMove(int x, int y) {
		if(super.getBoard().getPiece(x,y)==null){
			return true;
		}
		else {
			if(!super.getBoard().getPiece(x,y).equals(new Piece("white", super.getGame(),this.x(),this.y()))
					&!super.getBoard().getPiece(x,y).equals(new Piece("black", super.getGame(),this.x(),this.y()))){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean tryMove(int x, int y){
		boolean check = super.tryMove(x, y);
		if(check & !_moved) { setMoved(); }
		return check;
	}

}
