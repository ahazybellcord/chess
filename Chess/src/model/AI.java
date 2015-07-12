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
	int _level;
	HashMap<Piece, HashMap<Point,Integer>> _mainMap;
	HashMap<Piece, ArrayList<HashMap<Point, Integer>>> _woahMan;
	public AI(Game game, boolean aiColor){
		_game = game;
		_aiColor = aiColor;
		_board = _game.getBoard();
		_level = 2;
		_mainMap = new HashMap<Piece, HashMap<Point, Integer>>();
		_woahMan = new HashMap<Piece, ArrayList<HashMap<Point, Integer>>>();

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
		if(_game.getCurrentPlayer() == _aiColor){
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
			Collections.shuffle(aiPieces);
			//iterating over those pieces
			for(int i = 0; i < aiPieces.size(); i++){
				Piece currentPiece = aiPieces.get(i);
				Point pieceLocation = currentPiece.getLocation();
				//every piece has a HashMap of its possible points and the most damage the opponent could do if the AI makes this move
				HashMap<Point, Integer> myMap = new HashMap<Point, Integer>();
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
					Collections.shuffle(currentPiecePoints);
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
			Collections.shuffle(bestRating);
			int indexOfCandidate = bestRating.indexOf(Collections.max(bestRating));
			Piece myPiece = aiPieces.get(indexOfCandidate);
			Piece actualGamePiece = _game.getBoard().getPiece(myPiece.getLocation().x, myPiece.getLocation().y);
			_game.move(actualGamePiece, myPiece.getAIMove().x, myPiece.getAIMove().y);
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

	public void alphaMove(Game game, Board board, int level){
		if(game.getCurrentPlayer() == _aiColor){
			Board temporary = new Board();
			Game tempGame = new Game(temporary);
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(board.getPiece(i, j)!=null){
						if(board.getPiece(i, j).getSymbol().equals("K")){
							temporary.setPiece(new King(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
						else if(board.getPiece(i, j).getSymbol().equals("Q")){
							temporary.setPiece(new Queen(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
						else if(board.getPiece(i, j).getSymbol().equals("B")){
							temporary.setPiece(new Bishop(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
						else if(board.getPiece(i, j).getSymbol().equals("R")){
							temporary.setPiece(new Rook(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
						else if(board.getPiece(i, j).getSymbol().equals("N")){
							temporary.setPiece(new Knight(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
						else{
							temporary.setPiece(new Pawn(board.getPiece(i, j).getColor(), tempGame, new Point(i,j)), i, j);
						}
					}
					
				}
			}
			ArrayList<Piece> aiPieces = new ArrayList<Piece>();
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(temporary.getPiece(i, j)!=null && temporary.getPiece(i, j).getColor()==_aiColor){
						temporary.getPiece(i, j).setPossibleMoves();
						_game.selfCheck(temporary.getPiece(i, j), temporary);
						if(temporary.getPiece(i, j).getPossibleMoves().size()!=0){
							aiPieces.add(temporary.getPiece(i, j));
						}
					}
				}
			}
			Collections.shuffle(aiPieces);
			for(int i = 0; i < aiPieces.size(); i++){
				Piece currentPiece = aiPieces.get(i);
				Point currentLocation = currentPiece.getLocation();
				HashMap<Point, Integer> map = new HashMap<Point, Integer>();
				ArrayList<Integer> mp = new ArrayList<Integer>();
				for(Point p: currentPiece.getPossibleMoves()){
					temporary.setPiece(null, currentLocation.x, currentLocation.y);
					temporary.setPiece(currentPiece, p.x, p.y);
					currentPiece.setLocation(p.x, p.y);
					ArrayList<Integer> points = new ArrayList<Integer>();
					ArrayList<Piece> oPieces = new ArrayList<Piece>();
					for(int j = 0; j < 8; j++){
						for(int k = 0; k < 8; k++){
							if(temporary.getPiece(j, k)!=null && temporary.getPiece(j, k).getColor()!=_aiColor){
								temporary.getPiece(j, k).setPossibleMoves();
								_game.selfCheck(temporary.getPiece(j, k), temporary);
								if(temporary.getPiece(j, k).getPossibleMoves().size()!=0){
									oPieces.add(temporary.getPiece(j, k));
								}
							}
						}
					}
					for(int j = 0; j < oPieces.size(); j++){
						Piece oPiece = oPieces.get(j);
						Point oLocation = oPiece.getLocation();
						for(Point q: oPiece.getPossibleMoves()){
							temporary.setPiece(null, oLocation.x, oLocation.y);
							temporary.setPiece(oPiece, q.x, q.y);
							oPiece.setLocation(q.x, q.y);
							points.add(getPointDifference(temporary, _aiColor));
							if(level != 0){
								alphaMove(tempGame, temporary, level - 1);
							}
							temporary.setPiece(null, q.x, q.y);
							temporary.setPiece(oPiece, oLocation.x, oLocation.y);
							oPiece.setLocation(oLocation.x, oLocation.y);
						}
						mp.add(Collections.min(points));
					}
					map.put(p, Collections.min(mp));
					temporary.setPiece(null, p.x, p.y);
					temporary.setPiece(currentPiece, currentLocation.x, currentLocation.y);
					currentPiece.setLocation(currentLocation.x, currentLocation.y);
				}
				if(level == _level){
					_mainMap.put(currentPiece, map);
					_woahMan.put(currentPiece, new ArrayList<HashMap<Point,Integer>>());
					_woahMan.get(currentPiece).add(level, map);
				}
				else{
					_woahMan.get(currentPiece).add(level, map);
				}
				if(level == 0){
					ArrayList<HashMap<Point, Integer>> myList = _woahMan.get(currentPiece);
					for(int n = 0; n<myList.size(); n++){
						if(n==_level){
							break;
						}
						for(Point p: myList.get(n).keySet()){
							
						}
					}
				}
			}
		}
		
		
	}

}
