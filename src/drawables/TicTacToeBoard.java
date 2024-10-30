package drawables;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import default_package.Game;
import enums.GridType;
import extras.RGBA;
import extras.Timing;
import interfaces.DrawableClip;
import res.Resource;

public class TicTacToeBoard implements DrawableClip, AWTEventListener{
	private int rows, cols, r, c, box_size, box_gap;
	private Box box[][];

	public TicTacToeBoard(GridType grid_type) {
		rows = grid_type.row;
		cols = grid_type.col;
		
		box = new Box[rows][cols];
		for(r=0; r<rows; r++) {
			for(c=0; c<cols; c++) {
				box[r][c] = new Box();
			}
		}
		
		new Thread() {
			public void run() {
				
				while(!Game.loading_screen.isCurtainsOpen()){
					new Timing().sleep(1000);
					//waiting
				}
				
				for(int r=0; r<rows; r++) {
					for(int c=0; c<cols; c++) {
						box[r][c].setVisile(true);
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
		
	} 
	public int getRows() {
		return rows;
	}
	public int getCilumns() {
		return cols;
	}
	
	private class Box extends Rectangle implements DrawableClip{
		private Color color;
		private int alpha, alpha_iterator, arc;
		private boolean running;
		
		
		public Box() {
			alpha = 0;
			color = RGBA.setAlpha(Resource.main_color[0], alpha);
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			arc = w/8;
			setBounds(x, y, w, h);
			
			g2d.setColor(color);
			g2d.fillRoundRect(x, y, w, h, arc, arc);
			
		}
		public void setVisile(boolean toVisible) {
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
