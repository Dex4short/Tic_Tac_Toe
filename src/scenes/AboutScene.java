package scenes;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import interfaces.Drawable;
import interfaces.Scene;
import res.Resource;

public class AboutScene extends Rectangle implements Scene{
	private static final long serialVersionUID = 5122361222326349809L;
	private Color credits_bg;
	private BasicStroke stroke, default_stroke;
	private Paragraph paragraph[];
	private int p, paragraph_h;
	private boolean show=false;

	public AboutScene() {
		credits_bg = new Color(255, 255, 255, 200);
		stroke = new BasicStroke(5);
		default_stroke = new BasicStroke(1);
		paragraph = new Paragraph[] {
				new Paragraph("TicTacToe", Resource.font[2], 20),
				new Paragraph("Developers:", Resource.font[0], 20),
				new Paragraph("Dexter Pacaña (Graphics)", Resource.font[0], 5),
				new Paragraph("Lyndon Obenza (Ai)", Resource.font[0], 5),
				new Paragraph("Renie Boy Maglinte (Assets)", Resource.font[0], 30),
				new Paragraph("CSC108 Project", Resource.font[0], 30),
				new Paragraph("click to continue", Resource.font[4], -20)
		};
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(credits_bg);
		g2d.fillRect(x + (width/4), y + height/4, width/2, height/2);

		g2d.setColor(Resource.main_color[0]);
		g2d.setStroke(stroke);
		g2d.drawRect(x + (width/4), y + height/4, width/2, height/2);
		g2d.setStroke(default_stroke);
		
		g2d.setColor(Color.black);
		paragraph_h = 0;
		for(p=0; p<paragraph.length; p++) {
			paragraph[p].setLocation(width/2, paragraph_h);
			paragraph_h += paragraph[p].getSpacing(g2d);
		}
;
		g2d.translate(0, (height/2) - (paragraph_h/2));
		for(p=0; p<paragraph.length; p++) {
			paragraph[p].draw(g2d);
		}
		g2d.translate(0, -((height/2) - (paragraph_h/2)));
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent) event;
			
			if(e.getID() == MouseEvent.MOUSE_CLICKED) {
				show(false);
			}
		}
	}
	@Override
	public Scene next() {
		return null;
	}
	public void show(boolean show) {
		this.show = show;
	}
	public boolean isShowing() {
		return show;
	}
	
	private class Paragraph extends Point implements Drawable{
		private static final long serialVersionUID = -6607640022002482668L;
		private String sentence;
		private Font font;
		private int spacing;
		
		public Paragraph(String sentence, Font font, int spacing) {
			this.sentence = sentence;
			this.font = font;
			this.spacing = spacing;
		}
		@Override
		public void draw(Graphics2D g2d) {
			g2d.setFont(font);
			g2d.drawString(sentence, x - (g2d.getFontMetrics().stringWidth(sentence)/2), y);
		}
		public int getSpacing(Graphics2D g2d) {
			return g2d.getFontMetrics().getAscent() + spacing;
		}
	}
}
