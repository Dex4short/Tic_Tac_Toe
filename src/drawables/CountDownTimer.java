package drawables;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import interfaces.Drawable;
import res.Resource;

public abstract class CountDownTimer extends Point implements Drawable{
	private static final long serialVersionUID = 8997033674311539925L;
	private int sec, mili_sec, seconds, miliSeconds;
	private String time;
	private BasicStroke stroke1, stroke2;
	private int str_w, str_h;
	private float deg, deg_iterate;
	private boolean start;
	
	public CountDownTimer() {
		stroke1 = new BasicStroke(10);
		stroke2 = new BasicStroke(1);
		
		set(2, 0);
		reset();
	}
	@Override
	public void draw(Graphics2D g2d) {
		time = timeString(sec, mili_sec);
		str_w = g2d.getFontMetrics().stringWidth(time);
		str_h = g2d.getFontMetrics().getAscent();
		
		g2d.setColor(Resource.main_color[1]);
		g2d.fillOval(x-(str_w/2), y-(str_w/2), str_w, str_w);
		
		g2d.setColor(Resource.main_color[1].darker());
		g2d.setStroke(stroke1);
		g2d.drawArc(x-(str_w/2)+10, y-(str_w/2)+10, str_w-20, str_w-20, 90, Math.round(deg));
		
		g2d.setColor(Resource.main_color[2]);
		g2d.setFont(Resource.font[1]);
		g2d.drawString(time, x - (str_w/2), y + (str_h/2));

		g2d.setStroke(stroke2);
		countDown();
	}
	public String timeString(int sec, int mili_sec) {
		return "0" + sec + " : " + ((mili_sec<10) ? "0" : "") + mili_sec;
	}
	public void start() {
		start = true;
	}
	public void stop() {
		start = false;
	}
	public void restart() {
		sec = seconds;
		mili_sec = miliSeconds;
		start();
	}
	public void set(int seconds, int miliSeconds) {
		this.seconds = seconds;
		this.miliSeconds = miliSeconds;
		deg_iterate = (360f / ((seconds*60f) + miliSeconds)) + 1f;
	}
	public void reset() {
		stop();
		this.sec = seconds;
		this.mili_sec = miliSeconds;
	}
	public void countDown() {
		if(start) {
			mili_sec--;
			if(mili_sec < 0) {
				
				sec--;
				if(sec < 0) {
					sec = seconds;
					mili_sec = miliSeconds;
					timeOut();
				}
				else {
					mili_sec = 59;
				}
			}

			deg = (deg_iterate * ((sec*60f) + mili_sec));
		}
	}
	public void timeOut() {
		onTimeOut();
	}
	public abstract void onTimeOut();
}
