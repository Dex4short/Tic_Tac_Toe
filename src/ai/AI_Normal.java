package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_Normal extends AI_TicTacToe {
    public AI_Normal() {
        super();
    }

    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        // Check if AI can win in the next move
        AI_Move winningMove = findWinningMove(AI, rows, cols);
        if (winningMove != null) {
            return winningMove;
        }

        // Check if AI needs to block the player's winning move
        AI_Move blockingMove = findWinningMove(PLAYER, rows, cols);
        if (blockingMove != null) {
            return blockingMove;
        }

        //  Choose the center if available
        if (tictactoe_board.getBox(1, 1).getSymbol() == null) {
            return new AI_Move(1, 1);
        }

        // Choose a corner if available
        AI_Move cornerMove = chooseCornerMove(tictactoe_board);
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

    // Method to choose a corner move if available
    private AI_Move chooseCornerMove(TicTacToeBoard tictactoe_board) {
        int[][] corners = { {0, 0}, {0, 2}, {2, 0}, {2, 2} };
        for (int[] corner : corners) {
            int r = corner[0], c = corner[1];
            if (tictactoe_board.getBox(r, c).getSymbol() == null) {
                return new AI_Move(r, c);
            }
        }
        return null;
    }
}
