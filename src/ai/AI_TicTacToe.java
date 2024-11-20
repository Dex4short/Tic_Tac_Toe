package ai;

import java.util.Random;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_TicTacToe{
    public Symbol board[][];
    public Symbol Player;
    public Symbol Ai;
    
    public AI_TicTacToe() {
    	
	}
    public AI_Move make_move(TicTacToeBoard tictactoe_board, Symbol assigned_symbol) {
    	int
    	rows = tictactoe_board.getRows(),
    	cols = tictactoe_board.getColumns();

    	Ai = assigned_symbol;
    	if(Ai == Symbol.X) {
    		Player = Symbol.O;
    	}
    	else {
    		Player = Symbol.X;
    	}
    	
    	board = new Symbol[rows][cols];
    	for(int r=0; r<rows; r++) {
    		for(int c=0; c<cols; c++) {
    			board[r][c] = tictactoe_board.getBox(r, c).getSymbol();
    		}
    	}
    	
		return onMakeMove(tictactoe_board, rows, cols);
    }
    public AI_Move onMakeMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
    	return randomMove(tictactoe_board, rows, cols);
    }
    public AI_Move randomMove(TicTacToeBoard tictactoe_board, int rows, int cols) {
    	Random random = new Random();
    	int
    	moveRow = random.nextInt(rows),
    	moveCol = random.nextInt(cols);

    	if(tictactoe_board.isBoardHasVacant()) {
        	while(tictactoe_board.getBox(moveRow, moveCol).getSymbol() != null) {
            	moveRow = random.nextInt(rows);
            	moveCol = random.nextInt(cols);
        	}
    		return new AI_Move(moveRow, moveCol);
    	}
    	
    	return null;
    }
}
