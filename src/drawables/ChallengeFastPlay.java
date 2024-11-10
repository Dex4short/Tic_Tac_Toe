package drawables;

import java.awt.Graphics2D;

import scenes.PlayScene;

public abstract class ChallengeFastPlay extends Challenge{
	private static final long serialVersionUID = 7040312232811478999L;
	private CountDownTimer countDown_timer;
	
	public ChallengeFastPlay() {
		countDown_timer = new CountDownTimer() {
			private static final long serialVersionUID = -4611428062133590989L;
			
			@Override
			public void onTimeOut() {
				nextPlayer();
			}
			@Override
			public void countDown() {
				if(!PlayScene.paused || !PlayScene.game_over) {
					super.countDown();
				}
			}
		};		
	}
	@Override
	public void draw(Graphics2D g2d) {
		countDown_timer.setLocation(width/2 , height-50);
		countDown_timer.draw(g2d);
	}
	@Override
	public void onUpdate(int update_code) {
		
	}
	@Override
	public void onChallengeAccepted() {
		countDown_timer.restart();
	}
	@Override
	public void onGameStart() {
		countDown_timer.start();
	}
	@Override
	public void onPauseGame() {
		countDown_timer.stop();
	}
	@Override
	public void onResumeGame() {
		countDown_timer.start();
	}
	@Override
	public void onSuspendGame() {
		countDown_timer.stop();
	}
	@Override
	public void onGameOver() {
		countDown_timer.stop();
	}
	
	public void nextPlayer() {
		countDown_timer.stop();
		onNextPlayer();
	}
	public abstract void onNextPlayer();
}
