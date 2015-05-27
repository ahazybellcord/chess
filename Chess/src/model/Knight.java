package model;

public class Knight extends Piece {
	private String _name;
	
	public Knight(String color, Game game, int x, int y) {
		super(color, game, x, y);
		_name = "knight";
	}
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setPossibleMoves() {
		super.setPossibleMoves();
		int[] uno = {-1, 1};
		int[] dos = {-2, 2};
		for(int one : uno) {
			for(int two : dos) {
				squareChecker(one,two);
				squareChecker(two,one);
			}
		}
	}

}
