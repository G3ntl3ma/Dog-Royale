package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LiveTimerDto extends Dto {
    private int liveTime;

    public LiveTimerDto(int liveTime) {
        super(TypeGame.liveTimer.ordinal() + 200);
        this.liveTime = liveTime;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }
}
