package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class UnfreezeDto extends Dto{
    public UnfreezeDto() {
        super(TypeGame.unfreeze.ordinal() + 200);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
