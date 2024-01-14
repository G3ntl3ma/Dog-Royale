package Enums;

public enum GameState {
    paused("pausiert"),
    running("laufend"),
    canceled("abgebrochen"),
    over("beendet");

    public final String state;

    GameState(String state){
        this.state = state;
    }
}
