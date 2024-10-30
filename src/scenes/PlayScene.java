package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;

import interfaces.Scene;
import objects.GamePlay;

public class PlayScene implements Scene{
	private GamePlay game_play;

	public PlayScene(GamePlay game_play) {
		this.game_play = game_play;
	}
	@Override
	public void paint(Graphics2D g2d) {
		
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		
	}
	@Override
	public Scene next() {
		return null;
	}

}
