import java.util.Random;

public class Heuristic{
	public static float ratePosition(BigBoard gameBoard){
		int result = 0;
		PLAYER winner = gameBoard.checkWin()
		if(winner==PLAYER.NONE){
			result += heuristic1();
			result += heuristic2();
			result += heuristic3();
			return result;
		} else {
			if(winner == PLAYER.X){
				return -999999999;
			} else if(winner == PLAYER.O){
				return 9999999999;
			} else {
				return 0;
			}
		}
	}
	//to be completed later
	private static float heuristic1(){
		Random rand = new Random();
		return rand.nextFloat();
	}
	private static float heuristic2(){
		Random rand = new Random();
		return rand.nextFloat();
	}
	private static float heuristic3(){
		Random rand = new Random();
		return rand.nextFloat();
	}
} 