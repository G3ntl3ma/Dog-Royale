package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LeavePlayerDto extends Dto {
    public final int type = TypeGame.leavePlayer.ordinal() + 200;

    public LeavePlayerDto() {}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

}
