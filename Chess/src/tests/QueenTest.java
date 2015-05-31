package tests;

import java.awt.Point;

import model.Game;
import model.Queen;
import model.Rook;

import org.junit.Test;

public class QueenTest {

	@Test
	public void test() {
		String[] args = new String[0];
		Game g = new Game(args);
		Queen myQueen = (Queen)g.getBoard().getPiece(3, 0);
		g.getBoard().setPiece(null, 2, 1);
		g.getBoard().setPiece(null, 4, 1);
		g.getBoard().setPiece(null, 3, 1);
		
		myQueen.setPossibleMoves();
	
//		System.out.println(myQueen.getPossibleMoves().toString());
	}
	
	@Test
	public void test1(){
		String[] args = new String[0];
		Game g = new Game(args);
		for(int i = 0; i<8;i++){
			for(int j = 0; j<8; j++){
				g.getBoard().setPiece(null, i, j);
			}
		}
		Queen q = new Queen(true,g,new Point(3,3));
		g.getBoard().setPiece(q,3,3);		
		q.setPossibleMoves();
	
		System.out.println(q.getPossibleMoves().toString());
	}
}
