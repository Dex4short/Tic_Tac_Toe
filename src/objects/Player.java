package objects;

import drawables.NameTag;
import drawables.Score;
import drawables.TicTacToeBoard;

public abstract class Player{
	private Score score;
	private NameTag name_tag;
	private boolean myTurn;

	public Player(String name) {
		score = new Score();
		name_tag = new NameTag(name);
		
		setName(name);
	}
	public String getName() {
		return name_tag.getName();
	}
	public void setName(String name) {
		name_tag.setName(name);
	}
	public boolean isMyTurn() {
		return myTurn;
	}
	public void myTurn(boolean myTurn) {
		this.myTurn = myTurn;
		
		if(isMyTurn()) {
			System.out.println(name_tag.getName() + "'s turn");
			name_tag.show();
			onMyTurn();
		}
		else {
			name_tag.hide();
		}
	}
	public Score getScore() {
		return score;
	}
	public NameTag getNameTag() {
		return name_tag;
	}
	
	public abstract TicTacToeBoard getTicTacToeBoard();
	public abstract void onMyTurn();
	
}
