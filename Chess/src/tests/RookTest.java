package tests;

import java.awt.Point;

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
	
	@Test
	public void test1(){
		Game g = new Game();
		for(int i = 0; i<8;i++){
			for(int j = 0; j<8; j++){
				g.getBoard().setPiece(null, i, j);
			}
		}
		Rook myRook = new Rook(true, g, new Point(4,4));
		
		myRook.setPossibleMoves();
	
		System.out.println(myRook.getPossibleMoves().toString());
	}

}
