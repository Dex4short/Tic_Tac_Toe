package objects;

import java.util.Random;

import drawables.TicTacToeBoard;
import drawables.TicTacToeBox;
import enums.Symbol;
import extras.Timing;
import interfaces.Action;
import scenes.PlayScene;

public abstract class AI extends Player implements Action{

	public AI() {
		super("Computer");
	}
	@Override
	public void onAction() {
		System.out.println("Ai is thinking...");
		
		TicTacToeBoard board = getTacToeBoard();// access TicTacToe Board
		board.getRows();		//number of rows
		board.getColumns();		//number of columns
		board.getNextSymbol();	//the next symbol to be used; X for first move, O for second move.
		
		//before the game begins, a randomizer will spin first.
		//either player1 or player2 will be chosen to make the first move.
		//in PvCom mode player2 is Ai driven.
		
		//Demo
		boolean skip=false;
		if(skip) {
			TicTacToeBox box = board.getBox(0, 0);// access TicTacToe Box in a given (row, column)
			box.getSymbol(); 	// get the current symbol of box. 
								//returns:
								//		Symbol.O,
								//		Symbol.X,
								//		null. (if box is empty)

			box.setSymbol(Symbol.O); 	//set box to O
			box.setSymbol(Symbol.X); 	//set box to X
			box.setSymbol(null);		//set box to empty
		}
		
		new Thread() {
			public void run() {
				new Timing().sleep(1000);//wait for the Ai to make a move
				while(PlayScene.paused) {//the Ai will wait for the game to resume
					new Timing().sleep(1000);
					if(PlayScene.suspended) return;//if the player returns to main menu
				}
				makeMove();
			}
		}.start();
	}
	private void makeMove() {//sample move 
		Random r = new Random();
		
		TicTacToeBoard board = getTacToeBoard();
		int row = r.nextInt(board.getRows());
		int col = r.nextInt(board.getColumns());
		
		TicTacToeBox 
		next_box = board.getBox(row, col), 
		last_box = null;
		
		next_box.setHighlighted(true);
		last_box = next_box;
		while(next_box.getSymbol() != null) {
			if(!PlayScene.paused) {
				last_box.setHighlighted(false);
				
				row = r.nextInt(board.getRows());
				col = r.nextInt(board.getColumns());
				
				next_box = board.getBox(row, col);
				next_box.setHighlighted(true);

				last_box = next_box;
			}
			else if(PlayScene.suspended) return;
			new Timing().sleep(1000);
		}
		
		Symbol symbol = board.getNextSymbol();
		next_box.setSymbol(symbol);

		new Timing().sleep(1000);
		next_box.setHighlighted(false);
		if(last_box != null) last_box.setHighlighted(false);
		
		System.out.println("Ai marked " + symbol.toString() + " at row: " + row + ", col: " + col + ".");
		board.next();//an obligation of Ai to declare next() to switch turns.
	}
}
