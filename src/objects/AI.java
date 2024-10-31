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
				while(PlayScene.paused) {
					new Timing().sleep(1000);//wait for the ai to make a move
				}
				makeMove();
				
				board.next();//an obligation of Ai to declare next() to switch turns.
			}
		}.start();
	}
	private void makeMove() {//sample move 
		Random r = new Random();
		
		TicTacToeBoard board = getTacToeBoard();
		int row = r.nextInt(board.getRows());
		int col = r.nextInt(board.getColumns());
		
		while(board.getBox(row, col).getSymbol() != null) {
			row = r.nextInt(board.getRows());
			col = r.nextInt(board.getColumns());
		}
		
		Symbol symbol = board.getNextSymbol();
		board.getBox(row, col).setSymbol(symbol);
		System.out.println("Ai marked " + symbol.toString() + " at row: " + row + ", col: " + col + ".");
	}
}
