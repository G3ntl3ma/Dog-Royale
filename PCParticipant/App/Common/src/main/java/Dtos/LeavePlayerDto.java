package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LeavePlayerDto extends Dto {

    public LeavePlayerDto() {
        super(TypeGame.leavePlayer.ordinal() + 200);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
