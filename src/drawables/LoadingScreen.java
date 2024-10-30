package drawables;

import java.awt.Graphics2D;

import interfaces.DrawableClip;
import interfaces.Scene;

public class LoadingScreen implements DrawableClip{
	private String text;
	private Scene next_scene;
	
	public LoadingScreen(String text, Scene next_scene) {
		this.text = text;
		this.next_scene = next_scene;
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		//TODO
	}

}
