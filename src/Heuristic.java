public class Heuristic{
	
	//when the minimax algorithm goes to evaluate a leaf node, it will call this method which will call 3 other methods internal to this class
	//which will calculate an individual rating for the position and those 3 heuristcs will be added up to come up with the final evaluation of the position
	//in the case that there is a winner, instead of calculating individual heuristcs, a big number will be returned
	//(based on who the winner is either positive or negative)
	public static float ratePosition(BigBoard gameBoard){
		float result = 0;
		PLAYER winner = gameBoard.checkWin();
		if(winner==PLAYER.NONE){
			result += heuristic1(gameBoard)*3;
			result += heuristic2(gameBoard)*2;
			return result;
		} else {
			if(winner == PLAYER.X){
				return Integer.MIN_VALUE;
			} else if(winner == PLAYER.O){
				return Integer.MAX_VALUE;
			} else {
				return 0;
			}
		}
	}

	//Look for 2 in the same row/column/diagonal, as long as the third in that row/column/diagonal has not been won yet
	private static float heuristic1(BigBoard gameBoard){
		float result=0;

		return result;
	}

    //	give priority for winning center, and corner boards, if a board is not won, run another heuristic on it
     private static float heuristic2(BigBoard gameBoard){
		float result = 0;
		for(int i = 0; i<3; i++){
		    for(int j = 0;j<3; j++){
                Board currentBoard = gameBoard.getBoard(i,j);
                if(currentBoard.getWinner()==PLAYER.X){
                    result -= 5;
                    if((i+j)%2==0){
                        result -= 3;
                    }
                    if(i==1&&j==1){
                        result -= 10;
                    }
                } else if(currentBoard.getWinner() == PLAYER.O){
                    result += 5;
                    if((i+j)%2==0){
                        result += 3;
                    }
                    if(i==1&&j==1){
                        result += 10;
                    }
                } else {
                    result += heuristic3(currentBoard);
                }

            }
        }
		return result;
	}

	//return the difference of X's and O's within a given board. Give extra priority to the center
    private static float heuristic3(Board currentBoard){
        float result=0;
	    for(int innerRow=0;innerRow<3;innerRow++){
            for(int innerCol=0;innerCol<3;innerCol++){
                if(currentBoard.getPlayer(innerRow, innerCol)==PLAYER.X){
                    result-=1;
                    if(innerRow==1 && innerCol==1){
                        result-= 3;
                    }
                } else if(currentBoard.getPlayer(innerRow, innerCol)==PLAYER.O){
                    result+=1;
                    if(innerRow==1 && innerCol==1){
                        result+= 3;
                    }
                }
            }
        }
        return result;
    }
} 