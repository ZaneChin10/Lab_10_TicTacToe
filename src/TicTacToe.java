import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String board[][] = new String[ROWS][COLS];

    //Pseudocode for TTT
        /*
        START PROGRAM
        Display pretty header
        Create Scanner to get user input

        DO   // outer loop for getYNConfirm
            Clear board
            Display board
            Set currentPlayer to X
            Set moveCount to 0
            Set gameOver to false

        DO   // inner game loop that runs until gameOver
            Display currentPlayer's turn
            Prompt player for row (1–3), convert to 0–2 using -1
            Prompt player for column (1–3), convert to 0–2 using -1

        WHILE move is not valid
            Display error message
            Prompt again for row and column (1–3), convert to 0–2 using -1
        END WHILE

        Place currentPlayer's symbol (X/O) on the board
        Increment moveCount
        Display the board

        IF moveCount >= 5 AND isWin(currentPlayer)
            Display win message
            Set gameOver to true

        ELSE IF isTie(moveCount)
            Display tie message
            Set gameOver to true
        END IF

        IF gameOver is false
            Switch currentPlayer (X to O, or O to X)
        END IF

        WHILE gameOver is false

            Ask user if they want to play again (Y/N)
        WHILE user chooses Yes

        END PROGRAM
        */

    public static void main(String[] args) {
        SafeInput.prettyHeader("Welcome to Tic Tac Toe");
        Scanner in = new Scanner(System.in);

        boolean keepPlaying;
        do {
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
                while (!isValidMove(row, col)) {
                    System.out.println("Invalid move — space already taken.");
                    row = SafeInput.getRangedInt(in, "Enter row [1-3]", 1, 3) - 1;
                    col = SafeInput.getRangedInt(in, "Enter column [1-3]", 1, 3) - 1;

                }
                board[row][col] = currentPlayer;
                moveCount++;
                showBoard();

                if (moveCount >= 5 && isWin(currentPlayer)) {
                    System.out.println(currentPlayer + " wins!");
                    gameOver = true;
                } else if (isTie(moveCount)) {
                    System.out.println("It's a tie!");
                    gameOver = true;
                }
                if (!gameOver) {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            } while (!gameOver);
            keepPlaying = SafeInput.getYNConfirm(in, "Play again [Y/N]?");
        }while(keepPlaying);
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
