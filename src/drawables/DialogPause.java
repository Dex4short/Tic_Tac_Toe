package drawables;

import java.awt.AWTEvent;
import java.awt.Graphics2D;

import extras.RGBA;
import res.Resource;

public abstract class DialogPause extends Dialog{
	private static final long serialVersionUID = 4972697442630921194L;
	protected int alpha=0, alpha_iterate=0;

	public DialogPause() {
		super("Game Paused", "Main Menu", "Resume Game");
	}
	@Override
	public void draw(Graphics2D g2d) {		
		draw_pause_background(g2d);
		
		if(alpha_iterate==0 && alpha>0) {
			super.draw(g2d);
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
		if(alpha > 128) {
			alpha = 128;
			alpha_iterate = 0;
			onShown(true);
		}
		else if(alpha < 0) {
			alpha = 0;
			alpha_iterate = 0;
			onShown(false);
		}
	}
}
