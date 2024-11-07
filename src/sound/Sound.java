package sound;

public class Sound {
	
	public Sound() {
		
	}
	public static void playOnIntro() {
		//cancelled
	}
	public static void playOnLoadingCurtainsOpened() {
		//cancelled
	}
	public static void playOnLoading() {//1sec.
		System.out.println("Loading.mp3");
	}
	public static void playOnLoadingCurtainsClosed() {
		//cancelled
	}
	public static void playOnMainMenu() {
		//loop sound while in main menu.
		//stop when playOnPlayeScene() is playing.
		System.out.println("Main Menu.mp3");
	}
	public static void playOnHoverButton() {//1sec.
		System.out.println("button hovered.mp3");
	}
	public static void playOnClickButton() {//1sec.
		System.out.println("button clicked.mp3");
	}
	public static void playOnPlayeScene() {
		//loop sound while in play scene.
		//stop when playOnMainMenu() is playing.
		System.out.println("Play Scene.mp3");
	}
	public static void playOnRollRoullet() {//5sec ~ 8sec ?
		System.out.println("Roulet Roll.mp3");
	}
	public static void playOnStopRoullet() {//1sec
		System.out.println("Roullet Stop.mp3");
	}
	public static void playOnTicTacToeBoxAppeared() {//1sec
		System.out.println("Box Appeared.mp3");
	}
	public static void playOnTicTacToeBoxHovered() {//1sec
		System.out.println("Box Hovered.mp3");
	}
	public static void playOnPlayerCardRased() {//1sec
		System.out.println("Card Raised.mp3");
	}
	public static void playOnSymbolPlaced() {//1sec
		System.out.println("Symbol Placed.mp3");
	}
	public static void playOnLineDashed() {//1sec
		System.out.println("Line Dashed.mp3");
	}
	public static void playOnGainPoints() {//1sec
		System.out.println("Gain Points.mp3");
	}
	public static void playOnPause() {//1sec
		System.out.println("Pause.mp3");
	}
	public static void playOnPlayerWins() {//3sec
		System.out.println("Player Wins.mp3");
	}
	public static void playeOnPlayerLoses() {//3sec
		System.out.println("Player Lose.mp3");
	}
	public static void playOnDraw() {//3sec
		System.out.println("Draw.mp3");
	}

}
