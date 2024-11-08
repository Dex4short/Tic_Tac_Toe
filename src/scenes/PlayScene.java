package scenes;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import default_package.Game;
import drawables.Dialog;
import drawables.DialogGameOver;
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
import sound.Sound;

public class PlayScene extends Rectangle implements Scene{
	private static final long serialVersionUID = 7565085178773988848L;
	private Rectangle rect;
	private GamePlay game_play;
	private TicTacToeBoard ticTacToe_board;
	private ButtonPause pause_btn;
	private Player player[];
	private Dialog pause_dialog, gameOver_dialog;
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
				Sound.playOnPause();
				pauseGame();
			}
		};

		roullet = new Roullet(game_play.getGame_mode()) {
			private static final long serialVersionUID = -6257683198736734735L;
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
		
		paused=false;
		suspended=false;
		game_over=false;
		
		Sound.playOnPlayeScene();
	}
	private int board_x, board_y, board_size,roullet_size;
	@Override
	public void draw(Graphics2D g2d) {
		rect = g2d.getClipBounds();
		board_x = (rect.width/2) - (board_size/2);
		board_y = (rect.height/2) - (board_size/2);
		board_size = Metrics.rectLength(rect.width, rect.height)/3;
		
		g2d.setColor(Resource.main_color[1].darker());
		g2d.fill(rect);
		
		if(roullet.isStoped()) {
			ticTacToe_board.setBounds(board_x, board_y, board_size, board_size);
			ticTacToe_board.draw(g2d);
			
			pause_btn.setBounds((rect.width/2) - 25, 10, 50, 50);
			pause_btn.draw(g2d);

			player[0].getScore().setBounds(board_x, 10, (board_size/2) - 25, 50);
			player[0].getScore().draw(g2d);

			player[1].getScore().setBounds(pause_btn.x + pause_btn.width, 10, (board_size/2) - 25, 50);
			player[1].getScore().draw(g2d);

			player[0].setBounds(board_x / 8, board_y + board_size, (board_x / 8) * 6, board_y);
			player[0].draw(g2d);

			player[1].setBounds(board_x + board_size + (board_x / 8), board_y + board_size, (board_x / 8) * 6, board_y);
			player[1].draw(g2d);
		}
		else{
			roullet_size = Metrics.rectLength(rect.width, rect.height)/2;
			roullet.setBounds(rect.width/2, rect.height/2, roullet_size, roullet_size);
			roullet.draw(g2d);
		}
		
		if(paused) {
			pause_dialog.setBounds((rect.width/2) - 300, (rect.height/2) - 150, 600, 300);
			pause_dialog.draw(g2d);
		}
		if(game_over) {
			gameOver_dialog.setBounds((rect.width/2) - 300, (rect.height/2) - 150, 600, 300);
			gameOver_dialog.draw(g2d);
		}
	}
	@Override
	public void eventDispatched(AWTEvent event) {
		if(ticTacToe_board != null) {
			if(paused) {
				pause_dialog.eventDispatched(event);
			}
			else if(game_over) {
				gameOver_dialog.eventDispatched(event);
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
			private static final long serialVersionUID = -8490797535326364956L;
			@Override
			public void onNext(int next) {
				System.out.println("completed = " + isBoardCompleted());
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
				Sound.playOnGainPoints();
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
			private static final long serialVersionUID = 350669359808645361L;
			@Override
			public TicTacToeBoard getTicTacToeBoard() {
				return ticTacToe_board;
			}
			@Override
			public void onMyTurn() {
				player[p2].myTurn(false);
				
			}
		};
		
		switch (game_play.getGame_mode()) {
		case PvP: 	player[p2] = new Human("Player 2") {
			private static final long serialVersionUID = 5032690812356272981L;
						@Override
						public TicTacToeBoard getTicTacToeBoard() {
							return ticTacToe_board;
						}
						@Override
						public void onMyTurn() {
							player[p1].myTurn(false);
						}
					};
			break;
		case PvCom: player[p2] = new AI(game_play.getDifficulty()) {
			private static final long serialVersionUID = 1808574018998206338L;
					@Override
					public TicTacToeBoard getTicTacToeBoard() {
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
		paused = true;
		pause_dialog = new DialogPause() {
			private static final long serialVersionUID = 5077599183497315251L;
			@Override
			public void onRigghtButtonClicked() {
				resumeGame();
			}
			@Override
			public void onLeftButtonClicked() {
				toMainMenu();
			}
			@Override
			public void onShown(boolean show) {
				paused = show;
			}
		};
		((DialogPause)pause_dialog).show(true);
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
		gameOver_dialog = new DialogGameOver() {
			private static final long serialVersionUID = 7781619642463379694L;
			@Override
			public void onRigghtButtonClicked() {
				newGame();
			}
			@Override
			public void onLeftButtonClicked() {
				toMainMenu();
			}
			@Override
			public Player getWinner() {				
				return winning_player();
			}
		};
		((DialogGameOver)gameOver_dialog).show(true);
	}
	private Player winning_player() {
		int
		p1_score = player[0].getScore().getAmount(),
		p2_score = player[1].getScore().getAmount();
		
		if(p1_score > p2_score) {
			return player[0];
		}
		else if(p1_score < p2_score) {
			return player[1];
		}
		else {
			return null;
		}
	}
	private void newGame() {
		Game.loading_screen.load(() -> {
			next_scene = new PlayScene(game_play);
		});
		game_over = false;
	}
}
