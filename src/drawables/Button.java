package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import extras.Listeners;
import interfaces.Action;
import interfaces.ButtonModel;
import interfaces.DrawableClip;
import res.Resource;

public abstract class Button extends Rectangle implements DrawableClip, AWTEventListener, ButtonModel, Action{
	private static final long serialVersionUID = 2803333208958227507L;
	private String btn_name;
	private Color bg_color, border_color, color, highlight, txt_color;
	private BasicStroke stroke;
	private int arc;

	public Button(String btn_name) {
		setButtonName(btn_name);
		setColor(Resource.main_color[0]);
		setHighlight(Resource.main_color[0]);
		setTextColor(Resource.main_color[2]);
		
		stroke = new BasicStroke(3);
		arc = 10;
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		setBounds(x, y, w, h);		
		
		g2d.setColor(bg_color);
		g2d.fillRoundRect(x, y, w, h, arc, arc);
		
		g2d.setColor(txt_color);
		g2d.setFont(Resource.font[1]);
		g2d.drawString(
				btn_name,
				x + (w/2) - (g2d.getFontMetrics().stringWidth(btn_name)/2),
				y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
		);
		
		g2d.setColor(border_color);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(x, y, w, h, arc, arc);
	
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		Listeners.listen(event, this);
	}
	@Override
	public void onHighlight(MouseEvent e) {
		border_color = (getBounds().contains(e.getPoint())) ? highlight : new Color(0,0,0,0);
	}
	@Override
	public void onPress(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			bg_color = color.darker();
		}
	}
	@Override
	public void onRelease(MouseEvent e) {
		bg_color = color;
	}
	@Override
	public void onClicked(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			border_color = new Color(0,0,0,0);
			onAction();
		}
	}
	public String getButtonName() {
		return btn_name;
	}
	public void setButtonName(String btn_name) {
		this.btn_name = btn_name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		bg_color = color;
		this.color = color;
	}
	public Color getHighlight() {
		return highlight;
	}
	public void setHighlight(Color highlight) {
		border_color = new Color(0,0,0,0);
		this.highlight = highlight;
	}
	public Color getTextColor() {
		return txt_color;
	}
	public void setTextColor(Color txt_color) {
		this.txt_color = txt_color;
	}
}
