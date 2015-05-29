package tests;

import model.Game;
import model.Rook;

import org.junit.Test;

public class RookTest {

	@Test
	public void test() {
		Game g = new Game();
		Rook myRook = (Rook)g.getBoard().getPiece(0, 0);
		g.getBoard().setPiece(null, 0, 1);
		g.getBoard().setPiece(null, 0, 7);
		g.getBoard().setPiece(null, 1, 0);
		myRook.setPossibleMoves();
	
		System.out.println(myRook.getPossibleMoves().toString());
	}

}
