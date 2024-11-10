package ai;

import java.util.Random;

import drawables.TicTacToeBoard;
import enums.Symbol;

public class AI_TicTacToe {
    public Symbol board[][];
    public Symbol PLAYER;
    public Symbol AI;
    public Symbol assigned_symbol;
    
    public AI_TicTacToe() {
    	
	}
    public AI_Move make_move(TicTacToeBoard tictactoe_board) {
    	int
    	rows = tictactoe_board.getRows(),
    	cols = tictactoe_board.getColumns();
    	
    	board = new Symbol[rows][cols];
    	for(int r=0; r<rows; r++) {
    		for(int c=0; c<cols; c++) {
    			board[r][c] = tictactoe_board.getBox(r, c).getSymbol();
    		}
    	}
    	
    	assigned_symbol = tictactoe_board.getNextSymbol();
    	AI = assigned_symbol;
    	if(assigned_symbol == Symbol.X) {
    		PLAYER = Symbol.O;
    	}
    	else {
    		PLAYER = Symbol.X;
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
    	
    	if(isBoardEmpty(tictactoe_board, rows, cols) == false) {
        	while(tictactoe_board.getBox(moveRow, moveCol).getSymbol() != null) {
        		return new AI_Move(moveRow, moveCol);
        	}
    	}
    	
		return null;
    }    
    public boolean isBoardEmpty(TicTacToeBoard tictactoe_board, int rows, int cols) {
    	for(int r=0; r<rows; r++) {
    		for(int c=0; c<cols; c++) {
    			if(tictactoe_board.getBox(r, c).getSymbol() != null) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
