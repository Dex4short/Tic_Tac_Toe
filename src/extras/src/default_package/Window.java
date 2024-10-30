package default_package;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = -8255319694373975038L;
	private Game game;

	public Window() {
		setTitle("Tic Tac Toe");
		
		game = new Game();
		setContentPane(game);
		
	}
}
