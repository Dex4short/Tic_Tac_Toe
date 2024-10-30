package objects;

import drawables.TicTacToeBoard;

public abstract class Player {
	private String name;

	public Player(String name) {
		setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public abstract TicTacToeBoard getTacToeBoard();
}
