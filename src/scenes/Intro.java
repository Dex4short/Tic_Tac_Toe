package scenes;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import default_package.Game;
import interfaces.Scene;
import res.Resource;

public class Intro implements Scene{
	private final int img_w=200, img_h=200;
	private Image logo;
	private int r=0,g=0,b=0,a=255,i=-2;
	private Rectangle bounds;
	private boolean end;
	

	public Intro() {
		logo = Toolkit.getDefaultToolkit().getImage(new Resource().get("DX games logo.png"));
	}
	@Override
	public void paint(Graphics2D g2d) {
		bounds = g2d.getClipBounds();
		
		g2d.setColor(Color.white);
		g2d.fill(bounds);
		
		g2d.drawImage(logo, (bounds.width/2) - (img_w/2), (bounds.height/2) - (img_h/2) ,img_w, img_h, null);
		
		g2d.setColor(new Color(r,g,b,a));
		if(!end) {
			a+=i;
			if(a<0) {
				r=255;
				g=255;
				b=255;
				a=0;
				i*=-1;
			}
			if(a>255) {
				end=true;
			}
		}
		g2d.fill(g2d.getClip());
	}
	private Scene next_scene=null;
	@Override
	public Scene next() {
		if(end) {
			return new MainMenu();
		}
		return next_scene;
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent) {
			if(event.getID() == MouseEvent.MOUSE_CLICKED) {
				end = true;
			}
		}
	}
}
