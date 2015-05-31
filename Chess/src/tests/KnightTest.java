package tests;

import java.awt.Point;

import model.Bishop;
import model.Game;
import model.Knight;

import org.junit.Test;

public class KnightTest {

	@Test
	public void test() {
		String[] args = new String[0];
		Game g = new Game(args);
		Knight myKnight = (Knight)g.getBoard().getPiece(1, 0);
		g.getBoard().setPiece(null, 3, 1);
		myKnight.setPossibleMoves();
	
//		System.out.println(myKnight.getPossibleMoves().toString());
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
		Knight myKnight = new Knight(true, g, new Point(3,3));
		
		myKnight.setPossibleMoves();
	
		System.out.println(myKnight.getPossibleMoves().toString());
	}

}
