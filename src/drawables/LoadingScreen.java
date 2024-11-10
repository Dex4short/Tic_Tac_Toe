package drawables;

import java.awt.Color;
import java.awt.Graphics2D;

import extras.Timing;
import interfaces.DrawableClip;
import res.Resource;
import sound.Sound;

public class LoadingScreen implements DrawableClip{
	private String text;
	private Curtain curtains[];
	private int c;
	private boolean curtains_open, curtains_moving, isLoading;
	private Runnable when_fully_closed, when_fully_opened;
	
	public LoadingScreen(String text) {
		this.text = text;
		
		initializeCurtains();
		
		curtains_open = false;
		curtains_moving = false;
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		for(c=0; c<curtains.length; c++) {
			curtains[c].drawClip(g2d, x + (c * (w/curtains.length)), y, w/curtains.length, h);
		}
		
		if(curtains_open) return;
		
		g2d.setColor(Resource.main_color[2]);
		g2d.setFont(Resource.font[0]);
		g2d.drawString(
				text,
				x + (w/2) - (g2d.getFontMetrics().stringWidth(text)/2),
				y + (h/2) + (g2d.getFontMetrics().getAscent()/2)
		);
		
	}
	public void open() {
		Sound.playOnLoadingCurtainsOpened();
		curtains[0].open();
		curtains_moving = true;
	}
	public void close() {
		Sound.playOnLoadingCurtainsClosed();
		curtains[curtains.length-1].close();
		curtains_moving = true;
	}
	public boolean isCurtainsOpen() {
		return curtains_open;
	}
	public boolean isCurtainsMoving() {
		return curtains_moving;
	}
	public void load(Runnable runnable) {
		isLoading = true;
		close();
		
		 new Thread() {
			@Override
			public void run() {
				Sound.playOnLoading();
				
				while(curtains_moving && curtains_open) {
					new Timing().sleep(1000);//waiting for the curtains to fully close
				}
				
				if(runnable != null) {
					runnable.run();//run instructions
				}

				new Timing().sleep(1000); //standby
				open();
			}
		}.start();
	}
	public void whenFullyClosed(Runnable runnable) {
		when_fully_closed = runnable;
	}
	public void whenFullyOpened(Runnable runnable) {
		when_fully_opened = runnable;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public boolean isLoading() {
		return isLoading;
	}

	private void initializeCurtains() {
		curtains = new Curtain[8];
		for(c=0; c<curtains.length; c++) {
			curtains[c] = new Curtain() {
				final int C = c;
				@Override
				public Curtain left() {
					if((C-1) < 0) {
						return null;
					}
					else {
						return curtains[C-1];
					}
				}
				@Override
				public Curtain right() {
					if((C+1) == curtains.length) {
						return null;
					}
					else {
						return curtains[C+1];
					}
				}
				@Override
				public void opened() {
					if(C == curtains.length-1) {
						curtains_open = true;
						curtains_moving = false;
						isLoading = false;
						if(when_fully_opened != null) {
							when_fully_opened.run();
							when_fully_opened = null;
						}
					}
				}
				@Override
				public void closed() {
					if(C == 0) {
						curtains_open = false;
						curtains_moving = false;
						if(when_fully_closed != null) {
							when_fully_closed.run();
							when_fully_closed = null;
						}
					}
				}
			};
		}
	}
	private abstract class Curtain implements DrawableClip{
		private int r, g, b, a, tick;
		private boolean open, move;
		
		public Curtain() {
			 r = Resource.main_color[0].getRed();
			 g = Resource.main_color[0].getGreen();
			 b = Resource.main_color[0].getBlue();
			 a = 255;
			 
			 open = false;
			 move = false;
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {			
			if(move) {
				tick--;
				
				if(tick<=0) {
					if(open) {
						if(a>0) {
							if(right()!=null && a==255) {
								right().open();
							}
							a -= 32;
						}
					}
					else {
						if(a<255) {
							if(left()!=null && a==0) {
								left().close();
							}
							a += 32;
						}
					}
					
					tick = 4;
				}
				
				if(a<0) {
					a=0;
					move = false;
					opened();
				}
				else if(a>255) {
					a=255;
					move = false;
					closed();
				}
			}


			g2d.setColor(new Color(r,g,b,a));
			g2d.fillRect(x, y, w, h);
		}
		public abstract Curtain left();
		public abstract Curtain right();
		public abstract void opened();
		public abstract void closed();
		
		public void open() {
			open = true;
			move = true;
			tick = 4;
		}
		public void close() {
			open = false;
			move = true;
			tick = 4;
		}
	}
}
