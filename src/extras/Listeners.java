package extras;

import java.awt.AWTEvent;
import java.awt.event.MouseEvent;

import interfaces.ButtonModel;

public class Listeners {

	private Listeners() {
	}
	public static void listen(AWTEvent event, ButtonModel btn_model) {
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			switch (e.getID()) {
			case MouseEvent.MOUSE_MOVED:
				btn_model.onHighlight(e);
				break;
			case MouseEvent.MOUSE_PRESSED:
				btn_model.onPress(e);
				break;
			case MouseEvent.MOUSE_RELEASED:
				btn_model.onRelease(e);
				break;
			case MouseEvent.MOUSE_CLICKED:
				btn_model.onClicked(e);
				break;
			}
		}
	}
}
