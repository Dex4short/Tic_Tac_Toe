package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import default_package.Game;
import drawables.Dialog;
import drawables.PauseButton;
import drawables.Roullet;
import drawables.TicTacToeBoard;
import extras.Metrics;
import extras.RGBA;
import extras.Timing;
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
	private Roullet roullet;
	private Scene next_scene;
	private int next_turn;
	
	public static boolean paused, game_over;
	

	public PlayScene(GamePlay game_play) {
		this.game_play = game_play;
		
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
						game_over = true;
						next_scene = new MainMenu();
					}
				});
			}
		};
		paused = false;
		alpha = 0;
		
		roullet = new Roullet(game_play.getGame_mode()) {
			@Override
			public void onRoulletStopped() {
				new Timing().sleep(1000);
				initialize_tictactoe_board();
				initialize_players();
				initialize_challenge();
			}
		};
		
		new Thread() {
			public void run() {
				while(!Game.loading_screen.isCurtainsOpen()) {
					new Timing().sleep(1000);
				}
				roullet.roll();
			};
		}.start();
		
		game_over = false;
	}
	private int board_x, board_y, board_size,roullet_size;
	@Override
	public void paint(Graphics2D g2d) {
		rect = g2d.getClipBounds();
		board_x = (rect.width/2) - (board_size/2);
		board_y = (rect.height/2) - (board_size/2);
		board_size = Metrics.rectLength(rect.width, rect.height)/3;
		
		g2d.setColor(Resource.main_color[1].darker());
		g2d.fill(rect);
		
		if(ticTacToe_board != null) {
			ticTacToe_board.drawClip(g2d, board_x, board_y, board_size, board_size);
			pause_btn.drawClip(g2d, (rect.width/2) - 25, 10, 50, 50);
			
			player[0].drawClip(g2d, board_x / 8, board_y + board_size, (board_x / 8) * 6, board_y);
			player[1].drawClip(g2d, board_x + board_size + (board_x / 8), board_y + board_size, (board_x / 8) * 6, board_y);
		}
		
		
		if(!roullet.isStoped()) {
			roullet_size = Metrics.rectLength(rect.width, rect.height)/2;
			roullet.drawClip(g2d, rect.width/2, rect.height/2, roullet_size, roullet_size);
		}
		
		if(paused) {
			draw_pause_background(g2d);
			draw_pause_dialog(g2d);
		}
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(ticTacToe_board != null) {
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
				
				player[next_turn].myTurn(true);
			}
		};
	}
	private int p1, p2;
	private void initialize_players() {
		player = new Player[2];
		
		if(roullet.getSelectedName() == "Player 1") {
			p1 = 0;
			p2 = 1;
		}
		else {
			p1 = 1;
			p2 = 0;
		}
		
		player[p1] = new Human("Player 1") {
			@Override
			public TicTacToeBoard getTacToeBoard() {
				return ticTacToe_board;
			}
			@Override
			public void onMyTurn() {
				player[p2].myTurn(false);
				
			}
		};
		
		switch (game_play.getGame_mode()) {
		case PvP: 	player[p2] = new Human("Player 2") {
						@Override
						public TicTacToeBoard getTacToeBoard() {
							return ticTacToe_board;
						}
						@Override
						public void onMyTurn() {
							player[p1].myTurn(false);
							
						}
					};
			break;
		case PvCom: player[p2] = new AI() {
					@Override
					public TicTacToeBoard getTacToeBoard() {
						return ticTacToe_board;
					}
					@Override
					public void onMyTurn() {
						player[p1].myTurn(false);
						onAction();//triger's Ai to make an action
					}
				};
			break;
		}
		
		player[0].setColor(roullet.getColor1());
		player[1].setColor(roullet.getColor2());
		
		player[0].myTurn(true);
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
