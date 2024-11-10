package ai;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_Hard extends AI_TicTacToe{
    
    public AI_Hard() {
    
	}
    @Override
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
        if(isBoardEmpty(tictactoe_board, rows, cols)) {//allows AI to make a more diverse first move i it 
            return randomMove(tictactoe_board, rows, cols);//is the first to move
        }
        else {//apply the minimax algorithm for the following turns
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
                    
                    int score = minimax(board, 0, false,alpha,beta, rows, cols);
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
    

    private int minimax(Symbol[][] board, int depth, boolean isMaximizing,int alpha,int beta, int rows, int cols) {
        System.out.println("minimaxing: paramater values excluding board depth:" + depth + 
        " isMaximizing:" + isMaximizing + " alpha:" + alpha + " beta:" + beta);
        int score = evaluateBoard(rows, cols);
        if (score == 10 || score == -10 || isDraw(rows, cols)) return score;

        if (isMaximizing) {//maximizing
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = AI;
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false,alpha,beta, rows, cols));//get the greater value 
                        board[row][col] = null;
                        alpha = Math.max(alpha,bestScore);
                        if(beta <= alpha){
                            //stop exploring the game tree
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        } else {//minimize
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (board[row][col] == null) {
                        board[row][col] = PLAYER;
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true,alpha,beta, rows, cols));
                        board[row][col] = null;
                        beta = Math.min(beta,bestScore);
                        if(beta <= alpha){
                            //stop exlporing the game tree
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        }
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
}
