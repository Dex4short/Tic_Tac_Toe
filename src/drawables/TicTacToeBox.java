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
import interfaces.Drawable;
import res.Resource;
import sound.Sound;

public class TicTacToeBox  extends Rectangle implements Drawable{
	private static final long serialVersionUID = 5682340612501179503L;
	private Symbol symbol;
	private Color color, highlight;
	private BasicStroke stroke;
	private int alpha, alpha_iterator, arc;
	private boolean running;
	
	public TicTacToeBox() {
		symbol = null;
		alpha = 0;
		color = RGBA.setAlpha(Resource.main_color[0], alpha);
		highlight = new Color(0,0,0,0);
	}
	@Override
	public void draw(Graphics2D g2d) {
		arc = width/8;
		stroke = new BasicStroke(arc);
		setBounds(x, y, width, height);
		
		g2d.setColor(color);
		g2d.fillRoundRect(x, y, width, height, arc, arc);
		
		g2d.setColor(highlight);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(x+arc, y+arc, width-(arc*2), height-(arc*2), arc, arc);
		
		if(symbol != null) {
			g2d.drawImage(symbol.img, x+arc, y+arc, width-(arc*2), height-(arc*2), null);
		}
	}
	public void setVisible(boolean toVisible) {
		if(!running) {
			running = true;
			alpha_iterator = 4 + new Random().nextInt(32);
			
			new Timer().scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if(toVisible) {
						alpha += alpha_iterator;
						
						if(alpha > 255) {
							alpha = 255;
							running = false;
							cancel();
							Sound.playOnTicTacToeBoxAppeared();
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
	private int sound_flag=0;
	public void setHighlighted(boolean highlighted) {
		if(highlighted) {
			if(sound_flag == 1) {
				Sound.playOnTicTacToeBoxHovered();
				sound_flag=0;
			}
			highlight = color.darker();
		}
		else {
			if(sound_flag == 0) {
				sound_flag=1;
			}
			highlight = new Color(0,0,0,0);
		}
	}
	public void setSymbol(Symbol symbol) {
		Sound.playOnSymbolPlaced();
		this.symbol = symbol;
	}
	public Symbol getSymbol() {
		return symbol;
	}
}
