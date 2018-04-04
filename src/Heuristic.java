import java.util.Random;

public class Heuristic{
	public static float ratePosition(){
		int result = 0;
		result += heuristic1();
		result += heuristic2();
		result += heuristic3();
		return result;
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