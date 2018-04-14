public class Board {

    private PLAYER[][] board;
    private int moveCount;
    private PLAYER winner;

    //creates a small board and initializes each space as PLAYER.NONE
    public Board() {
        moveCount = 0;
        board = new PLAYER[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = PLAYER.NONE;
            }
        }
        winner = PLAYER.NONE;
    }

    //set the winner
    private void setWinner(PLAYER winner) {
        this.winner = winner;
    }

    //checks if someone has won this board, if it has been tied, or if still no winner
    public PLAYER getWinner() {
        //check diagonals
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            if (board[1][1] != PLAYER.NONE) {
                setWinner(board[1][1]);
            }
        }

        //check rows and columns
        for (int i = 0; i < 3; i++) {
            //rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != PLAYER.NONE) {
                    setWinner(board[i][0]);
                }
            }
            //columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != PLAYER.NONE) {
                    setWinner(board[0][i]);
                }
            }
        }

        //check for tie
        if (winner == PLAYER.NONE && moveCount == 9) {
            setWinner(PLAYER.TIE);
        }
        return winner;
    }

    //make a move on the board, increment the number of moves made, and return the updated board
    public Board makeMove(PLAYER move, int row, int col) {
        if (winner == PLAYER.NONE) {
            if (board[row][col] == PLAYER.NONE) {
                board[row][col] = move;
                moveCount++;
                getWinner();
            } else {
                return null; //couldn't make the move as someone had already played there
            }
        } else {
            return null; //couldn't make the move as there is already a winner at this board
        }
        return this;
    }

    //undo a move and decrement moveCount, used by minimax, not human player
    public Board undoMove(int row, int col) {
        winner = PLAYER.NONE;
        board[row][col] = PLAYER.NONE;
        moveCount--;
        return this;
    }

    //see who occupies an individual square on the board
	public PLAYER getPlayer(int i, int j) { return board[i][j]; }
}
