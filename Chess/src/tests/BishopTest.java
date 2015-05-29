package tests;

import java.awt.Point;

import model.Bishop;
import model.Game;
import model.Rook;

import org.junit.Test;

public class BishopTest {

	@Test
	public void test() {
		Game g = new Game();
		Bishop myBishop = (Bishop)g.getBoard().getPiece(2, 0);
//		System.out.println(myBishop.getColor());
		g.getBoard().setPiece(null, 3, 1);
		g.getBoard().setPiece(null, 1, 1);
//		g.getBoard().setPiece(null, 1, 0);
		myBishop.setPossibleMoves();
	
		System.out.println(myBishop.getPossibleMoves().toString());
	}
	
	@Test
	public void test1(){
		Game g = new Game();
		for(int i = 0; i<8;i++){
			for(int j = 0; j<8; j++){
				g.getBoard().setPiece(null, i, j);
			}
		}
		Bishop myRook = new Bishop(true, g, new Point(4,4));
		
		myRook.setPossibleMoves();
	
		System.out.println(myRook.getPossibleMoves().toString());
	}

}
