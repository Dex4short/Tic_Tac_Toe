package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import default_package.Game;
import drawables.Dialog;
import drawables.PauseButton;
import drawables.TicTacToeBoard;
import extras.Metrics;
import extras.RGBA;
import interfaces.Scene;
import objects.AI;
import objects.GamePlay;
import objects.Human;
import objects.Player;
import res.Resource;

public class PlayScene implements Scene{
	private Rectangle rect;
	private GamePlay game_play;
	private TicTacToeBoard ticTacToe_board;
	private PauseButton pause_btn;
	private Player player[];
	private Dialog pause_dialog;
	private Scene next_scene;
	private int board_size, next_turn;
	
	public static boolean paused;
	

	public PlayScene(GamePlay game_play) {
		this.game_play = game_play;
		
		initialize_tictactoe_board();
		initialize_players();
		initialize_challenge();
		
		pause_btn = new PauseButton() {
			private static final long serialVersionUID = -8785478662783287079L;
			@Override
			public void onPause() {
				alpha_iterate=4;
				paused = true;
			}
		};
		
		pause_dialog = new Dialog("Game Paused", "Main Menu", "Resume Game") {
			@Override
			public void onRigghtButtonClicked() {
				alpha_iterate=-4;
			}
			@Override
			public void onLeftButtonClicked() {
				Game.loading_screen.load(new Runnable() {
					@Override
					public void run() {
						next_scene = new MainMenu();
					}
				});
			}
		};
		
		paused = false;
		alpha = 0;
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
		
		pause_btn.drawClip(g2d, (rect.width/2) - 25, 10, 50, 50);
		if(paused) {
			draw_pause_background(g2d);
			draw_pause_dialog(g2d);
		}
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(!paused) {
			if(player[next_turn] instanceof Human) {
				((Human)player[next_turn]).eventDispatched(event);//Enables users to make an action through event input
			}
			pause_btn.eventDispatched(event);
		}
		else if(paused && alpha_iterate==0){
			pause_dialog.eventDispatched(event);
		}
	}
	@Override
	public Scene next() {
		return next_scene;
	}
	
	private int alpha, alpha_iterate;
	private void draw_pause_background(Graphics2D g2d) {
		g2d.setColor(RGBA.setAlpha(Resource.main_color[1], alpha));
		g2d.fill(rect);

		alpha += alpha_iterate;
		if(alpha == 128) {
			alpha_iterate = 0;
		}
		else if(alpha == 0) {
			paused = false;
			alpha_iterate = 0;
		}
	}
	private void draw_pause_dialog(Graphics2D g2d) {
		if(alpha_iterate >= 0 && alpha>0) {
			pause_dialog.drawClip(g2d, (rect.width/2) - 300, (rect.height/2) - 150, 600, 300);
		}
	}
	private void initialize_tictactoe_board() {
		ticTacToe_board = new TicTacToeBoard(game_play.getGrid_type()) {
			@Override
			public void onNext(int next) {
				next_turn = next;
				
				if(player[next_turn] instanceof AI) {
					((AI)player[next_turn]).onAction();//triger's Ai to make an action
				}
			}
		};
	}
	private void initialize_players() {
		player = new Player[2];
		
		player[0] = new Human("Player 1") {
			@Override
			public TicTacToeBoard getTacToeBoard() {
				return ticTacToe_board;
			}
		};
		
		switch (game_play.getGame_mode()) {
		case PvP:
			player[1] = new Human("Player 2") {
				@Override
				public TicTacToeBoard getTacToeBoard() {
					return ticTacToe_board;
				}
			};
			break;
		case PvCom:
			player[1] = new AI() {
					@Override
					public TicTacToeBoard getTacToeBoard() {
						return ticTacToe_board;
					}
				};
			break;
		}
		
	}
	private void initialize_challenge() {
		//TODO
		switch (game_play.getChallenge()) {
		case None:
			
			break;
		case FastPLay:
			
			break;
		case BombAttack:
			
			break;
		}
	}
}
