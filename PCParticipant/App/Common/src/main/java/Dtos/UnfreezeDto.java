package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class UnfreezeDto extends Dto{
    public final int type = TypeGame.unfreeze.ordinal() + 200;

    public UnfreezeDto() {}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }
}
