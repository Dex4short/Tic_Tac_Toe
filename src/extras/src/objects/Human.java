package objects;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import drawables.TicTacToeBox;

public abstract class Human extends Player implements AWTEventListener{
	private TicTacToeBox last_box;

	public Human(String name) {
		super(name);
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(event instanceof MouseEvent) {
			MouseEvent e = (MouseEvent)event;
			
			switch (e.getID()) {
			case MouseEvent.MOUSE_MOVED:
				onMouseMoved(e);
				break;
			case MouseEvent.MOUSE_CLICKED:
				onMouseClicked(e);
				break;
			}
			
		}
	}
	private void onMouseMoved(MouseEvent e) {
		TicTacToeBox box = getTacToeBoard().getBox(e.getPoint());

		if(last_box != null) {
			last_box.setHighlighted(false);
		}
		if(box != null) {
			box.setHighlighted(true);
			last_box = box;
		}
	}
	private void onMouseClicked(MouseEvent e) {
		TicTacToeBox box = getTacToeBoard().getBox(e.getPoint());
		
		if(box != null && box.getSymbol()==null) {
			box.setSymbol(getTacToeBoard().getNextSymbol());
			box.setHighlighted(false);
			getTacToeBoard().next();
		}
	}
}
