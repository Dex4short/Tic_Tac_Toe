package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import interfaces.DrawableClip;
import res.Resource;

public class MenuSelection implements DrawableClip, AWTEventListener{
	private int selection_x, selection_y,selection_w, selection_h, selection_arc=20, s;
	private Selection selections[];
	private BasicStroke stroke;
	private Font font;
	
	public MenuSelection() {
		stroke = new BasicStroke(5);
		selections = new Selection[]{
			new Selection("Play"),
			new Selection("Mode : 3x3"),
			new Selection("About")
		};
		font = new Font("Calibri", Font.BOLD, 20);
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		selection_w = w/3;
		selection_h = h/4;
		selection_x = x + (w/2) - (selection_w/2);
		selection_y = y + (int)(h*0.66) - (selection_h/2);
		
		g2d.setColor(Resource.main_color[2]);
		g2d.fillRoundRect(selection_x, selection_y, selection_w, selection_h, selection_arc, selection_arc);
		
		g2d.setColor(Resource.main_color[3]);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(selection_x, selection_y, selection_w, selection_h, selection_arc, selection_arc);
		
		drawSelections(g2d, selection_x, selection_y, selection_w, selection_h);
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		for(Selection selection: selections) {
			selection.eventDispatched(event);
		}
	}
	
	private void drawSelections(Graphics2D g2d, int x, int y, int w, int h) {
		for(s=0; s<selections.length; s++) {
			selections[s].drawClip(
					g2d, 
					x + 5, 
					y + 5 + (s*((h - 10) / selections.length)),
					w - 10, 
					(h/selections.length) - 5
			);
		}
	}
	
	private class Selection extends Rectangle implements DrawableClip,AWTEventListener{
		private static final long serialVersionUID = -7267045785208769956L;
		private int arc=20;
		private String selection_value;
		private boolean hover,pressed;

		public Selection(String selection_value) {
			this.selection_value = selection_value;
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			setBounds(x, y, w, h);

			if(pressed) {
				g2d.setColor(Resource.main_color[0].darker());
			}
			else if(hover) {
				g2d.setColor(Resource.main_color[0].brighter());
			}
			else {
				g2d.setColor(Resource.main_color[0]);
			}
			g2d.fillRoundRect(x, y, w, h, arc, arc);
			
			g2d.setFont(font);
			g2d.setColor(Resource.main_color[2]);
			g2d.drawString(
					selection_value,
					x + (w/2) - (g2d.getFontMetrics().stringWidth(selection_value)/2),
					y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
			);
		}
		@Override
		public void eventDispatched(AWTEvent event) {
			if(event instanceof MouseEvent) {
				MouseEvent e = (MouseEvent)event;
				
				switch(e.getID()) {
				case MouseEvent.MOUSE_PRESSED:
					if(this.contains(e.getPoint())) {
						pressed = true;
					}
					break;
					
				case MouseEvent.MOUSE_RELEASED:
					if(this.contains(e.getPoint())) {
						pressed = false;
					}
					break;
					
				case MouseEvent.MOUSE_CLICKED:
					if(this.contains(e.getPoint()))	{
						
					}
					break;
					
				case MouseEvent.MOUSE_MOVED:
					if(this.contains(e.getPoint())) {
						hover = true;
					}
					else {
						hover = false;
					}
					break;
				}
			}
		}
		public void action() {
			
		}
	}
}
