package Dtos;

import Dtos.CustomClasses.PlayerName;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class RegisteredForTournamentDto extends Dto {
    public final int type = TypeMenue.registeredForTournament.ordinal() + 100;
    private boolean success;
    private int tournamentId;
    private int maxPlayer;
    private ArrayList<PlayerName> players;
    private int rounds;

    public RegisteredForTournamentDto(boolean success, int tournamentId, int maxPlayer, ArrayList<PlayerName> players ,int rounds) {
        this.success = success;
        this.tournamentId = tournamentId;
        this.maxPlayer = maxPlayer;
        this.players = players;
        this.rounds = rounds;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
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

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}