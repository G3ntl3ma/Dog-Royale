package Enums;

public enum TournamentState {
    planned("geplant"),
    running("laufend"),
    over("beendet");

    public final String state;
    TournamentState(String state){
        this.state = state;
    }
}
