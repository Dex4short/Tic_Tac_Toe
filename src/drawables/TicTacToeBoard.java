package drawables;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import enums.Direction;
import enums.GridType;
import enums.Orientation;
import enums.Symbol;
import extras.Metrics;
import interfaces.Drawable;
import sound.Sound;

public abstract class TicTacToeBoard extends Rectangle implements Drawable{
	private static final long serialVersionUID = 8176833577053081124L;
	private int rows, cols, box_size, box_gap, next;
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
	public void draw(Graphics2D g2d) {
		box_size = width / rows;
		box_gap  = (box_size / 50) + 1;
		
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c].setBounds(x + (c * box_size), y + (r * box_size), box_size - box_gap, box_size - box_gap);
				box[r][c].draw(g2d);
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
		doDashLine(getNextSymbol());
		next = 1 - next;
		onNext(next);
	}
	public Symbol getNextSymbol() {
		return Symbol.values()[next];
	}
    public boolean isBoardEmpty() {
    	for(int r=0; r<rows; r++) {
    		for(int c=0; c<cols; c++) {
    			if(getBox(r, c).getSymbol() != null) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    public boolean isBoardHasVacant() {
    	for(int r=0; r<rows; r++) {
    		for(int c=0; c<cols; c++) {
    			if(getBox(r, c).getSymbol() == null) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
	public boolean isBoardCompleted() {
		int boxes_filled=0;
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				if(getBox(r, c).getSymbol() != null) {
					boxes_filled++;
				}
			}
		}
		
		boolean completed = (boxes_filled == (rows*cols));
		
		if(completed) {
			for(r=0; r<rows; r++) {
				for(c=0; c<cols; c++) {
					box[r][c].setVisible(false);
				}
			}
		}
		
		return completed;
	}
	public static int board_x, board_y, board_size;
	public static void preferBoardSize(int screen_width, int screen_height) {
		board_x = (screen_width/2) - (board_size/2);
		board_y = (screen_height/2) - (board_size/2);
		board_size = (int) (Metrics.rectLength(screen_width, screen_height)/3);
	}
	public static Rectangle preferedBoardRect() {
		return new Rectangle(board_x, board_y, board_size, board_size);
	}
	public void doDashLine(Symbol symbol) {
		checking = true;
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {			
				forEachDirectionOf(r, c, symbol);
			}
		}
		checking = false;
	}
	public void resetDashLines() {
		lines.clear();
	}
	
	public abstract void onNext(int next);
	public abstract void onCheck();
	

	private void forEachDirectionOf(int r, int c, Symbol symbol) {
		for(Direction direction: Direction.values()) {
			if(check_line(0, r, c, symbol, direction)) {
				addLine(new Line(this , r, c, direction));
			}
		}
	}
	@SuppressWarnings("unused")
	@Deprecated
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
		Sound.playOnLineDashed();
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
