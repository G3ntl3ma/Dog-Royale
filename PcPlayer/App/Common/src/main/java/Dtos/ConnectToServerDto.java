package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectToServerDto extends Dto {
    public final int type = TypeMenue.connectToServer.ordinal() + 100;
    private String name;
    private boolean isObserver;

    public ConnectToServerDto(String name, boolean isObserver){
        this.name = name;
        this.isObserver = isObserver;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isObserver() {
        return isObserver;
    }

    public void setObserver(boolean observer) {
        isObserver = observer;
    }

}
