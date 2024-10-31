package interfaces;

import java.awt.event.MouseEvent;


public interface ButtonModel {
	public void onHighlight(MouseEvent e);
	public void onPress(MouseEvent e);
	public void onRelease(MouseEvent e);
	public void onClicked(MouseEvent e);
}
