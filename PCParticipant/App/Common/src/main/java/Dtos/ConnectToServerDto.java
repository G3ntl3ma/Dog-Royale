package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class ConnectToServerDto extends Dto {
    private String name;
    private boolean isObserver;

    public ConnectToServerDto(String name, boolean isObserver){
        super(TypeMenue.connectToServer.ordinal() + 100);
        this.name = name;
        this.isObserver = isObserver;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
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
