package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class JoinGameAsPlayerDto extends Dto {
    private int gameId;
    private int clientId;
    private String playerName;

    public JoinGameAsPlayerDto(int gameId, int clientId, String playerName){
        super(TypeMenue.joinGameAsPlayer.ordinal() + 100);
        this.gameId = gameId;
        this.clientId = clientId;
        this.playerName = playerName;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int id) {
        this.gameId = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

}