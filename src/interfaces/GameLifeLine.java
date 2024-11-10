package interfaces;

public interface GameLifeLine {

	public default void gameStart() {
		onGameStart();
	}
	public default void pauseGame() {
		onPauseGame();
	}
	public default void resumeGame() {
		onResumeGame();
	}
	public default void suspendGame() {
		onSuspendGame();
	}
	public default void gameOver() {
		onGameOver();
	}

	public void onGameStart();
	public void onPauseGame();
	public void onResumeGame();
	public void onSuspendGame();
	public void onGameOver();
}
