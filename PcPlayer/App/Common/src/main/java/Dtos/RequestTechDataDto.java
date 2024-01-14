package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestTechDataDto extends Dto {
    public final int type = TypeMenue.requestTechData.ordinal() + 100;

    public RequestTechDataDto(){
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

}

