package app.bowling.bowlingapp.bowling.core.utils;

public class BowlingAppState {

    private static BowlingAppState bowlingAppState;

    public static final String GAMES = "games";
    public static final String GAME_SCREEN = "game_screen";

    private String currentScreen = "";

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(String currentScreen) {
        this.currentScreen = currentScreen;
    }

    public static BowlingAppState getInstance(){
        /**
         * Only need this created once
         * **/

        if (bowlingAppState == null) {
            synchronized (BowlingAppState.class) {
                if (bowlingAppState == null) {
                    bowlingAppState = new BowlingAppState();
                }
            }
        }

        return bowlingAppState;
    }
}
