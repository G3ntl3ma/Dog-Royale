package Dtos;

import Dtos.CustomClasses.PlayerName;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class RegisteredForTournamentDto extends Dto {
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private ArrayList<PlayerName> players;
    private int maxGames;

    public RegisteredForTournamentDto(boolean success, int tournamentId, int maxPlayer, ArrayList<PlayerName> players ,int maxGames) {
        super(TypeMenue.registeredForTournament.ordinal() + 100);
        this.success = success;
        this.tournamentId = tournamentId;
        this.maxPlayer = maxPlayer;
        this.players = players;
        this.maxGames = maxGames;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public ArrayList<PlayerName> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerName> players) {
        this.players = players;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(int maxGames) {
        this.maxGames = maxGames;
    }
}