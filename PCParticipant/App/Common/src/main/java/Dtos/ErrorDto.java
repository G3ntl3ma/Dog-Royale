package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ErrorDto extends Dto  {
    public final int type = TypeMenue.error.ordinal() + 100;
    private int dataID;
    private String message;

    public ErrorDto(int dataID, String message){
        this.dataID = dataID;
        this.message = message;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getDataID() {
        return dataID;
    }

    public void setDataID(int dataID) {
        this.dataID = dataID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
