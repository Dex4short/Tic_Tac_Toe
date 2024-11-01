package drawables;

import java.awt.Graphics2D;

import interfaces.DrawableClip;
import res.Resource;

public class Score implements DrawableClip{
	private int amount;
	
	public Score() {
		amount = 0;
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		g2d.setColor(Resource.main_color[2]);
		g2d.setFont(Resource.font[3]);
		g2d.drawString(
				amount + "",
				x + (w/2) - (g2d.getFontMetrics().stringWidth(amount + "")/2),
				y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
		);
	}
	public void addAmount(int additional_amount) {
		amount += additional_amount;
	}
	public int getAmount() {
		return amount;
	}
}
