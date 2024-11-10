package drawables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import enums.GameMode;
import interfaces.DrawableClip;
import res.Resource;
import sound.Sound;

public abstract class Roullet implements DrawableClip{
	private Color color[];
	private int pie_n;
	private float deg, deg_iterate, deg_increment;
	private String p_name[];
	private Pie pie[], selected_pie;
	private Arrow arrow;
	private boolean roullet_stopped=false, show_roulet=false;

	public Roullet(GameMode game_mode) {
		p_name = new String[] {game_mode.player1, game_mode.player2};
		
		color = new Color[] {
				Color.red, 
				Color.orange, 
				Color.yellow, 
				Color.green,
				Color.cyan, 
				Color.blue,
				Color.magenta
		};

		int 
		color_n=0,
		pie_count = color.length * 2,
		player_n = 0;
		
		float 
		pie_angle = 0, 
		pie_arc = 360f/pie_count;
		
		pie = new Pie[pie_count];
		pie_n = 0;
		for(pie_angle=0; pie_angle<360; pie_angle+=pie_arc) {
			pie[pie_n] = new Pie(p_name[player_n], color[color_n], pie_angle, pie_arc);
			
			pie_n++;
			player_n = 1 - player_n;
			color_n = (color_n==color.length-1) ? 0 : color_n+1;
		}
		
		arrow = new Arrow(pie) {
			@Override
			public void onPoint(Pie pie) {
				selected_pie = pie;
				stop_roullet();
			}
		};
		
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		if(!show_roulet) return; 
		
		g2d.translate(x, y);
		
		g2d.rotate(Math.toRadians(deg));
		for(pie_n=0; pie_n<pie.length; pie_n++) {
			pie[pie_n].drawClip(g2d, 0, 0, w, h);
		}
		g2d.rotate(Math.toRadians(-deg));
		
		arrow.drawClip(g2d, -w/16, -h/16, w/8, h/8);		
		g2d.translate(-x, -y);
		
		if(deg_increment != 0) {
			deg+=deg_iterate;
			if(deg>=360) {
				deg=0;
			}
			
			deg_iterate += deg_increment;
			if(deg_iterate>9) {
				deg_increment = -0.05f;
			}
			else if(deg_iterate<0) {
				deg_iterate = 0;
				deg_increment = 0;
				
				arrow.point();
			}
		}
		
	}
	public void roll() {
		deg = new Random().nextInt(360);
		deg_iterate = 0;
		deg_increment = 0.05f;
		Sound.playOnRollRoullet();
	}
	public boolean isStoped() {
		return roullet_stopped;
	}
	public String getSelectedName() {
		return selected_pie.name;
	}
	public Color getColor1() {
		return selected_pie.color;
	}
	public Color getColor2() {
		Color color2 = color[new Random().nextInt(color.length)];
		while(color2 == getColor1()) {
			color2 = color[new Random().nextInt(color.length)];
		}
		return color2;
	}
	public void show(boolean show) {
		show_roulet = show;
	}
	
	public abstract void onRoulletStopped();
	
	public class Pie implements DrawableClip{
		private String name;
		private Color color;
		private float angle, arc;

		public Pie(String name, Color color, float angle, float arc) {
			this.name = name;
			this.color = color;
			this.angle = angle;
			this.arc = arc;
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			g2d.translate(x, y);
			
			
			g2d.setColor(color);
			g2d.fillArc(-(w/2), -(h/2), w, h, Math.round(angle-(arc/2)), Math.round(arc));

			g2d.rotate(Math.toRadians(-angle));
			g2d.setColor(Color.white);
			g2d.setFont(Resource.font[2]);
			g2d.drawString(
					name,
					w/5,
					g2d.getFontMetrics().getAscent()/2
			);
			g2d.rotate(Math.toRadians(angle));
			
			g2d.translate(-x, -y);
		}
	}

	private void stop_roullet() {
		onRoulletStopped();
		roullet_stopped = true;
		Sound.playOnStopRoullet();
	}
	private abstract class Arrow implements DrawableClip{
		private float arrow_angle;
		private Pie pie[];

		public Arrow(Pie pie[]) {
			this.pie = pie;
			arrow_angle = 0;
		}
		@Override
		public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
			g2d.rotate(Math.toRadians(arrow_angle));
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillOval(-w/2, -h/2, w, h);
			g2d.fillPolygon(
					new int[] {0, w, 0},
					new int[] {-h/2, 0, h/2},
					3
			);
			g2d.rotate(Math.toRadians(-arrow_angle));
		}
		public void point() {
			int p;
			for(p=0; p<pie.length; p++) {
				if((pie[p].angle) > (deg-(pie[p].arc/2)) && (pie[p].angle) < (deg+(pie[p].arc/2))) {
					onPoint(pie[p]);
					return;
				}
			}
			onPoint(pie[0]);
			
		}
		public abstract void onPoint(Pie pie);
	}

}
