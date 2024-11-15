package interfaces;

import objects.Player;

public interface Challengable{

	public default void acceptChallenge(Player player) {
		onChallengeAccepted(player);
	}
	public void onChallengeAccepted(Player player);
	
}
