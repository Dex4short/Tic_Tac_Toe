package drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import extras.RGBA;
import interfaces.Drawable;
import res.Resource;

public class MenuBackground extends Rectangle implements Drawable{
	private static final long serialVersionUID = -3739618271336656198L;
	private Random r;
	private Image particle_img[];
	private int particle_count=10;
	private Particle particle[];
	
	public MenuBackground() {
		r = new Random();
		
		particle_img = new Image[] {
			RGBA.alterImage(Resource.createBufferedImage("O.png"), Resource.main_color[1]),
			RGBA.alterImage(Resource.createBufferedImage("X.png"), Resource.main_color[1])
		};

	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect(x, y, width, height);
		
		if(particle == null) {
			particle = new Particle[particle_count];
			
			for(int p=0; p<particle_count; p++) {
				particle[p] = new Particle();
				particle[p].img = particle_img[p%2];
				particle[p].x = r.nextInt(width);
				particle[p].y = r.nextInt(height);
				particle[p].size = 50 + r.nextInt(100);
				particle[p].deg = r.nextInt(360);
				particle[p].rot = 0;
			}
		}
		
		for(Particle p: particle) {
			p.x = p.x += Math.cos(Math.toRadians(p.deg));
			p.y = p.y += Math.sin(Math.toRadians(p.deg));
			
			g2d.translate(x + p.x, y + p.y);
			g2d.rotate(Math.toRadians(p.deg));
			
			g2d.drawImage(
					p.img,
					-(p.size/2),
					-(p.size/2),
					p.size,
					p.size,
					null
			);

			g2d.rotate(Math.toRadians(-p.deg));
			g2d.translate(x + -p.x, y + -p.y);
			
			if(p.x<-p.size) {//west
				p.x = -p.size;
				p.deg = 315 + r.nextInt(90);
			}
			else if(p.x>width+p.size) {//east
				p.x = width+p.size;
				p.deg = 135 + r.nextInt(90);
			}
			else if(p.y<-p.size) {//north
				p.y = -p.size;
				p.deg = 45 + r.nextInt(90);
			}
			else if(p.y>height+p.size) {//south
				p.y = height+p.size;
				p.deg = 225 + r.nextInt(90);
			}
			
			p.rot++;
			if(p.rot ==360) {
				p.rot=0;
			}
		}
	}
	private class Particle{
		Image img;
		float x,y;
		int size;
		double deg,rot;
	}
}
