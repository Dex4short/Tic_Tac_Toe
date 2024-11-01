package drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import enums.Symbol;
import extras.RGBA;
import interfaces.DrawableClip;
import res.Resource;

public class TicTacToeBox  extends Rectangle implements DrawableClip{
	private static final long serialVersionUID = 5682340612501179503L;
	private Symbol symbol;
	private Color color, highlight;
	private BasicStroke stroke;
	private int alpha, alpha_iterator, arc;
	private boolean running, mark;
	
	public TicTacToeBox() {
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
	public void setHighlighted(boolean highlighted) {
		if(highlighted) {
			highlight = color.darker();
		}
		else {
			highlight = color;
		}
	}
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	public Symbol getSymbol() {
		return symbol;
	}
	public boolean isMarked() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
}
