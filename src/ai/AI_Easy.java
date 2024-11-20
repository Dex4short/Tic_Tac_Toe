package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_Easy extends AI_TicTacToe {

    public AI_Easy() {
    	
    }
    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        // Check if AI can win in the next move
        AI_Move winningMove = findWinningMove(Ai, rows, cols);
        if (winningMove != null) {
            return winningMove;
        }
        // Fall back to a random move
        return super.onMakeMove(tictactoe_board, rows, cols);
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
    // Helper function to check if placing a symbol at (row, col) is a winning move
    private boolean isWinningMove(int row, int col, Symbol symbol) {
        return checkRow(row, symbol) || checkColumn(col, symbol) || checkDiagonals(symbol);
    }
    private boolean checkRow(int row, Symbol symbol) {
        for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] != symbol) {
                return false;
            }
        }
        return true;
    }
    private boolean checkColumn(int col, Symbol symbol) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][col] != symbol) {
                return false;
            }
        }
        return true;
    }
    private boolean checkDiagonals(Symbol symbol) {
        boolean diagonal1 = true, diagonal2 = true;
        for (int i = 0; i < board.length; i++) {
            diagonal1 &= (board[i][i] == symbol); // Top-left to bottom-right
            diagonal2 &= (board[i][board.length - i - 1] == symbol); // Top-right to bottom-left
        }
        return diagonal1 || diagonal2;
    }
}