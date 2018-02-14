public class Board {

    private PLAYER[][] board;
    private int moveCount;
    private PLAYER winner;

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

    private void setWinner(PLAYER winner) {
        this.winner = winner;
    }

    public PLAYER getWinner() {
        return winner;
    }

    public void checkWin() {
        PLAYER win = PLAYER.NONE;

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
        if(winner == PLAYER.NONE && moveCount == 9) {
            setWinner(PLAYER.TIE);
        }
    }
}
