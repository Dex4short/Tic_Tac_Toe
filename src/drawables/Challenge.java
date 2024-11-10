package drawables;

import java.awt.Rectangle;

import interfaces.Drawable;
import interfaces.GameLifeLine;
import interfaces.Updatable;

public abstract class Challenge extends Rectangle implements Drawable, Updatable, GameLifeLine{
	private static final long serialVersionUID = 2988873997856900886L;

	public Challenge() {
		
	}
	public void acceptChallenge() {
		onChallengeAccepted();
	}
	public abstract void onChallengeAccepted();
}
