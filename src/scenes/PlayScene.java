package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import default_package.Game;
import drawables.Dialog;
import drawables.DialogPause;
import drawables.ButtonPause;
import drawables.Roullet;
import drawables.TicTacToeBoard;
import extras.Metrics;
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
	private ButtonPause pause_btn;
	private Player player[];
	private Dialog pause_dialog;
	private Roullet roullet;
	private Scene next_scene;
	private int next_turn;
	
	public static boolean paused=false, suspended=false, game_over=false;
	

	public PlayScene(GamePlay game_play) {
		this.game_play = game_play;
		
		pause_btn = new ButtonPause() {
			private static final long serialVersionUID = -8785478662783287079L;
			@Override
			public void onPause() {
				pauseGame();
			}
		};
		
		pause_dialog = new DialogPause() {
			@Override
			public void onRigghtButtonClicked() {
				resumeGame();
			}
			@Override
			public void onLeftButtonClicked() {
				toMainMenu();
			}
		};

		roullet = new Roullet(game_play.getGame_mode()) {
			@Override
			public void onRoulletStopped() {
				new Timing().sleep(1000);
				initialize_tictactoe_board();
				initialize_players();
				initialize_challenge();
			}
		};
		Game.loading_screen.whenFullyOpened(() -> {
			roullet.show(true);
			roullet.roll();
		});
		
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
		
		if(roullet.isStoped()) {
			ticTacToe_board.drawClip(g2d, board_x, board_y, board_size, board_size);
			pause_btn.drawClip(g2d, (rect.width/2) - 25, 10, 50, 50);

			player[0].getScore().drawClip(g2d, board_x, 10, (board_size/2) - 25, 50);
			player[1].getScore().drawClip(g2d, pause_btn.x + pause_btn.width, 10, (board_size/2) - 25, 50);
			
			player[0].drawClip(g2d, board_x / 8, board_y + board_size, (board_x / 8) * 6, board_y);
			player[1].drawClip(g2d, board_x + board_size + (board_x / 8), board_y + board_size, (board_x / 8) * 6, board_y);
		}
		else{
			roullet_size = Metrics.rectLength(rect.width, rect.height)/2;
			roullet.drawClip(g2d, rect.width/2, rect.height/2, roullet_size, roullet_size);
		}
		
		if(paused) {
			pause_dialog.drawClip(g2d, (rect.width/2) - 300, (rect.height/2) - 150, 600, 300);
		}
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(ticTacToe_board != null) {
			if(paused) {
				pause_dialog.eventDispatched(event);
			}
			else{
				if(player[next_turn] instanceof Human) {
					((Human)player[next_turn]).eventDispatched(event);//Enables users to make an action through event input
				}
				pause_btn.eventDispatched(event);
			}
		}
	}
	@Override
	public Scene next() {
		return next_scene;
	}

	private void initialize_tictactoe_board() {
		ticTacToe_board = new TicTacToeBoard(game_play.getGrid_type()) {
			@Override
			public void onNext(int next) {
				if(!isBoardCompleted()) {
					next_turn = next;
					player[next_turn].myTurn(true);
				}
				else {
					gameOver();					
				}
			}
			@Override
			public void onCheck() {
				player[next_turn].getScore().addAmount(1);
			}
		};
	}
	private int p1, p2;
	private void initialize_players() {
		player = new Player[2];

		p1 = 0;
		p2 = 1;
		if(roullet.getSelectedName() != "Player 1") {
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
	private void pauseGame() {
		((DialogPause)pause_dialog).show(true);
		paused = true;
	}
	private void toMainMenu() {
		Game.loading_screen.load(() -> {
			suspended = true;
			next_scene = new MainMenu();
		});
	}
	private void resumeGame() {
		((DialogPause)pause_dialog).show(false);
	}
	private void gameOver() {
		game_over = true;
	}
}
