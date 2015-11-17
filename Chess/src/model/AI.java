package model;

import model.Tree.Node;

import java.awt.*;
import java.util.*;

public class AI {

	private final Game _game;
	private final boolean _aiColor;
	private final Board _board;

	public AI(Game game, boolean aiColor){
		_game = game;
		_aiColor = aiColor;
		_board = _game.getBoard();

	}

	public void move(){
		if(_game.getCurrentPlayer() == _aiColor){
			ArrayList<Piece> aiPieces = new ArrayList<Piece>();
			for(int i = 0; i<8; i++){
				for(int j = 0; j<8; j++){
					if(!_board.isEmpty(i, j)){
						if(_board.getPiece(i, j).getColor()==_game.getCurrentPlayer()){
							_board.getPiece(i, j).setPossibleMoves();
							_game.selfCheck(_board.getPiece(i, j), _board);
							if(_board.getPiece(i, j).getPossibleMoves().size()!=0){
								aiPieces.add(_board.getPiece(i, j));
							}
						}
					}
				}
			}
			Collections.shuffle(aiPieces);
			if(aiPieces.size()>0){
				Piece selectedPiece = aiPieces.get(0);
				HashSet<Point> moves = selectedPiece.getPossibleMoves();
				Point q = new Point(0,0);
				boolean canCapture = false;
				for(Point p: moves){
					if(!_board.isEmpty(p.x, p.y)){
						canCapture = true;
					}
				}
				do{
					int size = moves.size();
					int randIndex = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
					int i = 0;
					for(Point p : moves)
					{
						if (i == randIndex){
							q=p;
						}
						i = i + 1;
					}
					if(!canCapture){
						break;
					}
				} while(_board.isEmpty(q.x, q.y));
				_game.move(selectedPiece, q.x, q.y);
			}
		}
	}
	
	public void treeMove(){
		Board temporary = new Board();
		Game temporaryGame = new Game(temporary);
		//creating a temporary board
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(_game.getBoard().getPiece(i, j)!=null){
					temporary.setPiece(_game.getBoard().getPiece(i, j), i, j);
					temporary.getPiece(i, j).setGame(temporaryGame);
				}
			}
		}
		Tree<Board> gameTree = new Tree<Board>(temporary);
		ArrayList<Piece> aiPieces = new ArrayList<Piece>();
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(temporary.getPiece(i, j)!=null && temporary.getPiece(i, j).getColor() == _aiColor){
					temporary.getPiece(i, j).setPossibleMoves();
					_game.selfCheck(temporary.getPiece(i, j), temporary);
					if(temporary.getPiece(i, j).getPossibleMoves().size()!=0){
						aiPieces.add(temporary.getPiece(i, j));
					}
				}
			}
		}
		for(int i = 0; i<aiPieces.size(); i++){
			for(Point p: aiPieces.get(i).getPossibleMoves()){
				Board temp = temporary;
				Game gTemp = temporaryGame;
				temp.setGame(gTemp);
				gTemp.setBoard(temp);
				gTemp.move(aiPieces.get(i), p.x, p.y);
				Node<Board> n = new Node<Board>();
				n.data = temp;
				n.parent = gameTree.root;
				gameTree.root.children.add(n);
				//creating a temporary board

			}
		}
		
		
	}



	private int getPointDifference(Board board, boolean aiColor){
		int count = 0;
		int oppositionCount = 0;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j<8; j++){
				if(board.getPiece(i, j)!=null){
					if(board.getPiece(i, j).getColor() == aiColor){
						count += board.getPiece(i, j).getValue();
					}
					else{
						oppositionCount += board.getPiece(i, j).getValue();
					}
				}
			}
		}
		return count - oppositionCount;
	}

}
