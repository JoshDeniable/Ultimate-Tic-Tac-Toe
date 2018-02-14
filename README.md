# Ultimate-Tic-Tac-Toe
by Joshua DeNoble and Stanimir Genov


 This is an artificial intelligence for playing the game Ultimate Tic-Tac-Toe. The game will be displayed via a text UI. Whenever it is the player's move, the board will be printed out with a prompt asking where the user wants to play, the user will then input the position as two integers representing the row and column. We plan to use minimax with alpha-beta pruning. The game board will represented as a 3x3 matrix where in each cell is another 3x3 matrix. A move will be represented by placing an X or an O in an unoccupied space within one of the inner matrices. Once a small board has been won, the remaining free spaces in that board will be filled in with slashes to indicate that no one can play in that board anymore. If someone is sent to a board that has already been won, they will instead get to play in any open board they want.
  
One heuristic that we have in mind to use in the solution of this problem is that the AI should have each of the small tic-tac-toe boards weighed by some factor in terms of importance. For example: give highest priority to winning the center tic-tac-toe board as it has four possible ways it can win with it if it has won that board, next highest priority to corner boards as it has three possible ways to win with those, and lowest priority to middle border boards as it can only win one way using each of those. 
  
A defensive heuristic might be that the AI will want to send the opponent to a board with less advantageous possibilities. That way the program will have a smaller branching factor to look at and can observe all possibilities faster as well as the benefit of restricting its opponent.
  
An offensive heuristic might be that the AI will play in locations that will bring it closer to completing that inner board. 
