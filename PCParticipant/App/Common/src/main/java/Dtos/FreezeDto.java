package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class FreezeDto extends Dto{

    public FreezeDto() {
        super(TypeGame.freeze.ordinal() + 200);
    }
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
