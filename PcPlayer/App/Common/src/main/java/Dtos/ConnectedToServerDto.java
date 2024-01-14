package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectedToServerDto extends Dto {
    public final int type = TypeMenue.connectedToServer.ordinal() + 100;
    private int clientId;

    public ConnectedToServerDto(int id){
        this.clientId = id;
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

    public void setClientId(int id) {
        this.clientId = id;
    }

}

