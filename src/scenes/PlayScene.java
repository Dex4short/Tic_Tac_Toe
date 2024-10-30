package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import drawables.TicTacToeBoard;
import enums.GameMode;
import extras.Metrics;
import interfaces.Scene;
import objects.AI;
import objects.GamePlay;
import objects.Human;
import objects.Player;
import res.Resource;

public class PlayScene implements Scene{
	private Rectangle rect;
	private int board_size, next_turn;
	private TicTacToeBoard ticTacToe_board;
	private Player player[];

	public PlayScene(GamePlay game_play) {		
		ticTacToe_board = new TicTacToeBoard(game_play.getGrid_type()) {
			@Override
			public void onNext(int next) {
				next_turn = next;
				
				if(player[next_turn] instanceof AI) {
					((AI)player[next_turn]).onAction();//triger's Ai to make an action
				}
			}
		};
		
		player = new Player[] {
			new Human("Player 1") {
				@Override
				public TicTacToeBoard getTacToeBoard() {
					return ticTacToe_board;
				}
			},
			new Human("Player 2") {
				@Override
				public TicTacToeBoard getTacToeBoard() {
					return ticTacToe_board;
				}
			}
		};
		
		if(game_play.getGame_mode() == GameMode.PvCom) {
			player[1] = new AI() {
				@Override
				public TicTacToeBoard getTacToeBoard() {
					return ticTacToe_board;
				}
			};
		}
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
		if(player[next_turn] instanceof Human) {
			((Human)player[next_turn]).eventDispatched(event);//Enables users to make an action through event input
		}
	}
	@Override
	public Scene next() {
		return null;
	}

}
