package drawables;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import enums.GridType;
import enums.Symbol;
import interfaces.Direction;
import interfaces.DrawableClip;
import interfaces.Orientation;

public abstract class TicTacToeBoard implements DrawableClip{
	private int rows, cols, box_size, box_gap, next, boxes_filled;
	private TicTacToeBox box[][];
	private ArrayList<Line> lines;
	private boolean checking;

	public TicTacToeBoard(GridType grid_type) {
		rows = grid_type.row;
		cols = grid_type.col;
		
		box = new TicTacToeBox[rows][cols];
		
		int r,c;
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c] = new TicTacToeBox();
			}
		}
		
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c].setVisible(true);
			}
		}
		
		lines = new ArrayList<Line>();
	}
	private int r, c, l;
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		box_size = w / rows;
		box_gap  = (box_size / 50) + 1;
		
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c].drawClip(g2d, x + (r * box_size), y + (c * box_size), box_size - box_gap, box_size - box_gap);
			}
		}
		
		if(!checking) {
			for(l=0; l<lines.size(); l++) {
				lines.get(l).draw(g2d);
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
		check_grid(getNextSymbol());
		
		next = 1 - next;
		onNext(next);
	}
	public Symbol getNextSymbol() {
		return Symbol.values()[next];
	}
	public boolean isBoardCompleted() {
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(getBox(r, c).getSymbol() != null) {
					boxes_filled++;
				}
			}
		}
		return boxes_filled == (rows*cols);
	}
	
	public abstract void onNext(int next);
	public abstract void onCheck();
	
	private void check_grid(Symbol symbol) {
		checking = true;
		
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {				
				forEachDirectionOf(r, c, symbol);
				forEachOrientationOf(r, c, symbol);
			}
		}

		checking = false;
	}
	private void forEachDirectionOf(int r, int c, Symbol symbol) {
		for(Direction direction: Direction.values()) {
			if(check_line(0, r, c, symbol, direction)) {
				addLine(new Line(this , r, c, direction));
			}
		}
	}
	private void forEachOrientationOf(int r, int c, Symbol symbol) {
		for(Orientation orientation: Orientation.values()) {
			if(check_line(0, r, c, symbol, orientation)) {
				addLine(new Line(this , r, c, orientation));
			}
		}
	}
	private void addLine(Line line) {
		for(Line l: lines) {
			if(l.compare(line)) {
				return;
			}
		}
		lines.add(line);
		onCheck();
	}
	private boolean check_line(int n, int r, int c, Symbol symbol, Direction direction) {
		if(n == 3) return true;
		
		if(getBox(r, c) == null) return false;
		
		if(getBox(r, c).getSymbol() == symbol) {
			return check_line(n+1, r+direction.x_iterate, c+direction.y_iterate, symbol, direction);
		}
		else {
			return false;
		}
	}
	private boolean check_line(int n, int r, int c, Symbol symbol, Orientation orientation) {
		if(n==2) return true;
		
		if(getBox(r, c) == null) return false;
		
		if(getBox(r, c).getSymbol() == symbol) {
			return 
				check_line(n+1, r + orientation.x1, c + orientation.y1, symbol, orientation) 
				&&
				check_line(n+1, r + orientation.x2, c + orientation.y2, symbol, orientation)
			;
		}
		else {
			return false;
		}
	}
}
