package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import default_package.Game;
import enums.GridType;
import enums.Symbol;
import extras.RGBA;
import extras.Timing;
import interfaces.DrawableClip;
import res.Resource;

public abstract class TicTacToeBoard implements DrawableClip, AWTEventListener{
	private int rows, cols, r, c, box_size, box_gap, next_symbol;
	private Box box[][];
	private Symbol symbols[];

	public TicTacToeBoard(GridType grid_type) {
		rows = grid_type.row;
		cols = grid_type.col;
		
		box = new Box[rows][cols];
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c] = new Box(r, c);
			}
		}
		
		symbols = new Symbol[] {Symbol.X, Symbol.O };
		
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
	@Override
	public void eventDispatched(AWTEvent event) {
		for(int r=0; r<rows; r++) {
			for(int c=0; c<cols; c++) {
				box[r][c].eventDispatched(event);
			}
		}
	} 
	public int getRows() {
		return rows;
	}
	public int getCilumns() {
		return cols;
	}
	public void nextSymbol() {
		next_symbol = 1 - next_symbol;
	}
	public abstract void onCommitMove(int row, int col, Symbol symbol);
	
	private class Box extends Rectangle implements DrawableClip, AWTEventListener{
		private static final long serialVersionUID = 5682340612501179503L;
		private Symbol symbol;
		private Color color, highlight;
		private BasicStroke stroke;
		private int row, col, alpha, alpha_iterator, arc;
		private boolean running;
		
		public Box(int row, int col) {
			this.row = row;
			this.col = col;
			
			symbol = null;
			alpha = 0;
			color = RGBA.setAlpha(Resource.main_color[0], alpha);
			highlight = new Color(0,0,0,0);
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			arc = w/8;
			stroke = new BasicStroke(arc);
			setBounds(x, y, w, h);
			
			g2d.setColor(color);
			g2d.fillRoundRect(x, y, w, h, arc, arc);
			
			g2d.setColor(highlight);
			g2d.setStroke(stroke);
			g2d.drawRoundRect(x+arc, y+arc, w-(arc*2), h-(arc*2), arc, arc);
			
			if(symbol != null) {
				g2d.drawImage(symbol.img, x+arc, y+arc, w-(arc*2), h-(arc*2), null);
			}
		}
		@Override
		public void eventDispatched(AWTEvent event) {
			if(event instanceof MouseEvent) {
				MouseEvent e = (MouseEvent)event;
				
				switch(e.getID()) {
				case MouseEvent.MOUSE_MOVED:
					if(getBounds().contains(e.getPoint())) {
						highlight = color.darker();
					}
					else {
						highlight = color;
					}
					break;
				case MouseEvent.MOUSE_CLICKED:
					if(getBounds().contains(e.getPoint())) {
						symbol = symbols[next_symbol];
						onCommitMove(row, col, symbol);
					}
				}
			}
		}
		public void setVisible(boolean toVisible) {
			if(!running) {
				running = true;
				alpha_iterator = 4 + new Random().nextInt(16);
				
				new Timer().scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						
						if(toVisible) {
							alpha += alpha_iterator;
							
							if(alpha > 255) {
								alpha = 255;
								running = false;
								cancel();
							}
						}
						else {
							alpha -= alpha_iterator;
							
							if(alpha < 0) {
								alpha = 0;
								running = false;
								cancel();
							}
						}

						color = RGBA.setAlpha(Resource.main_color[0], alpha);
					}
				}, 0, 15);
				
			}
		}
	}

}
