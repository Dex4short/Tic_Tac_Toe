package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;
import java.util.HashMap;

public class AI_Hard extends AI_TicTacToe {
    
    private HashMap<String, Integer> memo; // Cache for board evaluations

    public AI_Hard() {
    	
    }
    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        memo = new HashMap<>();
        if (tictactoe_board.isBoardEmpty()) {
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
                    board[row][col] = Ai;
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
        if (memo.containsKey(boardState)) return memo.get(boardState);/// // Return cached result if available

        int score = evaluateBoard(rows, cols);
        if (score == 10 || score == -10 || isDraw(rows, cols)) return score;

        if (depth >= 10) return 0; // Limit depth for larger grids avoid slowing down computation

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) {
                    board[row][col] = isMaximizing ? Ai : Player;
                    int currentScore = minimax(board, depth + 1, !isMaximizing, alpha, beta, rows, cols);
                    board[row][col] = null;

                    if (isMaximizing) {
                        bestScore = Math.max(bestScore, currentScore);// Maximize AI score
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        bestScore = Math.min(bestScore, currentScore);// Minimize Player score
                        beta = Math.min(beta, bestScore);
                    }

                    if (beta <= alpha) break;
                }
            }
        }
        memo.put(boardState, bestScore);
        return bestScore;
    }
    //convert to string to save in memo
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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (checkRow(row, col, rows, cols)) {
                    return board[row][col] == Ai ? 10 : -10;
                }
            }
        }
        return 0;
    }

    private boolean checkRow(int row, int col, int rows, int cols) {
        Symbol symbol = board[row][col];
        if (symbol == null) return false;
    
        // Check horizontal
        if (col + 2 < cols && board[row][col + 1] == symbol && board[row][col + 2] == symbol) return true;
        
        // Check vertical
        if (row + 2 < rows && board[row + 1][col] == symbol && board[row + 2][col] == symbol) return true;
        
        // Check diagonal (down-right)
        if (row + 2 < rows && col + 2 < cols && board[row + 1][col + 1] == symbol && board[row + 2][col + 2] == symbol) return true;
        
        // Check anti-diagonal (down-left)
        if (row + 2 < rows && col - 2 >= 0 && board[row + 1][col - 1] == symbol && board[row + 2][col - 2] == symbol) return true;
    
        return false;
    }
}