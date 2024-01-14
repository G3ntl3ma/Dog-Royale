package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class KickDto extends Dto {

    public final int type = TypeGame.kick.ordinal() + 200;
    private int clientId;
    private String reason;

    public KickDto(int clientId, String reason) {
        this.clientId = clientId;
        this.reason = reason;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
