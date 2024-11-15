package drawables;
import java.awt.Graphics2D;
import java.awt.Point;

import interfaces.Drawable;

public abstract class Particle extends Point implements Drawable{
	private static final long serialVersionUID = -1016389573820346484L;
	public double opacity=1, scale=1;

	public Particle() {
	}
	@Override
	public void draw(Graphics2D g2d) {
		onDrawParticle(g2d);
	}
	public abstract void onDrawParticle(Graphics2D g2d);
}
