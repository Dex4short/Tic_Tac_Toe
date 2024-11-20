package drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import extras.RGBA;
import extras.Timing;
import sound.Sound;

public class Explosion extends ParticleEmiter{
	private static final long serialVersionUID = 3525032338958843700L;
	private int particle_count=10, alpha[];
	private boolean isClear=false;
	
	public Explosion() {
		Random random = new Random();
		alpha = new int[particle_count];
		
		for(int p=0; p<particle_count; p++) {
			alpha[p] = 255;
		}
		
		new Thread() {
			public void run() {
				new Timing().sleep(random.nextInt(200));
				
				for(int p=0; p<particle_count; p++) {
					final int P=p;
					
					addParticle(new Particle() {
						private static final long serialVersionUID = 2406453175229105635L;
						private float px,py,x_iterate, y_iterate, size_iterate;
						private int angle,size=50, size_limit, o_x,o_y,o_size,alpha_iterator;
						{
							alpha_iterator = random.nextInt(10);
							angle = random.nextInt(360);
							size = 25 + random.nextInt(25);
							size_limit = random.nextInt(25);
							px = 0;
							py = 0;
							x_iterate = (0.5f+random.nextFloat()) * (float)Math.cos(Math.toRadians(angle));
							y_iterate = (0.5f+random.nextFloat()) * (float)Math.sin(Math.toRadians(angle));
							size_iterate = random.nextFloat();
						}
						@Override
						public void onDrawParticle(Graphics2D g2d) {
							px += x_iterate;
							py += y_iterate;
							setLocation(px, py);
							
							g2d.setColor(RGBA.setAlpha(Color.white, alpha[P]));
							if(alpha[P]>0) {
								alpha[P]-=alpha_iterator;
								if(alpha[P]<0) {
									alpha[P]=0;
								}
							}
							
							o_x = (int)Math.round(Explosion.this.x + (Explosion.this.width/2) + x - ((size/2f)*scale));
							o_y = (int)Math.round(Explosion.this.y + (Explosion.this.height/2) + y - ((size/2f)*scale));
							o_size = (int)Math.round(size*scale);
							g2d.fillOval(o_x, o_y, o_size, o_size);
							if(size>0) {
								size-=size_iterate;
								if(size<size_limit) {
									size=0;
								}
							}
						}
					});
				}
				
				Sound.playOnExplode();
			};
		}.start();
	}
	@Override
	public void draw(Graphics2D g2d) {
		particle_clearance();
		super.draw(g2d);
	}
	public boolean isCleared() {
		return isClear;
	}
	
	private int p;
	private void particle_clearance() {
		if(!isClear) {
			for(p=0; p<particle_count; p++) {
				if(alpha[p] > 0) {
					return;
				}
			}
		}
		isClear = true;
	}
}
