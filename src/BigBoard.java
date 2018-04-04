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

    public boolean makeMove(PLAYER player, int row, int col) {
        int smallBoardRow = row / 3;
        int smallBoardCol = col / 3;
        int smallBoardCellRow = row % 3;
        int smallBoardCellCol = col % 3;
        Board smallBoard = bigBoard[smallBoardRow][smallBoardCol];
        smallBoard = smallBoard.makeMove(player, smallBoardCellRow, smallBoardCellCol);
        if (smallBoard == null) {
            return false; //unsuccessful
        }
        bigBoard[smallBoardRow][smallBoardCol] = smallBoard;
        return true; //successful
    }

    public void undoMove(int row, int col) {
        int smallBoardRow = row / 3;
        int smallBoardCol = col / 3;
        int smallBoardCellRow = row % 3;
        int smallBoardCellCol = col % 3;
        bigBoard[smallBoardRow][smallBoardCol].undoMove(smallBoardCellRow, smallBoardCellCol);
    }

    public Board getBoard(int i, int j) {
        return bigBoard[i][j];
    }
}
