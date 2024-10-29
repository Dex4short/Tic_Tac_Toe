package default_package;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import extras.Settings;
import interfaces.Scene;
import scenes.Intro;

public class Game  extends JPanel{
	private static final long serialVersionUID = -1530919121054777204L;
	private Scene scene;
	private Timer timer;
	private Graphics2D g2d;

	public Game() {
		scene = (Scene)new Intro();
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				repaint();
			}
		}, 0, 16);//60fps (1000 microseconds/ 60 frames = 16 microseconds intervals)
		
		enableEvents(
				AWTEvent.MOUSE_EVENT_MASK |				//for mouse clicked, pressed, released, entered, exited
				AWTEvent.MOUSE_MOTION_EVENT_MASK |		//for mouse dragged and moved
				AWTEvent.COMPONENT_EVENT_MASK			//for resize event
		);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		Settings.graphic_settings(g2d);
		
		scene.paint(g2d);
		if(scene.next() != null) {
			scene = scene.next();
		}
	}
	@Override
	protected void processEvent(AWTEvent e) {
		scene.eventDispatched(e);
		super.processEvent(e);
	}

}
