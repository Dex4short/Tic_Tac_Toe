package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_Hard extends AI_TicTacToe{
    
    public AI_Hard() {
    	
	}
    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
    	if(isBoardEmpty(tictactoe_board, rows, cols)) {
    		return randomMove(tictactoe_board, rows, cols);
    	}
    	else {
    		return bestMove(tictactoe_board, rows, cols);
    	}
    }
    private AI_Move bestMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1, moveCol = -1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) {
                    board[row][col] = AI;
                    
                    int score = minimax(board, 0, false, rows, cols);
                    board[row][col] = null;
                    
                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = row;
                        moveCol = col;
                    }
                }
            }
        }
        
        if (moveRow != -1 && moveCol != -1) {
            board[moveRow][moveCol] = AI;
        	return new AI_Move(moveRow, moveCol);
        }
        
        return null;
    }
    private int minimax(Symbol[][] board, int depth, boolean isMaximizing, int rows, int cols) {
        int score = evaluateBoard(rows, cols);
        if (score == 10 || score == -10 || isDraw(rows, cols)) return score;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = AI;
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false, rows, cols));
                        board[row][col] = null;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = PLAYER;
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true, rows, cols));
                        board[row][col] = null;
                    }
                }
            }
            return bestScore;
        }
    }
    private int evaluateBoard(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == AI) return 10;
                if (board[row][0] == PLAYER) return -10;
            }
        }
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == AI) return 10;
                if (board[0][col] == PLAYER) return -10;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == AI) return 10;
            if (board[0][0] == PLAYER) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == AI) return 10;
            if (board[0][2] == PLAYER) return -10;
        }
        return 0;
    }
    private boolean isDraw(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == null) return false;
            }
        }
        return true;
    }
}
