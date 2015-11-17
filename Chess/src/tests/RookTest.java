package tests;

import model.Game;
import model.Rook;
import org.junit.Test;

import java.awt.*;

public class RookTest {

	@Test
	public void test() {
		String[] args = new String[0];
		Game g = new Game(args);
		Rook myRook = (Rook)g.getBoard().getPiece(0, 0);
		g.getBoard().setPiece(null, 0, 1);
		g.getBoard().setPiece(null, 0, 7);
		g.getBoard().setPiece(null, 1, 0);
		myRook.setPossibleMoves();
	
		System.out.println(myRook.getPossibleMoves().toString());
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
		Rook myRook = new Rook(true, g, new Point(4,4));
		
		myRook.setPossibleMoves();
	
		System.out.println(myRook.getPossibleMoves().toString());
	}

}
