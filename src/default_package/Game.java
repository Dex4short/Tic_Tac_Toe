package default_package;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import extras.Settings;
import interfaces.Scene;
import scenes.Intro;

public class Game extends JPanel implements Runnable{
	private static final long serialVersionUID = -1530919121054777204L;
	private Scene scene;
	private Graphics2D g2d;
	private Thread gameThread;
	private volatile boolean running = false;

	public Game() {
		//Game intro
		scene = (Scene)new Intro();
		//Game Loop here
		startGameLoop();
				
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
	}
	@Override
	protected void processEvent(AWTEvent e) {
		scene.eventDispatched(e);
		super.processEvent(e);
	}
	private void startGameLoop() {
			
			running = true;
			gameThread = new Thread(this);
			gameThread.start();
			
		}
	//wa pa nagamit ni nga part dex kaw ray bahala utilize ani 
	//or e ayaw nalang gud gamita kinsa raman sab ni 
	private void stopGameLoop() {
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
