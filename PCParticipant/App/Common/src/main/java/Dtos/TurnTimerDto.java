package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class TurnTimerDto extends Dto{
    private int turnTime;

    public TurnTimerDto(int turnTime) {
        super(TypeGame.turnTimer.ordinal() + 200);
        this.turnTime = turnTime;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(int turnTime) {
        this.turnTime = turnTime;
    }
}
