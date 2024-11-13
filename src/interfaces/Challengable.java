package interfaces;

import drawables.TicTacToeBoard;
import objects.Player;

public interface Challengable{

	public default void acceptChallenge(Player player) {
		onChallengeAccepted(player);
	}
	public default void applyChallenge(TicTacToeBoard board) {
		onApplyChallenge(board);
	}
	public void onChallengeAccepted(Player player);
	public void onApplyChallenge(TicTacToeBoard board);
	
}
