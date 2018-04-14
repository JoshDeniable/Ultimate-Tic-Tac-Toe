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
    private static final int GLOBAL_DEPTH = 9; // Odd depths are better for good AI
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
                        System.out.println("Input is invalid, try again!\n");
                }
            } catch (IOException e) {
                System.out.println("Input is invalid, try again!");
                mainMenu();
            }
        }
    }

    private static void displayRules() {
        System.out.println("In order to win, you have to win the big board in the same way you would with a regular Tic-Tac-Toe game,");
        System.out.println("by controlling 3 of the small boards in a row. When you play in a certain square within a small board, ");
        System.out.println("your opponent has to play in the corresponding small board. For example, if you play in the top right of ");
        System.out.println("a board, your opponent will have to play in the top right board. If that board has already been won or is");
        System.out.println("is completely filled but no one has won it, your opponent can play in any board that has not been won.");
    }

    private static String readInput() throws IOException{
        String input;
        input = stdin.readLine().toLowerCase();
        return input.trim();
    }

    private static void play() {
        //initialize game
        gameBoard = new BigBoard();
        lastMove = "";
        String winString;

        while(true) {
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
            System.out.print("Select the cell you'd like to control: ");
            try {
                String input = readInput();
                if (!input.matches("^[0-8]{2}$")) {
                	switch (input) {
						case "quit":
							System.out.println("Are you sure you want to quit to the Main Menu? y/n");
							input = readInput();
							if (input.equals("y")) {
								mainMenu();
							} else {
								continue;
							}
						case "exit":
							System.out.println("Are you sure you want to quit to the Main Menu? y/n");
							input = readInput();
							if (input.equals("y")) {
								mainMenu();
							} else {
								continue;
							}
						case "restart":
							System.out.println("Are you sure you want to restart this game? y/n");
							input = readInput();
							if (input.equals("y")) {
								System.out.println("\n\n\n\n\n\n\n\n\n\n");
								play();
								break;
							}
						default :
							System.out.println("Invalid input, try again!\n");
							continue;
					}
                }
                input =  input.substring(0,2);
                int x = Integer.parseInt("" + input.charAt(0));
                int y = Integer.parseInt("" + input.charAt(1));
                if (x >= playerRowMin && x <= playerRowMax && y >= playerColMin && y <= playerColMax) {
					if (gameBoard.getPlayer(x, y) == PLAYER.NONE) {
						gameBoard.makeMove(PLAYER.X, x, y);
                        if (gameBoard.checkWin() == PLAYER.X) {
                            winString = ("\nCongratulations, you've won!");
                            break;
                        }
					} else {
						System.out.println("\nThat cell is already occupied! Try again!");
						continue;
					}
				} else {
                	continue;
				}
                lastMove = input;
                String compIn = alphaBeta(GLOBAL_DEPTH, -999999999, 999999999, input, true);
				int compX = Integer.parseInt(compIn.substring(0,1));
                int compY = Integer.parseInt(compIn.substring(1,2));
                gameBoard.makeMove(PLAYER.O, compX, compY);
                if (gameBoard.checkWin() == PLAYER.O) {
                    winString = ("\nYou have been defeated...");
                    break;
                }
                lastMove = compIn;
                System.out.println(gameBoard.toString() + "\nPlayer O has taken cell " + lastMove.substring(0,2));
				System.out.println("It is now Player X's turn\n");
            }
            catch (IOException e) {
                //Do nothing, probably
            }
        }
        System.out.println(winString);
    }

	//public static String alphaBeta(int depth, float alpha, float beta, String move, PLAYER player) {
    private static String alphaBeta(int depth, float alpha, float beta, String move, boolean maximizingPlayer) {
        ArrayList<String> listOfMoves = possibleMoves();
        if (depth == 0 || listOfMoves.size() == 0) {
            return move + (Heuristic.ratePosition(gameBoard));
        }
		float tempValue;
        String bestMove = listOfMoves.get(0);
        if (maximizingPlayer) {
			float value = (float) Integer.MIN_VALUE;
			for(String newMove : listOfMoves){
				gameBoard.makeMove(PLAYER.O, Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				String temp = lastMove;
				lastMove = newMove.substring(0,2);
				String returnString = alphaBeta(depth - 1, alpha, beta, newMove, false);
				tempValue = Float.valueOf(returnString.substring(2));
				gameBoard.undoMove(Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				lastMove = temp;
				if(value!=tempValue) {
                    value = Math.max(value, tempValue);
                    alpha = Math.max(alpha, value);
                    if (value == tempValue) {
                        bestMove = newMove.substring(0, 2);
                    }
                }
				if(beta <= alpha){
					break; //beta cut-off
				}
			}
			return bestMove + value;
        } else {
            float value = (float) Integer.MAX_VALUE;
			for(String newMove : listOfMoves){
				gameBoard.makeMove(PLAYER.X, Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				String temp = lastMove;
				lastMove = newMove.substring(0,2);
				String returnString = alphaBeta(depth - 1, alpha, beta, newMove, true);
				tempValue = Float.valueOf(returnString.substring(2));
				gameBoard.undoMove(Integer.parseInt(newMove.substring(0, 1)), Integer.parseInt(newMove.substring(1, 2)));
				lastMove = temp;
				if(value!=tempValue) {
                    value = Math.min(value, tempValue);
                    beta = Math.min(beta, value);
                    if (value == tempValue) {
                        bestMove = newMove.substring(0, 2);
                    }
                }
				if(beta <= alpha){
					break; //alpha cut-off
				}
			}
			return bestMove + value;
        }
	}

    private static ArrayList<String> possibleMoves() {
        ArrayList<String> listOfMoves = new ArrayList<>();
        if(gameBoard.checkWin()!=PLAYER.NONE){
            return listOfMoves;
        }
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
