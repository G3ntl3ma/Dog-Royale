package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LeaveObsDto extends Dto {
    public final int type = TypeGame.leaveObs.ordinal() + 200;

    public LeaveObsDto() {}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

}
