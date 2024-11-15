package drawables;

import java.awt.AWTEvent;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.AWTEventListener;

import interfaces.Challengable;
import interfaces.Drawable;
import interfaces.GameLifeLine;
import interfaces.Updatable;
import objects.Player;

public class Challenge extends Rectangle implements Challengable, Drawable, AWTEventListener, Updatable, GameLifeLine{
	private static final long serialVersionUID = 5520762618902077387L;
	private Player challenger1, challenger2;

	public Challenge(Player challenger1, Player challenger2) {
		this.challenger1 = challenger1;
		this.challenger2 = challenger2;
	}
	@Override
	public void draw(Graphics2D g2d) {}
	@Override
	public void eventDispatched(AWTEvent event) {}
	@Override
	public void onUpdate(int update_code) {}
	@Override
	public void onChallengeAccepted(Player player) {}
	@Override
	public void onGameStart() {}
	@Override
	public void onPlayGame() {}
	@Override
	public void onPauseGame() {}
	@Override
	public void onResumeGame() {}
	@Override
	public void onSuspendGame() {}
	@Override
	public void onGameOver() {}
	public Player getChallenger1() {
		return challenger1;
	}
	public Player getChallenger2() {
		return challenger2;
	}
}
