package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectedToGameDto extends Dto {
    private boolean success;

    public ConnectedToGameDto(boolean success){
        super(TypeMenue.connectedToGame.ordinal() + 100);
        this.success = success;
    }

    public ConnectedToGameDto(){
        super(TypeMenue.connectedToGame.ordinal() + 100);
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

}

