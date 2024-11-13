package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import interfaces.Action;
import interfaces.ButtonModel;
import interfaces.Drawable;
import res.Resource;
import sound.Sound;

public abstract class Button extends Rectangle implements Drawable, AWTEventListener, ButtonModel, Action{
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
	public void draw(Graphics2D g2d) {
		
		g2d.setColor(bg_color);
		g2d.fillRoundRect(x, y, width, height, arc, arc);
		
		g2d.setColor(txt_color);
		g2d.setFont(Resource.font[1]);
		g2d.drawString(
				btn_name,
				x + (width/2) - (g2d.getFontMetrics().stringWidth(btn_name)/2),
				y + (height/2) + (g2d.getFontMetrics().getAscent()/2)
		);
		
		g2d.setColor(border_color);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(x, y, width, height, arc, arc);
	
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			switch (e.getID()) {
			case MouseEvent.MOUSE_MOVED:
				onHover(e);
				break;
			case MouseEvent.MOUSE_PRESSED:
				onPress(e);
				break;
			case MouseEvent.MOUSE_RELEASED:
				onRelease(e);
				break;
			case MouseEvent.MOUSE_CLICKED:
				onClicked(e);
				break;
			}
		}
	}
	private int sound_flag=0;
	@Override
	public void onHover(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			if(sound_flag==1) {
				Sound.playOnHoverButton();
				sound_flag = 0;
			}
			border_color = highlight;
		}
		else {
			if(sound_flag==0) {
				sound_flag = 1;
			}
			border_color =new Color(0,0,0,0);
		}
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
			Sound.playOnClickButton();
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
