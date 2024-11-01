package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.AWTEventListener;

import interfaces.DrawableClip;
import res.Resource;

public abstract class Dialog implements DrawableClip, AWTEventListener{
	private String title;
	private int arc;
	private BasicStroke stroke;
	private Button btn_left, btn_right;

	public Dialog(String title, String btn1_name, String btn2_name) {
		setTitle(title);
		
		stroke = new BasicStroke(8);
		arc = 20;
		
		btn_left = new Button(btn1_name) {
			private static final long serialVersionUID = 333149180292055639L;
			@Override
			public void onAction() {
				onLeftButtonClicked();
			}
		};
		
		btn_right = new Button(btn2_name) {
			private static final long serialVersionUID = -1401807116833725364L;
			@Override
			public void onAction() {
				onRigghtButtonClicked();
			}
		};
		
	}
	@Override
	public void drawClip(Graphics2D g2d, int x, int y, int w, int h) {
		g2d.setColor(Resource.main_color[2]);
		g2d.fillRoundRect(x, y, w, h, arc, arc);
		
		g2d.setColor(Color.black);
		g2d.setFont(Resource.font[3]);
		g2d.drawString(
				title,
				x + (w/2) - (g2d.getFontMetrics().stringWidth(title)/2), 
				y + (h/3)
		);
		
		btn_left.drawClip(
				g2d, 						//Graphics2D
				x + (w/4) - (w/6),			//x
				y + ((h/4) * 3) - (h/16), 	//y
				(w/3), 						//width
				h/8							//height
		);
		btn_right.drawClip(
				g2d, 
				x + ((w/4) * 3) - (w/6), 
				btn_left.y, 
				btn_left.width, 
				btn_left.height
		);

		g2d.setColor(Resource.main_color[0]);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(x, y, w, h, arc, arc);
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		btn_left.eventDispatched(event);
		btn_right.eventDispatched(event);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public abstract void onLeftButtonClicked();
	public abstract void onRigghtButtonClicked();
	

}
