package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import drawables.TicTacToeBoard;
import extras.Metrics;
import interfaces.Scene;
import objects.GamePlay;
import res.Resource;

public class PlayScene implements Scene{
	private GamePlay game_play;
	private TicTacToeBoard ticTacToe_board;
	private int board_size;
	private Rectangle rect;

	public PlayScene(GamePlay game_play) {
		this.game_play = game_play;
		
		ticTacToe_board = new TicTacToeBoard(game_play.getGrid_type());
		
	}
	@Override
	public void paint(Graphics2D g2d) {
		rect = g2d.getClipBounds();
		board_size = Metrics.rectLength(rect.width, rect.height)/3;
		
		g2d.setColor(Resource.main_color[1].darker());
		g2d.fill(rect);
		
		ticTacToe_board.drawClip(
				g2d,
				(rect.width/2) - (board_size/2),
				(rect.height/2) - (board_size/2),
				board_size,
				board_size
		);
		
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		
	}
	@Override
	public Scene next() {
		return null;
	}

}
