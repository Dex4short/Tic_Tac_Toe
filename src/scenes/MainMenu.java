package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

public class MainMenu extends Rectangle implements Scene{
	private static final long serialVersionUID = 7186429339437146539L;
	private Drawable background, title, menu_selection;
	private Scene next_scene;
	private AboutScene about_scene;

	public MainMenu() {
		background = new MenuBackground();
		title = new Title();
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
				about_scene.show(true);
			}
		};
		about_scene = new AboutScene();
		onResized(Settings.W, Settings.H);
		
		Sound.playOnMainMenu();
	}
	@Override
	public void draw(Graphics2D g2d) {
		setBounds(g2d.getClipBounds());
		background.draw(g2d);
		
		if(!about_scene.isShowing()) {
			title.draw(g2d);
			menu_selection.draw(g2d);
		}
		else {
			about_scene.setBounds(x, y, width, height);
			about_scene.draw(g2d);
		}
	}
	@Override
	public Scene next() {
		return next_scene;
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(!about_scene.isShowing()) {
			((MenuSelection)menu_selection).eventDispatched(event);
			if(event instanceof ComponentEvent) {
				ComponentEvent e = (ComponentEvent)event;
				if(e.getID() == ComponentEvent.COMPONENT_RESIZED) {
					onResized(width, height);
				}
			}
		}
		else {
			about_scene.eventDispatched(event);
		}
	}
	public void onResized(int w, int h) {
		((MenuBackground)background).setBounds(0, 0, w, h);
		((Title)title).setBounds(0, 0, w, h);
		((MenuSelection)menu_selection).setBounds(0, 0, w, h);
	}
}
