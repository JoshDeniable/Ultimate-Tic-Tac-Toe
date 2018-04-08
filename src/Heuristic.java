public class Heuristic{
	
	//when the minimax algorithm goes to evaluate a leaf node, it will call this method which will call 3 other methods internal to this class
	//which will calculate an individual rating for the position and those 3 heuristcs will be added up to come up with the final evaluation of the position
	//in the case that there is a winner, instead of calculating individual heuristcs, a big number will be returned
	//(based on who the winner is either positive or negative)
	public static float ratePosition(BigBoard gameBoard,String lastMove,PLAYER player){
		float result = 0;
		PLAYER winner = gameBoard.checkWin();
		if(winner==PLAYER.NONE){
			result += heuristic1(gameBoard);
			result += heuristic2(gameBoard,lastMove,player);
			result += heuristic3(gameBoard);
			return result;
		} else {
			if(winner == PLAYER.X){
				return -999999999;
			} else if(winner == PLAYER.O){
				return 999999999;
			} else {
				return 0;
			}
		}
	}
	//within an individual board, give highest priority to having the center, 
	//next highest to having the corners, last to having the side cells
	private static float heuristic1(BigBoard gameBoard){
		float result = 0;
		for(int row=0;row<3;row++){
			for(int col=0;col<3;col++){
				Board currentBoard = gameBoard.getBoard(row,col);
				for(int innerRow=0;innerRow<3;innerRow++){
					for(int innerCol=0;innerCol<3;innerCol++){
						float current = 0;
						PLAYER player = currentBoard.getPlayer(innerRow,innerCol);
						if(player == PLAYER.NONE){
							continue;
						}
						if((innerCol+innerRow)%2==0){
							current=2;
							if(innerCol==1&&innerRow==1){
								current+=1;
							}
						} else {
							current=1;
						}
						if(player == PLAYER.X){
							current *= -1;
						}
						result += current;
					}
				}
			}
		}
		return result;
	}
	
	//defensive heuristic such that the last player should have sent the opponent to a board with less possibilities
	//but we don't want to sent him to a board with 0 possibilities, as that would actually give the opponent even more choices
	private static float heuristic2(BigBoard gameBoard, String lastMove, PLAYER player){
		float result = 9;
		int individualBoardRow = Integer.parseInt(lastMove.substring(0, 1))%3; 
		int individualBoardCol = Integer.parseInt(lastMove.substring(1, 2))%3;
		Board individualBoard = gameBoard.getBoard(individualBoardRow,individualBoardCol);
		for(int boardRow=0;boardRow<3;boardRow++){
			for(int boardCol=0;boardCol<3;boardCol++){
				if(individualBoard.getPlayer(boardRow,boardCol)==PLAYER.NONE){
					result-=1;
				}
			}
		}
		if(result==0){
			result = 10;
		}
		if(player == PLAYER.O){
			result *= -1;
		}
		return result;
	}
	
	//give extra priority to having control over the middle board
	//if neither player has control of the middle board, give a higher score to the person who has more in there
	private static float heuristic3(BigBoard gameBoard){
		float result = 0;
		Board midBoard = gameBoard.getBoard(1,1);
		if(midBoard.getWinner()==PLAYER.X){
			return result = -10;
		} else if(midBoard.getWinner() == PLAYER.O){
			return result = 10;
		}
		for(int innerRow=0;innerRow<3;innerRow++){
			for(int innerCol=0;innerCol<3;innerCol++){
				if(midBoard.getPlayer(innerRow, innerCol)==PLAYER.X){
					result-=1;
				} else if(midBoard.getPlayer(innerRow, innerCol)==PLAYER.O){
					result+=1;
				}
			}
		}
		return result;
	}
} 