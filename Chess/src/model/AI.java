package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AI {

	Game _game;
	boolean _aiColor;
	Board _board;
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

	public void betaMove(){
		ArrayList<Piece> aiPieces = new ArrayList<Piece>();
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
		//getting the pieces that belong to the AI
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
		//iterating over those pieces
		for(int i = 0; i < aiPieces.size(); i++){
			Piece currentPiece = aiPieces.get(i);
			Point pieceLocation = currentPiece.getLocation();
			currentPiece.setPossibleMoves();
			//every piece has a HashMap of its possible points and the most damage the opponent could do if the AI makes this move
			HashMap<Point, Integer> myMap = new HashMap<Point, Integer>();
			_game.selfCheck(currentPiece, temporary);
			//going over the possible moves of the current piece
			for(Point pos: currentPiece.getPossibleMoves()){
				temporary.setPiece(currentPiece, pos.x, pos.y);
				temporary.setPiece(null, pieceLocation.x, pieceLocation.y);
				currentPiece.setLocation(pos.x, pos.y);
				//moved it to its new location on the board
				//keeps track of the point difference between the sides for all the possible moves the opponent could make
				ArrayList<Integer> currentPiecePoints = new ArrayList<Integer>();
				//going over the entire board and getting the opponent pieces
				for(int j = 0; j<8; j++){
					for(int k = 0; k< 8; k++){
						if(temporary.getPiece(j, k)!=null && temporary.getPiece(j,k).getColor()!=_aiColor){
							Piece oppositionPiece = temporary.getPiece(j, k);
							oppositionPiece.setPossibleMoves();
							_game.selfCheck(oppositionPiece, temporary);
							if(oppositionPiece.getPossibleMoves().size() !=0){
								Point oppositionLocation = oppositionPiece.getLocation();
								ArrayList<Integer> oppDamage = new ArrayList<Integer>();
								//going over the currently selected opposition piece's possible moves
								for(Point p: oppositionPiece.getPossibleMoves()){
									temporary.setPiece(oppositionPiece, p.x, p.y);
									temporary.setPiece(null, oppositionLocation.x, oppositionLocation.y);
									oppositionPiece.setLocation(p.x, p.y);
									//adding the point difference to the ArrayList
									oppDamage.add(getPointDifference(temporary, _aiColor));
									//moving the piece back
									temporary.setPiece(null, p.x, p.y);
									temporary.setPiece(oppositionPiece, oppositionLocation.x, oppositionLocation.y);
									oppositionPiece.setLocation(oppositionLocation.x, oppositionLocation.y);
								}
								currentPiecePoints.add(Collections.min(oppDamage));
							}
							
						}
					}
				}
				int lowestValue = Collections.min(currentPiecePoints);
				myMap.put(pos, lowestValue);
				temporary.setPiece(null, pos.x, pos.y);
				temporary.setPiece(currentPiece, pieceLocation.x, pieceLocation.y);
				currentPiece.setLocation(pieceLocation.x, pieceLocation.y);
			}
			currentPiece.setAIMap(myMap);
		}
		ArrayList<Integer> bestRating = new ArrayList<Integer>();
		for(int i = 0; i< aiPieces.size(); i++){
			bestRating.add(aiPieces.get(i).getAIRating());
		}
		int indexOfCandidate = bestRating.indexOf(Collections.max(bestRating));
		Piece myPiece = aiPieces.get(indexOfCandidate);
		Piece actualGamePiece = _game.getBoard().getPiece(myPiece.getLocation().x, myPiece.getLocation().y);
		_game.move(actualGamePiece, myPiece.getAIMove().x, myPiece.getAIMove().y);
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
