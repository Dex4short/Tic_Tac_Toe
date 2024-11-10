package interfaces;

import java.awt.Graphics2D;
import java.awt.event.AWTEventListener;

public interface Scene extends AWTEventListener{
	public void paint(Graphics2D g2d);
	public Scene next();
}
