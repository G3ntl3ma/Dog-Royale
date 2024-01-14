package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectedToGameDto extends Dto {
    public final int type = TypeMenue.connectedToGame.ordinal() + 100;
    private boolean success;

    public ConnectedToGameDto(boolean success){
        this.success = success;
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

}

