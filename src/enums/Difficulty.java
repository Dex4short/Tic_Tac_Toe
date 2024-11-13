package enums;

import ai.AI_Easy;
import ai.AI_Hard;
import ai.AI_Normal;
import ai.AI_TicTacToe;

public enum Difficulty {
	Easy(new AI_Easy()),
	Normal(new AI_Normal()),
	Hard(new AI_Hard());
	
	public AI_TicTacToe ai_tictactoe;
	private Difficulty(AI_TicTacToe ai_tictactoe) {
		this.ai_tictactoe = ai_tictactoe;
	}
}
