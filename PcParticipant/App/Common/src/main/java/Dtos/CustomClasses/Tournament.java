package Dtos.CustomClasses;

public abstract class Tournament {
    // abstarct Class Tournament, wich includes attributes and functions, that all inheritors have in common
    protected int tournamentId;

    public Tournament(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }
}
