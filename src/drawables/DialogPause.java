package drawables;

import java.awt.AWTEvent;
import java.awt.Graphics2D;

import extras.RGBA;
import res.Resource;
import scenes.PlayScene;

public abstract class DialogPause extends Dialog{
	protected int alpha=0, alpha_iterate=0;

	public DialogPause() {
		super("Game Paused", "Main Menu", "Resume Game");
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {		
		draw_pause_background(g2d);
		
		if(alpha_iterate==0 && alpha>0) {
			super.drawClip(g2d, x, y, w, h);
		}
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(alpha_iterate==0 && alpha>0) {
			super.eventDispatched(event);
		}
	}
	public void show(boolean show) {
		if(show) {
			alpha_iterate=4;
		}
		else {
			alpha_iterate=-4;
		}
	}
	public abstract void onShown(boolean visible);
	
	private void draw_pause_background(Graphics2D g2d) {
		g2d.setColor(RGBA.setAlpha(Resource.main_color[1], alpha));
		g2d.fill(g2d.getClipBounds());

		alpha += alpha_iterate;
		if(alpha >= 128) {
			alpha = 128;
			alpha_iterate = 0;
			onShown(true);
		}
		else if(alpha <= 0) {
			alpha = 0;
			alpha_iterate = 0;
			onShown(false);
		}
	}
}
