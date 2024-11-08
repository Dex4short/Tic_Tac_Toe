package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;

import default_package.Game;
import drawables.MenuBackground;
import drawables.MenuSelection;
import drawables.Title;
import extras.Settings;
import interfaces.Drawable;
import interfaces.Scene;
import objects.GamePlay;
import sound.Sound;

public class MainMenu implements Scene{
	private Drawable background, title, menu_selection;
	private Scene next_scene;

	public MainMenu() {
		background     = new MenuBackground();
		title 		   = new Title();
		menu_selection = new MenuSelection() {
			private static final long serialVersionUID = 2531560105775009568L;
			@Override
			public void onPlay(GamePlay game_play) {
				Game.loading_screen.load(new Runnable() {
					@Override
					public void run() {
						next_scene = new PlayScene(game_play);
					}
				});
			}
			@Override
			public void onAbout() {
				//TODO
			}
		};
		onResized(Settings.W, Settings.H);
		
		Sound.playOnMainMenu();
	}
	@Override
	public void draw(Graphics2D g2d) {		
		background.draw(g2d);
		
		title.draw(g2d);
		
		menu_selection.draw(g2d);
	}
	@Override
	public Scene next() {
		return next_scene;
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		((MenuSelection)menu_selection).eventDispatched(event);
		
		if(event instanceof ComponentEvent) {
			ComponentEvent e = (ComponentEvent)event;
			
			if(e.getID() == ComponentEvent.COMPONENT_RESIZED) {
				onResized(Settings.W, Settings.H);
			}
		}
	}
	public void onResized(int w, int h) {
		((MenuBackground)background).setBounds(0, 0, w, h);
		((Title)title).setBounds(0, 0, w, h);
		((MenuSelection)menu_selection).setBounds(0, 0, w, h);
	}
}
