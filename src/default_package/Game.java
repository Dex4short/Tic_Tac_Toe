package default_package;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import drawables.LoadingScreen;
import extras.Settings;
import interfaces.Scene;
import scenes.Intro;
import scenes.PlayScene;

public class Game extends JPanel implements Runnable{
	private static final long serialVersionUID = -1530919121054777204L;
	private Scene scene;
	private Graphics2D g2d;
	private Thread gameThread;
	private volatile boolean running;

	public static LoadingScreen loading_screen;

	public Game() {
		running = false;
		
		//Loading Screen
		loading_screen = new LoadingScreen("Loading...");
		
		//Game Introduction
		scene = (Scene)new Intro() {
			@Override
			public Scene next() {
				Scene next_scene = super.next();
				
				if(next_scene != null) {
					loading_screen.load(null);
					scene = next_scene;
				}
				
				return null;
			}
		};
		
		//Enabling Mouse and Window Events
		enableEvents(
				AWTEvent.MOUSE_EVENT_MASK |				//for mouse clicked, pressed, released, entered, exited
				AWTEvent.MOUSE_MOTION_EVENT_MASK |		//for mouse dragged and moved
				AWTEvent.COMPONENT_EVENT_MASK			//for resize event
		);
		
		
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		Settings.graphic_settings(g2d);
		
		//render current scene
		scene.paint(g2d);
		if(scene.next() != null) scene = scene.next();
		
		if(loading_screen.isLoading()) {
			loading_screen.drawClip(g2d, 0, 0, getWidth(), getHeight());
		}
	}
	@Override
	protected void processEvent(AWTEvent e) {
		if(loading_screen.isLoading()) return;
		
		scene.eventDispatched(e);
		super.processEvent(e);
	}
	public void start() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void stop() {
		PlayScene.suspended = true;
		running = false;
		try {
			gameThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		final int FPS = 60;
		final long drawInterval = 1000000000/FPS;
		
		while(running) {
			long startTime = System.nanoTime();
			
			repaint();
			
			long elapsedTime = System.nanoTime() - startTime;
			long sleepTime = (drawInterval- elapsedTime)/ 1000000;//convert to millisecond
			
			if(sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		}
		
		
	}
	

	

}
