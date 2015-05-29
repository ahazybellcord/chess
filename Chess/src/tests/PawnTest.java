package tests;

import java.awt.Point;

import model.Game;
import model.Pawn;

import org.junit.Test;

public class PawnTest {

	@Test
	public void test() {
		Game g = new Game();
		Pawn myPawn = (Pawn)g.getBoard().getPiece(0, 1);
//		System.out.println(myBishop.getColor());
//		g.getBoard().setPiece(null, 3, 1);
//		g.getBoard().setPiece(null, 1, 1);
//		g.getBoard().setPiece(null, 1, 0);
		myPawn.setMoved();
		g.getBoard().setPiece(new Pawn(true, g, new Point(1,2)), 1, 2);
		myPawn.setPossibleMoves();
	
		System.out.println(myPawn.getPossibleMoves().toString());
	}
	
	@Test
	public void test1(){
		Game g = new Game();
		for(int i = 0; i<8;i++){
			for(int j = 0; j<8; j++){
				g.getBoard().setPiece(null, i, j);
			}
		}
		Pawn myPawn = new Pawn(true, g, new Point(4,4));
		
		myPawn.setPossibleMoves();
	
		System.out.println(myPawn.getPossibleMoves().toString());
	}

}
