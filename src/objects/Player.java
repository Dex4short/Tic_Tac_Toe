package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import drawables.Score;
import drawables.TicTacToeBoard;
import interfaces.DrawableClip;
import res.Resource;

public abstract class Player implements DrawableClip{
	private Score score;
	private Color color;
	private BasicStroke stroke;
	private String name;
	private int arc, y_translate, y_deg, y_iterate;
	private boolean myTurn;

	public Player(String name) {
		setName(name);
		
		score = new Score();
		stroke = new BasicStroke(10);
		arc = 20;
	}
	private Rectangle original_clip;
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		original_clip = g2d.getClipBounds();
		
		g2d.setClip(x, y, w, h);
		g2d.translate(0, y_translate);
		
		g2d.setColor(color);
		g2d.fillRoundRect(
				x,
				y,
				w,
				h+arc,
				arc,
				arc
		);

		g2d.setColor(color.darker());
		g2d.setStroke(stroke);
		g2d.drawRoundRect(
				x+(arc/2),
				y+(arc/2),
				w-arc,
				h,
				arc/2,
				arc/2
		);

		g2d.setColor(Color.white);
		g2d.setFont(Resource.font[1]);
		g2d.drawString(
				name,
				x + (w/2) - (g2d.getFontMetrics().stringWidth(name)/2),
				y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
		);
		
		g2d.translate(0, -y_translate);
		g2d.setClip(original_clip);
		
		if(y_iterate != 0) {
			y_deg += y_iterate;
			
			if(y_deg>=0 && y_deg<90) {
				y_translate = (int)(Math.sin(Math.toRadians(y_deg)) * h);
			}
			else {
				y_iterate = 0;
			}
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isMyTurn() {
		return myTurn;
	}
	public void myTurn(boolean myTurn) {
		this.myTurn = myTurn;
		
		if(isMyTurn()) {
			System.out.println(name + "'s turn");
			show();
			onMyTurn();
		}
		else {
			hide();
		}
	}
	public Score getScore() {
		return score;
	}
	
	public abstract TicTacToeBoard getTicTacToeBoard();
	public abstract void onMyTurn();
	
	public void show() {
		y_iterate = -3;
	}
	public void hide() {
		y_iterate = 3;
	}
}
