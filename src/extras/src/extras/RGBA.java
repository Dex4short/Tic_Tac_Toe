package extras;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class RGBA {

	private RGBA() {
		
	}
	public static Color setAlpha(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}
	public static Image alterImage(BufferedImage buff_img, Color color) {
		int 
		img_w = buff_img.getWidth(),
		img_h = buff_img.getHeight();
		
		Graphics2D g2d = (Graphics2D)buff_img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(buff_img, 0, 0, img_w, img_h, null);
		g2d.dispose();
		
		for(int x=0; x<img_w; x++) {
			for(int y=0; y<img_h; y++) {
				int argb = buff_img.getRGB(x, y);
				
				int alpha = (argb >> 24) & 0xFF;
				if(alpha==0) {
					continue;
				}
				
				int new_argb = (alpha << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
				buff_img.setRGB(x, y, new_argb);
			}
		}
		
		return buff_img;
	}
}
