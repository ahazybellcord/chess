package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
					Piece c = null;
					if(temporary.getPiece(pos.x, pos.y)!=null){
						c = temporary.getPiece(pos.x, pos.y);
					}
					temporary.setPiece(currentPiece, pos.x, pos.y);
					temporary.setPiece(null, pieceLocation.x, pieceLocation.y);
					currentPiece.setLocation(pos.x, pos.y);
					_game.printBoard(temporary);
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
										Piece captured = null;
										if(temporary.getPiece(p.x, p.y)!=null){
											captured = temporary.getPiece(p.x, p.y);
										}
										temporary.setPiece(oppositionPiece, p.x, p.y);
										temporary.setPiece(null, oppositionLocation.x, oppositionLocation.y);
										oppositionPiece.setLocation(p.x, p.y);
										//adding the point difference to the ArrayList
										oppDamage.add(getPointDifference(temporary, _aiColor));
										
//											_game.printBoard(temporary);
										
//										System.out.println("Point diff " + getPointDifference(temporary, _aiColor));
										//moving the piece back
										temporary.setPiece(null, p.x, p.y);
										if(captured!=null){
											temporary.setPiece(captured, p.x, p.y);
										}
										temporary.setPiece(oppositionPiece, oppositionLocation.x, oppositionLocation.y);
										oppositionPiece.setLocation(oppositionLocation.x, oppositionLocation.y);
									}
									currentPiecePoints.add(Collections.max(oppDamage));
								}

							}
						}
					}
					Collections.shuffle(currentPiecePoints);
					int lowestValue = Collections.max(currentPiecePoints);
					myMap.put(pos, lowestValue);
					temporary.setPiece(null, pos.x, pos.y);
					if(c!=null){
						temporary.setPiece(c, pos.x, pos.y);
					}
					temporary.setPiece(currentPiece, pieceLocation.x, pieceLocation.y);
					currentPiece.setLocation(pieceLocation.x, pieceLocation.y);
				}
				currentPiece.setAIMap(myMap);
				System.out.println(myMap.toString());
			}
			ArrayList<Integer> bestRating = new ArrayList<Integer>();
			for(int i = 0; i< aiPieces.size(); i++){
				bestRating.add(aiPieces.get(i).getAIRating());
			}
			Collections.shuffle(bestRating);
			System.out.println("Best Rating: " + Collections.max(bestRating));
			int indexOfCandidate = bestRating.indexOf(Collections.max(bestRating));
			Piece myPiece = aiPieces.get(indexOfCandidate);
			System.out.println("Best Piece: " + myPiece.getUnicode());
			Piece actualGamePiece = _game.getBoard().getPiece(myPiece.getLocation().x, myPiece.getLocation().y);
			System.out.println("Actual Game Piece: " + actualGamePiece.getUnicode());
			System.out.println("Actual Move: " + myPiece.getAIMove().toString());
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
				if(!currentPiece.wasMoved()){
					currentPiece.setMoved();
				}
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
//								_game.selfCheck(temporary.getPiece(j, k), temporary);
								if(temporary.getPiece(j, k).getPossibleMoves().size()!=0){
									oPieces.add(temporary.getPiece(j, k));
								}
							}
						}
					}
					for(int j = 0; j < oPieces.size(); j++){
						Piece oPiece = oPieces.get(j);
						Point oLocation = oPiece.getLocation();
						if(!oPiece.wasMoved()){
							oPiece.setMoved();
						}
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
				if(level == 0){
					_woahMan.put(currentPiece, new ArrayList<HashMap<Point, Integer>>());
					_woahMan.get(currentPiece).add(level, map);
				}
				else if(level != _level){
					_woahMan.get(currentPiece).add(level, map);
				}
				else{
					_woahMan.get(currentPiece).add(level, map);
					ArrayList<HashMap<Point,Integer>> list = _woahMan.get(currentPiece);
					for(int p = 0; p<list.size(); p++){
						if(p == _level){
							break;
						}
						HashMap<Point, Integer> m = list.get(p);
						Map.Entry<Point, Integer> worstMove = null;
						for (Map.Entry<Point, Integer> entry : m.entrySet()){
						    if (worstMove == null || entry.getValue().compareTo(worstMove.getValue()) < 0){
						        worstMove = entry;
						    }
						}
						HashMap<Point, Integer> m2 = list.get(p+1);
						for(Point s: m2.keySet()){
							boolean color = currentPiece.getColor();
							String symbol = currentPiece.getSymbol();
							Board temp = new Board();
							Game t = new Game(temp);
							if(symbol.equals("K")){
								King k = new King(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							else if(symbol.equals("Q")){
								Queen k = new Queen(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							else if(symbol.equals("N")){
								Knight k = new Knight(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							else if(symbol.equals("B")){
								Bishop k = new Bishop(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							else if(symbol.equals("R")){
								Rook k = new Rook(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							else{
								Pawn k = new Pawn(color, t, s);
								temp.setPiece(k, s.x, s.y);
							}
							temp.getPiece(s.x, s.y).setPossibleMoves();
							for(Point u: temp.getPiece(s.x, s.y).getPossibleMoves()){
								if(u.equals(worstMove.getKey())){
									m2.put(s, m.get(s) + worstMove.getValue());
								}
							}
							
						}
						
					}
				}
			}
			if(level == _level){
				for(Piece z: _woahMan.keySet()){
					ArrayList<HashMap<Point, Integer>> finalList = _woahMan.get(z);
					HashMap<Point, Integer> finalMap = finalList.get(_level);
					z.setAIMap(finalMap);
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
		
		
	}

}
