import java.util.*;

public class PlayGame {
    private static BigBoard gameBoard;
    private static String lastMove;
    private static final int GLOBAL_DEPTH = 6;

    public PlayGame() {
        gameBoard = new BigBoard();
        lastMove = "";
    }

    public static String alphaBeta(int depth, int alpha, int beta, String move, PLAYER player) {
        ArrayList<String> listOfMoves = possibleMoves();
        if (depth == 0 || listOfMoves.size() == 0) {
            return move + (Heuristic.ratePossition());
        }
        if (player == PLAYER.X) {
            player = PLAYER.O;
        } else if (player == PLAYER.O) {
            player = PLAYER.X;
        }
        for (String newMove : listOfMoves) {
            //make the move
            gameBoard.makeMove(player, Integer.parseInt(move.substring(0, 1)), Integer.parseInt(move.substring(1, 2)));
            String returnString = alphaBeta(depth - 1, alpha, beta, newMove, player);
            int value = Integer.valueOf(returnString.substring(2));
            //undo the move
            gameBoard.undoMove(Integer.parseInt(move.substring(0, 1)), Integer.parseInt(move.substring(1, 2)));
            if (player == PLAYER.O) {
                if (value <= beta) {
                    beta = value;
                    if (depth == GLOBAL_DEPTH) {
                        move = returnString.substring(0, 3);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;
                    if (depth == GLOBAL_DEPTH) {
                        move = returnString.substring(0, 3);
                    }
                }
            }
            if (alpha >= beta) {
                if (player == PLAYER.O) {
                    return move + beta;
                } else {
                    return move + alpha;
                }
            }
        }
        if (player == PLAYER.O) {
            return move + beta;
        } else {
            return move + alpha;
        }
    }

    private static ArrayList<String> possibleMoves() {
        ArrayList<String> listOfMoves = new ArrayList<>();
        if (lastMove.equals("")) { // first move, can play anywhere
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    listOfMoves.add("" + i + j);
                }
            }
            return listOfMoves;
        }
        Board smallBoard = gameBoard.getBoard(Integer.parseInt(lastMove.substring(0, 1)) / 3, Integer.parseInt(lastMove.substring(1, 2)) / 3);
        if (smallBoard.getWinner() == PLAYER.NONE) { // that board has not been won yet, have to play there
            listOfMoves = possibleMovesWithinABoard(Integer.parseInt(lastMove.substring(0, 1)) / 3, Integer.parseInt(lastMove.substring(1, 2)) / 3);
            return listOfMoves;
        } else { //that board has been won, can play in any board where winner is PLAYER.NONE
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard.getBoard(i, j).getWinner() == PLAYER.NONE) {
                        listOfMoves.addAll(possibleMovesWithinABoard(i, j));
                    }
                }
            }
            return listOfMoves;
        }
    }

    private static ArrayList<String> possibleMovesWithinABoard(int row, int col) {
        ArrayList<String> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard.getBoard(row, col).getPlayer(i, j) == PLAYER.NONE) {
                    possibleMoves.add("" + (row * 3 + i) + (col * 3 + j));
                }
            }
        }
        return possibleMoves;
    }

    public static void main(String[] args) {
        PlayGame newGame = new PlayGame();
    }
}
