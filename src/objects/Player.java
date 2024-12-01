package objects;

import drawables.NameTag;
import drawables.Score;
import drawables.TicTacToeBoard;
import enums.Symbol;

public abstract class Player{
	private Score score;
	private NameTag name_tag;
	private Symbol assigned_symbol;
	private boolean myTurn;

	public Player(String name) {
		score = new Score();
		name_tag = new NameTag(name) {
			private static final long serialVersionUID = 8532937625774334911L;
			@Override
			public void onShown() {
				onMyTurn();
				myTurn = true;
			}
			@Override
			public void onHidden() {}
		};
		
		setName(name);
	}
	public String getName() {
		return name_tag.getName();
	}
	public void setName(String name) {
		name_tag.setName(name);
	}
	public Symbol getAssignedSymbol() {
		return assigned_symbol;
	}
	public void setAssignedSymbol(Symbol assigned_symbol) {
		this.assigned_symbol = assigned_symbol;
		name_tag.setName(name_tag.getName() + " '" + assigned_symbol.toString() + "'");
	}
	public boolean isMyTurn() {
		return myTurn;
	}
	public void myTurn(boolean myTurn) {
		if(myTurn) {
			System.out.println(name_tag.getName() + "'s turn");
			name_tag.show();
		}
		else {
			myTurn = false;
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
