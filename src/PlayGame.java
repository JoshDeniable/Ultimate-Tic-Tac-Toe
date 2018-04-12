/*
Stanimir Genov & Joshua DeNoble
April 18
Concepts of Artificial Intelligence(graduate) Spring 2018

This program has been written to play the game of Ultimate Tic-Tac-Toe with the ability to be able to beat any human
*/

import java.util.*;
import java.io.*;

public class PlayGame {
    private static BigBoard gameBoard;
    private static String lastMove;
    private static final int GLOBAL_DEPTH = 6; // Test depths on both PC and Laptop (try to get to 20ish)
    private static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));

    public PlayGame() {
        gameBoard = new BigBoard();
        lastMove = "";
    }

    private static  void mainMenu() {
        System.out.println("-----ULTIMATE TIC-TAC-TOE-----\n");
        System.out.println("1. Start New Game");
        System.out.println("2. Read Rules");
        System.out.println("3. Exit Game\n");
        boolean selectionMade = false;
        while (!selectionMade) {
            System.out.println("To make your selection, type the corresponding number and hit Enter");
            try {
                String input = readInput();
                switch (input) {
                    case "1":
                        selectionMade = true;
                        play();
                        break;
                    case "2":
                        selectionMade = true;
                        displayRules();
                        break;
                    case "3":
                        selectionMade = true;
                        System.out.println("Terminating Program... Goodbye");
                        System.exit(0);
                    default:
                        System.out.println("Input is invalid, try again!");
                        continue;
                }
            } catch (IOException e) {
                System.out.println("Input is invalid, try again!");
                mainMenu();
            }
        }
    }

    private static void displayRules() {
        //sout the rules for Ultimate Tic-Tac-Toe here
    }

    private static String readInput() throws IOException{
        String input = "";
        input = stdin.readLine().toLowerCase();
        //System.out.println(input);
        if(input == null)
        {
            return "";
        }
        return input.trim();
    }

    private static void play() {
        //initialize game
        gameBoard = new BigBoard();
        lastMove = "";

        boolean done = false;
        while(!done) {
            //make moves, check for winner
            //Player chooses to be X or O? X always goes first.
			String cellRow = "[0-8]";
			String cellCol = "[0-8]";
			int playerRowMin = 0; int playerColMin = 0;
			int playerRowMax = 8; int playerColMax = 8;
			if (!lastMove.equals("")) {
				int bigRow = Integer.parseInt(lastMove.substring(0, 1)) % 3;
				int bigCol = Integer.parseInt(lastMove.substring(1, 2)) % 3;
				Board smallBoard = gameBoard.getBoard(bigRow, bigCol);
				if (smallBoard.getWinner() == PLAYER.NONE) { // that board has not been won yet, have to play there
					playerRowMin = bigRow*3;
					playerRowMax = playerRowMin + 2;
					playerColMin = bigCol*3;
					playerColMax = playerColMin + 2;
					cellRow = "[" + playerRowMin + "-" + playerRowMax + "]";
					cellCol = "[" + playerColMin + "-" + playerColMax + "]";
				}
			}
			System.out.println("For this turn, you must choose a cell between rows " + cellRow + " and columns " + cellCol);
            System.out.print("Select the cell you'd like to control:");
            try {
                String input = readInput();
                if (!input.matches("^[0-8]{2}$")) {
                    if (input.equals("quit") || input.equals("exit")) {
                        System.out.println("Are you sure you want to quit? y/n");
                        input = readInput();
                        if (input.equals("y")) {
                            System.out.println("Terminating Program... Goodbye");
                            System.exit(0);
                        } else {
                            continue;
                        }
                    } else {
                        System.out.println("Invalid input, try again!");
                        continue;
                    }
                }
                input =  input.substring(0,2);
                int x = Integer.parseInt("" + input.charAt(0));
                int y = Integer.parseInt("" + input.charAt(1));
                if (x >= playerRowMin && x <= playerRowMax && y >= playerColMin && y <= playerColMax) {
					if (gameBoard.getPlayer(x, y) == PLAYER.NONE) {
						gameBoard.makeMove(PLAYER.X, x, y);
					} else {
						System.out.println("\nThat cell is already occupied! Try again!");
						continue;
					}
				} else {
                	continue;
				}
                lastMove = input;
                //String compIn = alphaBeta(GLOBAL_DEPTH, -999999999, 999999999, input, PLAYER.O);
                String compIn = alphaBeta(GLOBAL_DEPTH, -999999999, 999999999, input, true);
				int compX = Integer.parseInt(compIn.substring(0,1));
                int compY = Integer.parseInt(compIn.substring(1,2));
                gameBoard.makeMove(PLAYER.O, compX, compY);
                lastMove = compIn;
                System.out.println(gameBoard.toString());
            }
            catch (IOException e) {
                //Do nothing, probably
            }
        }
    }

	//public static String alphaBeta(int depth, float alpha, float beta, String move, PLAYER player) {
    public static String alphaBeta(int depth, float alpha, float beta, String move, boolean maximizingPlayer) {
        ArrayList<String> listOfMoves = possibleMoves();
        if (depth == 0 || listOfMoves.size() == 0) {
			//return move + (Heuristic.ratePosition(gameBoard,move,player));
			if(maximizingPlayer){
				return move + (Heuristic.ratePosition(gameBoard,move,PLAYER.O));
			} else {
				return move + (Heuristic.ratePosition(gameBoard,move,PLAYER.X));
			}
        }
		float tempValue;
        if (maximizingPlayer) {
			value = -999999999;
			for(String newMove : listOfMoves){
				gameBoard.makeMove(PLAYER.O, Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				String temp = lastMove;
				lastMove = newMove.substring(0,2);
				String returnString = alphaBeta(depth - 1, alpha, beta, newMove, false);
				float tempValue = Float.valueOf(returnString.substring(2));
				gameBoard.undoMove(Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				value = Math.max(value, tempValue);
				alpha = Math.max(alpha, value);
				if(beta <= alpha){
					break; //beta cut-off
				}
			}
			return move+value;
        } else {
            value = 999999999;
			for(String newMove : listOfMoves){
				gameBoard.makeMove(PLAYER.X, Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				String temp = lastMove;
				lastMove = newMove.substring(0,2);
				String returnString = alphaBeta(depth - 1, alpha, beta, newMove, true);
				float tempValue = Float.valueOf(returnString.substring(2));
				gameBoard.undoMove(Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				value = Math.min(value,tempValue);
				beta = Math.min(beta,value);
				if(beta <= alpha){
					break;
				}
			}
			return move + value;
        }
	}
		/*
		if(player == PLAYER.O){
			player = PLAYER.X;
		} else {
			player = PLAYER.O;
		}
        for (String newMove : listOfMoves) {
            //make the move
            gameBoard.makeMove(player, Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
            String temp = lastMove;
            lastMove = newMove.substring(0,2);
            String returnString = alphaBeta(depth - 1, alpha, beta, newMove, player);
            float value = Float.valueOf(returnString.substring(2));
            //undo the move
            gameBoard.undoMove(Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
            lastMove = temp;
            if (player == PLAYER.O) {
                if (value <= beta) {
                    beta = value;
                    if (depth == GLOBAL_DEPTH) {
                        move = returnString.substring(0, 2);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;
                    if (depth == GLOBAL_DEPTH) {
                        move = returnString.substring(0, 2);
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
    } */

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
        Board smallBoard = gameBoard.getBoard(Integer.parseInt(lastMove.substring(0, 1)) % 3, Integer.parseInt(lastMove.substring(1, 2)) % 3);
        if (smallBoard.getWinner() == PLAYER.NONE) { // that board has not been won yet, have to play there
            listOfMoves = possibleMovesWithinABoard(Integer.parseInt(lastMove.substring(0, 1)) % 3, Integer.parseInt(lastMove.substring(1, 2)) % 3);
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
        mainMenu();
    }
}
