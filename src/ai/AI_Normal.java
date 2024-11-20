package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI_Normal extends AI_TicTacToe {
    private final Random random = new Random(); 

    public AI_Normal() {
    	
    }

    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        // Check if AI can win in the next move
        AI_Move winningMove = findWinningMove(Ai, rows, cols);
        if (winningMove != null) {
            return winningMove;
        }

        // Check if AI needs to block the player's winning move
        AI_Move blockingMove = findWinningMove(Player, rows, cols);
        if (blockingMove != null) {
            return blockingMove;
        }

        // Choose the center if available
        if (rows % 2 == 1 && cols % 2 == 1) { // Center only exists for odd-sized boards
            int center = rows / 2;
            if (tictactoe_board.getBox(center, center).getSymbol() == null) {
                return new AI_Move(center, center);
            }
        }

        // Choose a random corner if available
        AI_Move cornerMove = chooseRandomCornerMove(tictactoe_board, rows, cols);
        if (cornerMove != null) {
            return cornerMove;
        }

        // Fall back to a random move
        return randomMove(tictactoe_board, rows, cols);
    }

    // Method to find a winning move for the given symbol
    private AI_Move findWinningMove(Symbol symbol, int rows, int cols) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == null) { // Check if the spot is empty
                    board[r][c] = symbol; // Simulate the move
                    if (isWinningMove(r, c, symbol)) {
                        board[r][c] = null; // Undo the move
                        return new AI_Move(r, c);
                    }
                    board[r][c] = null; // Undo the move
                }
            }
        }
        return null; // No winning move found
    }

    // Method to choose a random corner move if available
    private AI_Move chooseRandomCornerMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        List<AI_Move> availableCorners = new ArrayList<>();

        // Define corners dynamically based on board size
        int[][] corners = {
            {0, 0}, {0, cols - 1}, {rows - 1, 0}, {rows - 1, cols - 1}
        };

        for (int[] corner : corners) {
            int r = corner[0], c = corner[1];
            if (tictactoe_board.getBox(r, c).getSymbol() == null) {
                availableCorners.add(new AI_Move(r, c));
            }
        }

        // Choose a random corner from the available ones
        if (!availableCorners.isEmpty()) {
            return availableCorners.get(random.nextInt(availableCorners.size()));
        }
        return null; // No corners available
    }

    // Helper function to check if a move is a winning move
    private boolean isWinningMove(int row, int col, Symbol symbol) {
        // Check row, column, and diagonals
        return checkRow(row, symbol) || checkColumn(col, symbol) || checkDiagonals(symbol);
    }

    private boolean checkRow(int row, Symbol symbol) {
        return board[row][0] == symbol && board[row][1] == symbol && board[row][2] == symbol;
    }

    private boolean checkColumn(int col, Symbol symbol) {
        return board[0][col] == symbol && board[1][col] == symbol && board[2][col] == symbol;
    }

    private boolean checkDiagonals(Symbol symbol) {
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }
}