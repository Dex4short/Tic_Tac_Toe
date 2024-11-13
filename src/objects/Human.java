package objects;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import drawables.TicTacToeBox;

public abstract class Human extends Player implements AWTEventListener{
	
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
	private TicTacToeBox last_box;
	private void onMouseMoved(MouseEvent e) {
		TicTacToeBox box = getTicTacToeBoard().getBox(e.getPoint());
		if(last_box!=box && last_box!=null) {
			last_box.setHighlighted(false);
			last_box = null;
		}
		if(box != null) {
			box.setHighlighted(true);
			last_box = box;
		}
	}
	private void onMouseClicked(MouseEvent e) {
		TicTacToeBox box = getTicTacToeBoard().getBox(e.getPoint());
		if(box != null && box.getSymbol()==null) {
			box.setSymbol(getTicTacToeBoard().getNextSymbol());
			box.setHighlighted(false);
			getTicTacToeBoard().next();
		}
	}
}
