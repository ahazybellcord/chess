package model;

public class King extends Piece {
	private String _name;

	public King(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "king";	
	}
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		//there are eight  squares to check
		for(int i=-1; i<=1; i++) {
			for(int j=-1; j<=1; j++) {
				if(i!=0 & j!=0){
					squareChecker(i,j); //checks over all possible king moves
				}
			}
		}
	}

}
