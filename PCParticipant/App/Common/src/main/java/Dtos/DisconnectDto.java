package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class DisconnectDto extends Dto {
    public DisconnectDto() {
        super(TypeMenue.disconnect.ordinal() + 100);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
