package interfaces;

import java.awt.event.MouseEvent;


public interface ButtonModel {
	public void onHover(MouseEvent e);
	public void onPress(MouseEvent e);
	public void onRelease(MouseEvent e);
	public void onClicked(MouseEvent e);
}
