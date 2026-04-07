import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String board[][] = new String[ROWS][COLS];

    //Pseudocode for TTT
        /*
            1. Initialize board using clearBoard, showBoard,
            initialize variables for current player (X) and moveCount (0),
            create a scanner for player input boolean for gameOver (false)
            2. Create a do while loop for the entire game (while(!gameover)),
            prompt current player to enter a move (row and column) using getRangedInt [1-3],
            subtract 1 to turn into 0-2 range
            3. Validate moving using isValidMove, if valid update board with current player's symbol,
            increment moveCount, showBoard, if invalid reprompt plus error message
        */

    public static void main(String[] args) {
        SafeInput.prettyHeader("Welcome to Tic Tac Toe");
        Scanner in = new Scanner(System.in);

        clearBoard();
        showBoard();

        String currentPlayer = "X";
        int moveCount = 0;
        boolean gameOver = false;

        do {
            System.out.println(currentPlayer + "'s turn");
            int row = SafeInput.getRangedInt(in, "Enter row [1-3]", 1, 3) - 1;
            int col = SafeInput.getRangedInt(in, "Enter column [1-3]", 1, 3) - 1;

            // Validate the move
            while (!isValidMove(row, col))
            {
                System.out.println("Invalid move — space already taken.");
                row = SafeInput.getRangedInt(in, "Enter row [1-3]", 1, 3) - 1;
                col = SafeInput.getRangedInt(in, "Enter column [1-3]", 1, 3) - 1;

            }
            board[row][col] = currentPlayer;
            moveCount++;
            showBoard();
        }while (!gameOver);
    }


        // Helper Methods
    private static void clearBoard() // sets all the board elements to a space
    {
        for(int row = 0; row < ROWS; row++)
        {
            for(int col = 0; col < COLS; col++)
            {
                board[row][col] = " "; // makes this cell a space
            }
        }
    }
    private static void showBoard() // displays the current board to the user
    {
        System.out.println();
        for (int row = 0; row < ROWS; row++)
        {
            System.out.printf(" %s | %s | %s \n", board[row][0], board[row][1], board[row][2]);
            if (row < ROWS - 1)
            {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }
    private static boolean isValidMove(int row, int col) // returns true if there is a space at proposed move coordinates, legal move
    {
        return board[row][col].equals(" ");
    }
    private static boolean isWin(String player) // checks to see if there is a win for the given player at current time in game
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }
        return false;
    }
    private static boolean isRowWin(String player) // checks to see if there is a row win for specified player
    {
        for(int row = 0; row < ROWS; row++)
        {
            if(board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private static boolean isColWin(String player) // checks to see if there is a column win for specified player
    {
        for(int col = 0; col < COLS; col++)
        {
            if(board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player))
            {
                return true;
            }
        }
        return false; // no column win
    }
    private static boolean isDiagnalWin(String player) // checks to see if there is a diagonal win for specified player
    {
        // Check both diagonals
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player))
        {
            return true;
        }
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))
        {
            return true;
        }
        return false;
    }
    private static boolean isTie(int moveCount)
    {
        // Case 1: full board tie
        if (moveCount == 9)
            return true;

        // Case 2: early tie (all win vectors blocked)
        if (moveCount >= 7 && allVectorsBlocked())
            return true;

        return false;
    }
    private static boolean isVectorBlocked(String a, String b, String c)
    {
        boolean hasX = a.equals("X") || b.equals("X") || c.equals("X");
        boolean hasO = a.equals("O") || b.equals("O") || c.equals("O");
        return hasX && hasO;
    }
    private static boolean allVectorsBlocked()
    {
        // rows
        boolean r0 = isVectorBlocked(board[0][0], board[0][1], board[0][2]);
        boolean r1 = isVectorBlocked(board[1][0], board[1][1], board[1][2]);
        boolean r2 = isVectorBlocked(board[2][0], board[2][1], board[2][2]);

        // columns
        boolean c0 = isVectorBlocked(board[0][0], board[1][0], board[2][0]);
        boolean c1 = isVectorBlocked(board[0][1], board[1][1], board[2][1]);
        boolean c2 = isVectorBlocked(board[0][2], board[1][2], board[2][2]);

        // diagonals
        boolean d0 = isVectorBlocked(board[0][0], board[1][1], board[2][2]);
        boolean d1 = isVectorBlocked(board[0][2], board[1][1], board[2][0]);

        return r0 && r1 && r2 && c0 && c1 && c2 && d0 && d1;
    }

}
