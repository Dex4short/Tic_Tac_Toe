package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import interfaces.ButtonModel;
import interfaces.Drawable;
import res.Resource;
import sound.Sound;

public abstract class ButtonPause extends Rectangle implements Drawable, AWTEventListener, ButtonModel{
	private static final long serialVersionUID = 2803333208958227507L;
	private Color color, highlight;
	private BasicStroke stroke;
	private int arc, portion_w, portion_h;
	
	public ButtonPause() {
		color = Resource.main_color[2];
		highlight = new Color(0,0,0,0);
		stroke = new BasicStroke(3);
		arc = 5;
	}
	@Override
	public void draw(Graphics2D g2d) {
		setBounds(x, y, width, height);		
		
		g2d.setColor(color);
		g2d.fillRoundRect(x, y, width, height, arc, arc);
		
		g2d.setColor(Color.gray);
		portion_w = width/5;
		portion_h = height/5;
		g2d.fillRoundRect(x+portion_w, y+portion_h, portion_w, height-(portion_h*2), arc/2, arc/2);
		g2d.fillRoundRect(x+(portion_w * 3), y+portion_h, portion_w, height-(portion_h*2), arc/2, arc/2);
		
		g2d.setColor(highlight);
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
			highlight = color.darker();
		}
		else {
			if(sound_flag==0) {
				sound_flag = 1;
			}
			highlight = new Color(0,0,0,0);
		}
	}
	@Override
	public void onPress(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			color = color.darker();
		}
	}
	@Override
	public void onRelease(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			color = Resource.main_color[2];
		}
	}
	@Override
	public void onClicked(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			Sound.playOnClickButton();
			highlight = new Color(0,0,0,0);
			onPause();
		}
	}

	public abstract void onPause();
	
}
