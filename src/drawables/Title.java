package drawables;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

import extras.Metrics;
import extras.Shapes;
import interfaces.DrawableClip;
import res.Resource;

public class Title implements DrawableClip{
	private int title_w, title_h, font_size, bx, by, bar_size, bar_gap=1 ,bar_arc=5, bar_n=0;
	private String title;
	private Font font;
	private Shape glyph;
	private BasicStroke stroke;
	private boolean execute_once;

	public Title() {
		title = "Tic Tac Toe";
		stroke = new BasicStroke(5);
		execute_once = true;
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		if(execute_once) {
			font_size  = Metrics.rectLength(800, 600) / 8;
			font       = new Font("Kreativ", Font.BOLD, (int)(font_size));

			g2d.setFont(font);
			title_w  = g2d.getFontMetrics().stringWidth(title);
			title_h  = g2d.getFontMetrics().getAscent();
			bar_size = font_size/5;
			glyph    = Shapes.glyph(title, g2d);
			
			execute_once = false;
		}
		else {
			g2d.setFont(font);
		}
		
		g2d.translate(x+((w/2)-(title_w/2)), y+(h/3));
		
		g2d.setColor(Resource.main_color[2]);
		g2d.fill(glyph);
		
		g2d.setClip(glyph);
		g2d.setColor(Resource.main_color[0]);
		for(bx=0; bx<title_w+bar_size; bx+=bar_size + bar_gap) {
			for(by=0; by<title_h; by+=bar_size + bar_gap) {
				g2d.fillRoundRect(bx + bar_n, by-title_h, bar_size, bar_size, bar_arc, bar_arc);
			}
		}
		g2d.setClip(-10, -(10+title_h), g2d.getClipBounds().width+20, g2d.getClipBounds().height + title_h);
		bar_n--;
		if(bar_n<-bar_size) {
			bar_n=0;
		}
		
		g2d.setColor(Resource.main_color[3]);
		g2d.setStroke(stroke);
		g2d.draw(glyph);
		
		g2d.translate(-(x+((w/2)-(title_w/2))), -(y+(h/3)));
		
		g2d.setClip(x, y ,w , h);
		
	}
}
