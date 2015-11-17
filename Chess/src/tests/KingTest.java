package tests;

import model.Game;
import model.King;
import org.junit.Test;

import java.awt.*;

public class KingTest {

	@Test
	public void test() {
		String[] args = new String[0];
		Game g = new Game(args);
		King myKing = (King)g.getBoard().getPiece(4, 0);
		g.getBoard().setPiece(null, 4, 1);
		g.getBoard().setPiece(null, 5, 1);
		g.getBoard().setPiece(null, 3, 0);
		myKing.setPossibleMoves();
	
//		System.out.println(myKing.getPossibleMoves().toString());
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
		King myKing = new King(true, g, new Point(4,4));
		
		myKing.setPossibleMoves();
	
		System.out.println(myKing.getPossibleMoves().toString());
	}

}
