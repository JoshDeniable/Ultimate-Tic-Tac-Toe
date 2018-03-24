import java.util.*;

public class PlayGame{
    private BigBoard gameBoard;
    private String lastMove;

    public PlayGame(){
    	gameBoard = BigBoard();
	lastMove = "";
    }

    public static String alphaBeta(int depth, int alpha, int beta, String move, PLAYER player){
        ArrayList<String> listOfMoves = possibleMoves();
        if(depth == 0 || listOfMoves.size() == 0){
	    //to figure out later
	}
	if(player == PLAYER.X){
	    player = PLAYER.O;
	} else if(player == PLAYER.O){
	    player = PLAYER.X;
	}
	for(String newMove:listOfMoves){
	    //make the move
            gameBoard.makeMove((Integer)move.substring(0,1),(Integer)move.substring(1,2),player);
	    String returnString = alphaBeta(depth-1, alpha, beta, newMove, player);
            int value = Integer.valueOf(returnString.substring(2));
	    //undo the move
	    gameBoard.undoMove((Integer)move.substring(0,1),(Integer)move.substring(1,2));
	    if(player == PLAYER.O){
	    	if(value <= beta) {
		    beta = value;
		    if(depth == GLOBAL_DEPTH){
		        move = returnString.substring(0,3);
		    }
		}
	    } else {
	        if(value > alpha) {
		    alpha = value;
		    if(depth == GLOBAL_DEPTH){
		        move = returnString.substring(0,3);
		    }
		}
	    }
	    if(alpha >= beta){
	        if(player == PLAYER.O){
		    return move+beta;
		} else {
		    return move+alpha;
		}
	    }
	}
	if(player == PLAYER.O){
	    return move+beta;
	} else {
	    return move+alpha
	}
    }
    
    private ArrayList<String> possibleMoves(){
        ArrayList<String> listOfMoves = new ArrayList<>();
	if(lastMove.equals("")){ // first move, can play anywhere
	    for(int i=0; i<9;i++){
	        for(int j=0;j<9;j++){
		    listOfMoves.add(i.toString()+j);
		}
	    }
	    return listOfMoves;
	}
	Board smallBoard = gameBoard.getBoard((Integer)lastMove.substring(0,1)/3,(Integer)lastMove.substring(1,2)/3);
	if(smallBoard.getWinner() == NONE){ // that board has not been won yet, have to play there
	    listOfMoves = possibleMovesWithinABoard((Integer)lastMove.substring(0,1)/3,(Integer)lastMove.substring(1,2)/3);
	    return listOfMoves;
	} else { //that board has been won, can play in any board where winner is PLAYER.NONE
	    for(int i=0; i<3; i++){
	        for(int j=0; j<3; j++{
		    if(gameBoard.getBoard(i,j).getWinner() == PLAYER.NONE){
		        listOfMoves.addAll(possibleMovesWithinABoard(i,j));
		    }
		}
	    }
	    return listOfMoves;
	}
	//if we get here, something wrong has happened
	return null;
    }

    private ArrayList<String> possibleMovesWithinABoard(int row, int col){
        ArrayList<String> possibleMoves = new ArrayList<>();
	for(int i=0; i<3; i++){
	    for(int j=0; j<3; j++){
	        if(gameBoard.getBoard(row,col).get(i,j) == PLAYER.NONE){
		    possibleMoves.add((row*3+i).toString()+(col*3+j));
		}
	    }
	}
    }

    public static void main(String[] args){
        PlayGame newGame = new PlayGame();
    }
}
