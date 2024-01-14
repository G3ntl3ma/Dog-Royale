package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class FreezeDto extends Dto{
    public final int type = TypeGame.freeze.ordinal() + 200;

    public FreezeDto() {}
    
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }
}
