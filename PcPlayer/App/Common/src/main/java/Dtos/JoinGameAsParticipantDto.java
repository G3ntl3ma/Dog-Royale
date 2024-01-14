package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class JoinGameAsParticipantDto extends Dto {
    public final int type = TypeMenue.joinGameAsParticipant.ordinal() + 100;
    private int gameId;
    private int clientId;
    private String playerName;

    public JoinGameAsParticipantDto(int gameId, int clientId,String playerName){
        this.gameId = gameId;
        this.clientId = clientId;
        this.playerName = playerName;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
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