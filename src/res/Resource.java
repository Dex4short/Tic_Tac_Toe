package res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Resource {
	public static Color main_color[] = {
			new Color(220, 87,  88),//peach
			new Color(  8, 97, 105),//blue-green
			Color.white,			//white
			new Color(189, 40,  40)//dark peach
	};
	public static Font font[] = {
			new Font("Calibri", Font.BOLD, 16)
	};

	public Resource() {}
	public URL get(String file_name) {
		return getClass().getResource(file_name);
	}
	public static Image getImage(String file_name) {
		try {
			return ImageIO.read(new Resource().get(file_name));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static BufferedImage createBufferedImage(String file_name) {
		try {
			return ImageIO.read(new Resource().get(file_name));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
