package drawables;

import java.awt.Graphics2D;

import objects.Human;
import objects.Player;
import sound.Sound;

public abstract class DialogGameOver extends DialogPause{
	private static final long serialVersionUID = 7227538691631842214L;
	
	public DialogGameOver() {
		setTitle("");
		getButtonLeft().setButtonName("Main Menu");
		getButtonRight().setButtonName("New Game");
	}
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
	}
	@Override
	public void show(boolean show) {
		if(show) {
			alpha_iterate=1;
		}
		else {
			alpha_iterate=-1;
		}
	}
	@Override
	public void onShown(boolean show) {
		Player winner = getWinner();
		
		if(show) {
			if(winner == null) {
				setTitle("Draw!");
				Sound.playOnDraw();
			}
			else {
				setTitle(winner.getName() + " Wins!");
				if(winner instanceof Human) {
					Sound.playOnPlayerWins();
				}
				else {
					Sound.playeOnPlayerLoses();
				}
			}
		}
		else {
			setTitle("");
		}
	}
	public abstract Player getWinner();
}
