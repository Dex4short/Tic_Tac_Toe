package drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import interfaces.Direction;
import interfaces.Drawable;
import res.Resource;

public class Line implements Drawable{
	private TicTacToeBox boxes[];
	private Color shadow;

	public Line(TicTacToeBoard board, int row, int col, Direction direction) {
		boxes = new TicTacToeBox[3];
		for(int i=0; i<boxes.length; i++) {
			boxes[i] = board.getBox(row + (direction.x_iterate*i), col + (direction.y_iterate*i));
			boxes[i].setMark(true);
		}
		
		shadow = new Color(0,0,0,32);
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(boxes[0].width/8));
		
		g2d.setColor(shadow);
		g2d.drawLine(
				boxes[0].x + (boxes[0].width/2) - 4,
				boxes[0].y + (boxes[0].height/2) + 4,
				boxes[2].x + (boxes[2].width/2) - 4, 
				boxes[2].y + (boxes[2].height/2) + 4
		);
		
		g2d.setColor(Resource.main_color[2]);
		g2d.drawLine(
				boxes[0].x + (boxes[0].width/2),
				boxes[0].y + (boxes[0].height/2),
				boxes[2].x + (boxes[2].width/2),
				boxes[2].y + (boxes[2].height/2)
		);
	}

}
