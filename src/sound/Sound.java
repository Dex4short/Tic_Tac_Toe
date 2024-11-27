package sound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Sound {
    private static Thread mainMenuThread;
    private static AdvancedPlayer mainMenuPlayer;
    private static boolean isMainMenuPlaying = false;
    
    private static Thread inGameThread;
    private static AdvancedPlayer inGamePlayer;
    private static boolean isInGamePlaying = false;

    public Sound() { }

    public static void playOnIntro() {
        // cancelled
    }

    public static void playOnLoadingCurtainsOpened() {
        // cancelled
    	System.out.println("CurtainSLide");
    	
    	new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("curtainSlide.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();
                
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnLoading() { // 1 sec
        System.out.println("Loading.mp3");
    }

    public static void playOnLoadingCurtainsClosed() {
        // cancelled
    }

    public static void playOnMainMenu() {
        System.out.println("Main Menu.mp3");
        
        stopInGameAudio();

        mainMenuThread = new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("MainMenu.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                mainMenuPlayer = new AdvancedPlayer(bufferedIn);
                isMainMenuPlaying = true;
                mainMenuPlayer.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            } finally {
                isMainMenuPlaying = false;
            }
        });

        mainMenuThread.start();
    }

    public static void stopMainMenuAudio() {
        if (isMainMenuPlaying && mainMenuPlayer != null) {
            mainMenuThread.interrupt(); // Interrupt the main menu thread
            
            // Closing AdvancedPlayer to stop it
            try {
                mainMenuPlayer.close();
            } catch (Exception e) {
                System.out.println("Error stopping main menu player: " + e.getMessage());
            }
            
            isMainMenuPlaying = false;
            System.out.println("Main menu audio stopped.");
        }
    }

    public static void playOnHoverButton() { // 1 sec
        System.out.println("button hovered.mp3");

        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("buttonHovered.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnClickButton() { // 1 sec
        System.out.println("button clicked.mp3");

        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("selectMenu.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnPlayeScene() {
        System.out.println("Play Scene.mp3");

        stopMainMenuAudio(); // Ensure any existing audio is stopped before starting new one

        if(inGameThread != null) {
        	stopInGameAudio();
        }
        inGameThread = new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("playscene.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                inGamePlayer = new AdvancedPlayer(bufferedIn);
                isInGamePlaying = true;
                inGamePlayer.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            } finally {
                isInGamePlaying = false;
            }
        });

        inGameThread.start();
    }
    
    private static void stopInGameAudio() {
    	System.out.println("Stoping ingame");
    	
        if (isInGamePlaying && inGamePlayer != null) {
            inGameThread.interrupt(); // Interrupt the main menu thread
            // Closing AdvancedPlayer to stop it
            try {
                inGamePlayer.close();
            } catch (Exception e) {
                System.out.println("Error stopping main menu player: " + e.getMessage());
            }
            
            isInGamePlaying = false;
            System.out.println("Main menu audio stopped.");
        }
    }

    // Additional sound methods
    public static void playOnRollRoullet() {
        System.out.println("Roulet Roll.mp3");
        stopMainMenuAudio(); // Ensure any existing audio is stopped before starting new one
        
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("rouletSound.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();
                
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void playOnStopRoullet() {
        System.out.println("Roullet Stop.mp3");
    }

    public static void playOnTicTacToeBoxAppeared() {
        System.out.println("Box Appeared.mp3");
    }

    public static void playOnTicTacToeBoxHovered() {
        System.out.println("Box Hovered.mp3");
        
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("buttonHovered.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnPlayerCardRased() {
        System.out.println("Card Raised.mp3");
    }

    public static void playOnSymbolPlaced() {
        System.out.println("Symbol Placed.mp3");
        
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("selectMenu.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnLineDashed() {
        System.out.println("Line Dashed.mp3");
    }

    public static void playOnGainPoints() {
        System.out.println("Gain Points.mp3");
    }

    public static void playOnPause() {
        System.out.println("Pause.mp3");
    }

    public static void playOnPlayerWins() {
        System.out.println("Player Wins.mp3");
        stopInGameAudio();
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("winnerSound.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnPlayerLoses() {
        System.out.println("Player Lose.mp3");
        stopInGameAudio();
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("gameOver.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void playOnDraw() {
        System.out.println("Draw.mp3");
        stopInGameAudio();
        new Thread(() -> {
            try {
                InputStream audioStream = Sound.class.getResourceAsStream("victorySound.mp3");
                if (audioStream == null) {
                    System.out.println("Audio file not found!");
                    return;
                }

                BufferedInputStream bufferedIn = new BufferedInputStream(audioStream);
                AdvancedPlayer player = new AdvancedPlayer(bufferedIn);
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public static void playOnSwapCards() {
    	
    }
    
    public static void playOnFlipCard() {
    	
    }
    
    public static void playOnExplode() {
    	
    }
}