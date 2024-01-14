package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class TurnTimerDto extends Dto{
    public final int type = TypeGame.turnTimer.ordinal() + 200;
    private int turnTime;

    public TurnTimerDto(int turnTime) {
        this.turnTime = turnTime;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }
}
