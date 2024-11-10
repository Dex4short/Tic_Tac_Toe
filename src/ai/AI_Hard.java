package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;
import java.util.HashMap;

public class AI_Hard extends AI_TicTacToe {
    
    private HashMap<String, Integer> memo; // Cache for board evaluations

    public AI_Hard() {
        memo = new HashMap<>();
    }

    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        if (isBoardEmpty(tictactoe_board, rows, cols)) {
            return randomMove(tictactoe_board, rows, cols);
        } else {
            return bestMove(tictactoe_board, rows, cols);
        }
    }

    private AI_Move bestMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1, moveCol = -1;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) {
                    board[row][col] = AI;
                    int score = minimax(board, 0, false, alpha, beta, rows, cols);
                    board[row][col] = null;

                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = row;
                        moveCol = col;
                    }
                }
            }
        }
        return (moveRow != -1 && moveCol != -1) ? new AI_Move(moveRow, moveCol) : null;
    }

    private int minimax(Symbol[][] board, int depth, boolean isMaximizing, int alpha, int beta, int rows, int cols) {
        String boardState = boardToString(board);
        if (memo.containsKey(boardState)) return memo.get(boardState);

        int score = evaluateBoard(rows, cols);
        if (score == 10 || score == -10 || isDraw(rows, cols)) return score;

        if (depth >= 5) return 0; // Limit depth for larger grids

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) {
                    board[row][col] = isMaximizing ? AI : PLAYER;
                    int currentScore = minimax(board, depth + 1, !isMaximizing, alpha, beta, rows, cols);
                    board[row][col] = null;

                    if (isMaximizing) {
                        bestScore = Math.max(bestScore, currentScore);
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        bestScore = Math.min(bestScore, currentScore);
                        beta = Math.min(beta, bestScore);
                    }

                    if (beta <= alpha) break;
                }
            }
        }
        memo.put(boardState, bestScore);
        return bestScore;
    }

    private String boardToString(Symbol[][] board) {
        StringBuilder sb = new StringBuilder();
        for (Symbol[] row : board) {
            for (Symbol cell : row) {
                sb.append(cell == null ? "N" : cell.toString());
            }
        }
        return sb.toString();
    }

    private boolean isDraw(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) return false;
            }
        }
        return true;
    }

    private int evaluateBoard(int rows, int cols) {
        int score = 0;
        // Check all possible 3-in-a-row combinations for any row, column, or diagonal
        for (int row = 0; row < rows; row++) {
            score += checkLine(row, 0, 0, 1, rows, cols);  // Rows
        }
        for (int col = 0; col < cols; col++) {
            score += checkLine(0, col, 1, 0, rows, cols);  // Columns
        }
        score += checkLine(0, 0, 1, 1, rows, cols);  // Diagonal
        score += checkLine(0, cols - 1, 1, -1, rows, cols);  // Anti-Diagonal

        return score;
    }

    // Helper function to check if there are 3 consecutive marks in a line
    private int checkLine(int startRow, int startCol, int rowDir, int colDir, int rows, int cols) {
        int aiCount = 0, playerCount = 0;

        for (int i = 0; i < 3; i++) {  // Check the line for 3 consecutive cells
            int row = startRow + i * rowDir;
            int col = startCol + i * colDir;
            if (row >= rows || col >= cols || row < 0 || col < 0) return 0;
            
            Symbol cell = board[row][col];
            if (cell == AI) aiCount++;
            if (cell == PLAYER) playerCount++;
        }

        if (aiCount == 3) return 10;  // AI wins
        if (playerCount == 3) return -10;  // Player wins
        return 0;
    }

    
}
