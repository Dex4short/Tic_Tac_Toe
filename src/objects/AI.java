package objects;

import ai.AI_Move;
import ai.AI_TicTacToe;
import drawables.TicTacToeBoard;
import enums.Difficulty;
import enums.Symbol;
import extras.Timing;
import interfaces.Accessible;
import interfaces.Action;
import scenes.PlayScene;

public abstract class AI extends Player implements Action {
	private AI_TicTacToe ai_tictactoe;
	
    public AI(Difficulty difficulty) {
        super("Computer");
        this.ai_tictactoe = difficulty.ai_tictactoe;
    }
    @Override
    public void myTurn(boolean myTurn) {
    	super.myTurn(myTurn);
    	
    	if(myTurn) {
    		action();//triger's Ai to make an action
    	}
    }
    @Override
    public void onAction() {
    	new Thread() {
			private AI_Move ai_move = null;
			
    		public void run() {
				TicTacToeBoard tictactoe_board = getTicTacToeBoard();
				Symbol 
				assigned_symbol = tictactoe_board.getNextSymbol();
				
				delay(300);
				new Thread() {
					public void run() {
						ai_move = ai_tictactoe.make_move(tictactoe_board);
					};
				}.start();

				Accessible<AI_Move> accessible_aiMove = new Accessible<AI_Move>() {
					@Override
					public AI_Move onAccess() {
						return ai_move;
					}
				};
				
				if(proceed_move(tictactoe_board, assigned_symbol, accessible_aiMove)) {
					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setHighlighted(true);

					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setSymbol(ai_tictactoe.assigned_symbol);

					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setHighlighted(false);
		            System.out.println("AI plays at position: " + ai_move.row + ", " + ai_move.col);
		            
					tictactoe_board.next();
				}
				else {
					System.out.println("No more move left");
				}
    		}
    	}.start();
    }
    private void delay(int milisec) {
		new Timing().sleep(milisec);//wait for the Ai to make a move
		while(PlayScene.paused) {//the Ai will wait for the game to resume
			new Timing().sleep(milisec);
			if(PlayScene.suspended) return;//if the player returns to main menu
		}
    }
    private boolean proceed_move(TicTacToeBoard tictactoe_board, Symbol assigned_symbol, Accessible<AI_Move> accessible_aiMove) {
    	while(accessible_aiMove.access() == null) {
    		delay(300);
    		if(PlayScene.suspended) {
    			return false;
    		}
        	if(tictactoe_board.getNextSymbol() != assigned_symbol) {
            	System.out.println("delayed...");
        		return false;
        	}
    	}

    	System.out.println("on time...");
        return true;
    }
}