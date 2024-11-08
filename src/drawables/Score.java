package drawables;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import interfaces.Drawable;
import res.Resource;

public class Score extends Rectangle implements Drawable{
	private static final long serialVersionUID = -6169739124668866215L;
	private int amount;
	
	public Score() {
		amount = 0;
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Resource.main_color[2]);
		g2d.setFont(Resource.font[3]);
		g2d.drawString(
				amount + "",
				x + (width/2) - (g2d.getFontMetrics().stringWidth(amount + "")/2),
				y + (height/2) + (g2d.getFontMetrics().getAscent()/2)
		);
	}
	public void addAmount(int additional_amount) {
		amount += additional_amount;
	}
	public int getAmount() {
		return amount;
	}
}
