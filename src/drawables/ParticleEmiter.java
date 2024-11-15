package drawables;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import extras.Metrics;
import interfaces.Drawable;

public abstract class ParticleEmiter extends Rectangle implements Drawable{
	private static final long serialVersionUID = -8704535958371018945L;
	private ArrayList<Particle> particles;
	private int p;
	public double scale;

	public ParticleEmiter() {
		particles = new ArrayList<Particle>();
	}
	@Override
	public void draw(Graphics2D g2d) {
		scale = Metrics.rectScale(width, height);
		
		for(p=0; p<particles.size(); p++) {			
			particles.get(p).scale = scale;
			particles.get(p).x = x + (width/2);
			particles.get(p).y = y + (height/2);
			particles.get(p).draw(g2d);
		}
	}
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	public Particle getParticle(int index) {
		return particles.get(index);
	}
}
