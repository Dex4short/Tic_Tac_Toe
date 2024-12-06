package drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import enums.Symbol;
import interfaces.Drawable;
import res.Resource;

public abstract class NameTag extends Rectangle implements Drawable{
	private static final long serialVersionUID = -8526841904725307929L;
	private String name;
	private Color color;
	private BasicStroke stroke;
	private Image img_symbol;
	private Symbol assigned_symbol;
	private int arc, y_translate, y_deg, y_iterate;

	public NameTag(String name) {
		stroke = new BasicStroke(10);
		arc = 20;
		
		setName(name);
	}
	private Rectangle original_clip;
	@Override
	public void draw(Graphics2D g2d) {
		original_clip = g2d.getClipBounds();
		
		g2d.setClip(x, y, width, height);
		g2d.translate(0, y_translate);
		
		g2d.setColor(color);
		g2d.fillRoundRect(
				x,
				y,
				width,
				height+arc,
				arc,
				arc
		);

		g2d.setColor(color.darker());
		g2d.setStroke(stroke);
		g2d.drawRoundRect(
				x+(arc/2),
				y+(arc/2),
				width-arc,
				height,
				arc/2,
				arc/2
		);
		
		g2d.drawImage(img_symbol, x+20, y+20, height-25, height-25, null);

		g2d.setColor(Color.white);
		g2d.setFont(Resource.font[1]);
		g2d.drawString(
				name,
				x + (width/2) - (g2d.getFontMetrics().stringWidth(name)/2),
				y + (height/2) + (g2d.getFontMetrics().getAscent()/2)
		);
		
		g2d.translate(0, -y_translate);
		g2d.setClip(original_clip);
		
		if(y_iterate != 0) {
			y_deg += y_iterate;
			
			if(y_deg>=0 && y_deg<90) {
				y_translate = (int)(Math.sin(Math.toRadians(y_deg)) * height);
			}
			else {
				if(y_deg>=90) {
					y_deg=90;
					onHidden();
				}
				else if(y_deg<=0){
					y_deg=0;
					onShown();
				}
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
	public Symbol getAssignedSymbol() {
		return assigned_symbol;
	}
	public void setAssignedSymbol(Symbol assigned_symbol) {
		this.assigned_symbol = assigned_symbol;
		img_symbol = assigned_symbol.img;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void show() {
		y_iterate = -3;
	}
	public void hide() {
		y_iterate = 3;
	}
	
	public abstract void onShown();
	public abstract void onHidden();
}
