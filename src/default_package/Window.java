package default_package;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Window extends JFrame implements WindowListener{
	private static final long serialVersionUID = -8255319694373975038L;
	private Game game;

	public Window() {
		setTitle("Tic Tac Toe");
		
		game = new Game();
		setContentPane(game);
		
		game.start();//game Loop here
		
	}
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {
		game.stop();//game stops
	}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}
