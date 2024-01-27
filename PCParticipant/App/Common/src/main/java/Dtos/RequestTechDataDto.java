package Dtos;

import Enums.TypeMenue;
import com.google.gson.Gson;

public class RequestTechDataDto extends Dto {

    public RequestTechDataDto(){
        super(TypeMenue.requestTechData.ordinal() + 100);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

