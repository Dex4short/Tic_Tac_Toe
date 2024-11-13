package drawables;

import java.awt.Rectangle;

import enums.BombPowerUp;

public class Card extends Rectangle{
	private static final long serialVersionUID = 3535430831853344022L;
	private BombPowerUp bomb_powerup;

	public void setBomb_card(BombPowerUp bomb_powerup) {
		this.bomb_powerup = bomb_powerup;
	}
	public Card(BombPowerUp bomb_powerup) {
		this.setBomb_card(bomb_powerup);
	}
	public BombPowerUp getBomb_card() {
		return bomb_powerup;
	}
	
}
