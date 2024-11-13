package drawables;

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;

import interfaces.Drawable;
import res.Resource;

public abstract class Dialog extends Rectangle implements Drawable, AWTEventListener{
	private static final long serialVersionUID = -3659500411363550871L;
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
	public void draw(Graphics2D g2d) {
		g2d.setColor(Resource.main_color[2]);
		g2d.fillRoundRect(x, y, width, height, arc, arc);
		
		g2d.setColor(Color.black);
		g2d.setFont(Resource.font[3]);
		g2d.drawString(
				title,
				x + (width/2) - (g2d.getFontMetrics().stringWidth(title)/2), 
				y + (height/3)
		);
		
		btn_left.setBounds(
				x + (width/4) - (width/6),			//x
				y + ((height/4) * 3) - (height/16), //y
				(width/3), 							//width
				height/8							//height
		);
		btn_left.draw(g2d);
		
		btn_right.setBounds( 
				x + ((width/4) * 3) - (width/6), 
				btn_left.y, 
				btn_left.width, 
				btn_left.height
		);
		btn_right.draw(g2d);

		g2d.setColor(Resource.main_color[0]);
		g2d.setStroke(stroke);
		g2d.drawRoundRect(x, y, width, height, arc, arc);
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
	public Button getButtonLeft() {
		return btn_left;
	}
	public Button getButtonRight() {
		return btn_right;
	}

	public abstract void onLeftButtonClicked();
	public abstract void onRigghtButtonClicked();
	

}
