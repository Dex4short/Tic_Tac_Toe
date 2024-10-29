package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;

import drawables.MenuBackground;
import drawables.MenuSelection;
import drawables.Title;
import interfaces.DrawableClip;
import interfaces.Scene;

public class MainMenu implements Scene{
	private int w,h;
	private DrawableClip background, title, menu_selection;
	private Scene next_scene;

	public MainMenu() {
		background     = new MenuBackground();
		title 		   = new Title();
		menu_selection = new MenuSelection();
	}
	@Override
	public void paint(Graphics2D g2d) {
		w = g2d.getClipBounds().width;
		h = g2d.getClipBounds().height;
		
		background.drawClip(g2d, 0, 0, w, h);
		title.drawClip(g2d, 0, 0, w, h);
		menu_selection.drawClip(g2d, 0, 0, w, h);
	}
	@Override
	public Scene next() {
		return null;
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		((MenuSelection)menu_selection).eventDispatched(event);
	}
}
