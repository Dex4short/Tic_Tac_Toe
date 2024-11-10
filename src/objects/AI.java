package objects;

import ai.AI_Move;
import ai.AI_TicTacToe;
import drawables.TicTacToeBoard;
import enums.Difficulty;
import extras.Timing;
import interfaces.Action;
import scenes.PlayScene;

public abstract class AI extends Player implements Action {
	private static final long serialVersionUID = 6550487556425386480L;
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
    		public void run() {
				TicTacToeBoard tictactoe_board = getTicTacToeBoard();
				
				delay(300);
				AI_Move ai_move = ai_tictactoe.make_move(tictactoe_board);
				if(ai_move != null) {
					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setHighlighted(true);

					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setSymbol(ai_tictactoe.assigned_symbol);

					delay(300);
					tictactoe_board.getBox(ai_move.row, ai_move.col).setHighlighted(false);
		            System.out.println("AI plays at position: " + ai_move.row + ", " + ai_move.col);
				}
				else {
					System.out.println("No more move left");
				}
				
				tictactoe_board.next();
    		}
    	}.start();
    }
    private void delay(int milisec) {
		new Timing().sleep(milisec);//wait for the Ai to make a move
		while(PlayScene.paused) {//the Ai will wait for the game to resume
			new Timing().sleep(1000);
			if(PlayScene.suspended) return;//if the player returns to main menu
		}
    }
}