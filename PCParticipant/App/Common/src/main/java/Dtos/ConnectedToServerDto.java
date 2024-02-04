package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectedToServerDto extends Dto {
    private int clientId;

    public ConnectedToServerDto(int id){
        super(TypeMenue.connectedToServer.ordinal() + 100);
        this.clientId = id;
    }

    public ConnectedToServerDto(){
        super(TypeMenue.connectedToServer.ordinal() + 100);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

}

