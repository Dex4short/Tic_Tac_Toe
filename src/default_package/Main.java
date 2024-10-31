package default_package;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[]args) {
		Window w = new Window();
		w.setSize(800, 600);
		w.setMinimumSize(new Dimension(800,600));
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setVisible(true);
		
	}
}
