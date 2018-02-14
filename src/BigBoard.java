public class BigBoard {

    public Board[][] bigBoard;

    public BigBoard() {
        bigBoard = new Board[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bigBoard[i][j] = new Board();
            }
        }
    }
}
