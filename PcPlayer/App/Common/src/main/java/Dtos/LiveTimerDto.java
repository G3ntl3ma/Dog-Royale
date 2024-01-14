package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class LiveTimerDto extends Dto {
    public final int type = TypeGame.liveTimer.ordinal() + 200;
    private int liveTime;

    public LiveTimerDto(int liveTime) {
        this.liveTime = liveTime;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }
}
