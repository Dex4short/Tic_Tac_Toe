package drawables;

import java.awt.Graphics2D;
import java.awt.Point;
import default_package.Game;
import enums.GridType;
import enums.Symbol;
import extras.Timing;
import interfaces.DrawableClip;

public abstract class TicTacToeBoard implements DrawableClip{
	private int rows, cols, r, c, box_size, box_gap, next;
	private TicTacToeBox box[][];
	private final Symbol symbols[] = {Symbol.X, Symbol.O};

	public TicTacToeBoard(GridType grid_type) {
		rows = grid_type.row;
		cols = grid_type.col;
		
		box = new TicTacToeBox[rows][cols];
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c] = new TicTacToeBox();
			}
		}
		
		new Thread() {
			public void run() {
				
				while(!Game.loading_screen.isCurtainsOpen()){ //waiting for the loading screen
					new Timing().sleep(1000);
				}
				
				for(int r=0; r<rows; r++) {
					for(int c=0; c<cols; c++) {
						box[r][c].setVisible(true);
					}
				}
				
			};
		}.start();
		
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		box_size = w / rows;
		box_gap  = (box_size / 50) + 1;
		
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c].drawClip(
						g2d,
						x + (r * box_size),
						y + (c * box_size),
						box_size - box_gap,
						box_size - box_gap
				);
			}
		}
	}
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return cols;
	}
	public TicTacToeBox getBox(int row, int col) {//get box for Ai
		if(row<0 || row>=rows || col<0 || col>=cols) {
			return null;
		}
		else {
			return box[row][col];
		}
	}
	public TicTacToeBox getBox(Point point) {//get box for Player
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(box[r][c].getBounds().contains(point)) {
					return box[r][c];
				}
			}
		}
		return null;
	}
	public void next() {
		next = 1 - next;
		onNext(next);
	}
	public Symbol getNextSymbol() {
		return symbols[next];
	}
	public abstract void onNext(int next);
}
