package objects;

import enums.Challenge;
import enums.GameMode;
import enums.GridType;

public class GamePlay {
	private GridType grid_type;
	private GameMode game_mode;
	private Challenge challenge;
	
	public GamePlay() {
		setGrid_type(GridType.Grid_3x3);
		setGame_mode(GameMode.PvP);
		setChallenge(Challenge.None);
	}
	@Override
	public String toString() {
		return "game mode: " + game_mode.toString() + ", challenge: " + challenge.toString() + ", grid type: " + grid_type.toString();
	}
	public GameMode getGame_mode() {
		return game_mode;
	}
	public void setGame_mode(GameMode game_mode) {
		this.game_mode = game_mode;
	}
	public GridType getGrid_type() {
		return grid_type;
	}
	public void setGrid_type(GridType grid_type) {
		this.grid_type = grid_type;
	}
	public Challenge getChallenge() {
		return challenge;
	}
	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}
}
