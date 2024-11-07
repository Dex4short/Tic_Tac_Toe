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
	public static void playOnSymbolPlaced() {
		
	}
	public static void playOnLineDashed() {
		
	}
	public static void playOnGainPoints() {
		
	}
	public static void playOnPause() {
		
	}
	public static void playOnPlayerWins() {
		
	}
	public static void playeOnPlayerLoses() {
		
	}
	public static void playOnDraw() {
		
	}

}
