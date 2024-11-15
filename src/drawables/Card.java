package drawables;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import enums.BombPowerUp;
import extras.Metrics;
import interfaces.Drawable;
import res.Resource;

public class Card extends Rectangle implements Drawable{
	private static final long serialVersionUID = 3535430831853344022L;
	private BombPowerUp powerup;
	private Image back_card;
	private int deg=0, deg_width, deg_iterate=0;
	private boolean flip_front, flip_back, isFacedFront=true,isTrigered,isActivated;

	public Card(BombPowerUp bomb_powerup) {
		setPowerUp(bomb_powerup);
		back_card = Resource.getImage("bomb_card_back.png");
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.translate(x+(width/2), y+(height/2));
		if(deg<=90 || deg>=270) {
			deg_width = (int)Math.round(width * Math.cos(Math.toRadians(deg)));
			g2d.drawImage(
					powerup.img,
					-(deg_width/2),
					-(height/2),
					deg_width,
					height,
					null
			);
		}
		else {
			deg_width = (int)Math.round(width * Math.cos(Math.toRadians(deg-180)));
			g2d.drawImage(
					back_card,
					-(deg_width/2),
					-(height/2), 
					deg_width,
					height, 
					null
			);
		}
		g2d.translate(-(x+(width/2)), -(y+(height/2)));

		deg+=deg_iterate;
		if(isTrigered) {
			if(deg>90) {
				deg=90;
				deg_iterate=0;
				isFacedFront = false;
				isTrigered = false;
				isActivated = true;
			}
		}
		else {
			if(flip_front) {
				if(deg<0) {
					deg=0;
					deg_iterate=0;
					flip_front=false;
					isFacedFront = true;
				}
			}
			else if(flip_back) {
				if(deg>180) {
					deg=180;
					deg_iterate=0;
					flip_back=false;
					isFacedFront = false;
				}
			}
		}
		
		if(deg>=360) {
			deg=0;
		}
	}
	public void setPowerUp(BombPowerUp powerup) {
		this.powerup = powerup;
	}
	public BombPowerUp getPowerUp() {
		return powerup;
	}
	public void spin(int speed) {
		deg_iterate = speed;
	}
	public void faceFront() {
		deg=0;
		isFacedFront = true;
	}
	public void faceBack() {
		deg=180;
		isFacedFront = false;
	}
	public void flipFront() {
		flip_front = true;
		spin(-10);
	}
	public void flipBack() {
		flip_back = true;
		spin(10);
	}
	public boolean isFacedFront() {
		return isFacedFront;
	}
	public boolean isActivated() {
		return isActivated;
	}
	public void activate() {
		isTrigered = true;
		deg_iterate = 10;
	}
	
	public static double card_scale;
	public static int card_w, card_h;
	public static void cardScale(int screen_width, int screen_height) {
		card_scale = Metrics.rectLength(screen_width, screen_height) / 1000f;
		card_w = (int)Math.round( 70 * card_scale);
		card_h = (int)Math.round(106 * card_scale);
	}
}
