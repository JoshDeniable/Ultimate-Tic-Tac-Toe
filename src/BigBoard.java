public class BigBoard {

    private Board[][] bigBoard;
	
    public BigBoard() {
        bigBoard = new Board[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bigBoard[i][j] = new Board();
            }
        }
    }

    public boolean makeMove(PLAYER player, int row, int col){
        int smallBoardRow = row / 3;
		int smallBoardCol = col / 3;
		int smallBoardCellRow = row % 3;
		int smallBoardCellCol = col % 3;
		Board smallBoard = bigBoard[smallBoardRow][smallBoardCol];
		smallBoard = smallBoard.makeMove(player, smallBoardCellRow, smallBoardCellCol);
        if(smallBoard == null){
			return false; //unsuccessful
		}
		bigBoard[smallBoardRow][smallBoardCol] = smallBoard;
		return true; //successful
    }

    public void undoMove(int row, int col){
        int smallBoardRow = row / 3;
		int smallBoardCol = col / 3;
		int smallBoardCellRow = row % 3;
		int smallBoardCellCol = col % 3;
        bigBoard[smallBoardRow][smallBoardCol].undoMove(smallBoardCellRow, smallBoardCellCol);
    }
	
	public PLAYER checkWin(){
		//check diagonals
		if ((bigBoard[0][0].getWinner() == bigBoard[1][1].getWinner() && bigBoard[1][1].getWinner() == bigBoard[2][2].getWinner()) 
			|| (bigBoard[0][2].getWinner() == bigBoard[1][1].getWinner() && bigBoard[1][1].getWinner() == bigBoard[2][0].getWinner())) {
            if (bigBoard[1][1].getWinner() != PLAYER.NONE) {
                return bigBoard[1][1].getWinner();
            }
        }

        //check rows and columns
        for (int i = 0; i < 3; i++) {
            //rows
            if (bigBoard[i][0].getWinner() == bigBoard[i][1].getWinner() && bigBoard[i][1].getWinner() == bigBoard[i][2].getWinner()) {
                if (bigBoard[i][0].getWinner() != PLAYER.NONE) {
                    return bigBoard[i][0].getWinner();
                }
            }
            //columns
            if (bigBoard[0][i].getWinner() == bigBoard[1][i].getWinner() && bigBoard[1][i].getWinner() == bigBoard[2][i].getWinner()) {
                if (bigBoard[0][i].getWinner() != PLAYER.NONE) {
                    return bigBoard[0][i].getWinner();
                }
            }
        }
		return PLAYER.NONE;
	}
	
	public String toString(){
		String output = "";
		for(int row=0; row<9; row++){
			for(int col=0; col<9; col++){
				PLAYER currentSpace = bigBoard[row/3][col/3].getPlayer(row%3,col%3);
				if(currentSpace == PLAYER.NONE){
					output+=" ";
				} else {
					output+=currentSpace;
				}
				if(col==2||col==5){
					output+="|";
				}
				if(col!=8){
					output+="|";
				} else {
					output+="\n";
				}
			}
			if(row!=8){
				output+="-----||-----||------\n";
			}
			if(row==2||row==5){
				output+="-----||-----||------\n";
			}
		}
		output+="\n";
		for(int row=0; row<3; row++){
			for(int col=0; col<3; col++){
				PLAYER currentWinner = bigBoard[row][col].getWinner();
				if(currentWinner==PLAYER.NONE){
					output+=" ";
				} else if(currentWinner==PLAYER.TIE){
					output+="T";
				} else {
					output+=currentWinner;
				}
				if(col!=2){
					output+="|";
				} else {
					output+="\n";
				}
			}
			if(row!=2){
				output+="-----\n";
			}
		}
		return output;
	}
	
    public Board getBoard(int i, int j) {
        return bigBoard[i][j];
    }
}
