package Dtos;

import com.google.gson.Gson;

public class DisconnectDto extends Dto {

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
