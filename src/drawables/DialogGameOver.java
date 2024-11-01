package drawables;

import java.awt.Graphics2D;

import interfaces.DrawableClip;

public abstract class DialogGameOver extends Dialog{

	public DialogGameOver() {
		super("Game Over", "Main Menu", "New Game");
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		super.drawClip(g2d, x, y, w, h);
	}
}
