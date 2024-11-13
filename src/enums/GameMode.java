package enums;

public enum GameMode {
	PvP("Player 1", "Player 2"),
	PvCom("Player 1", "Computer");
	
	public String player1,player2;
	private GameMode(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
}
