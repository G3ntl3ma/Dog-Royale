package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class JoinObsDto extends Dto{
    public final int type = TypeGame.joinObs.ordinal() + 200;
    private int countObs;

    public JoinObsDto(int countObs) {
        this.countObs = countObs;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int getCountObs() {
        return countObs;
    }

    public void setCountObs(int countObs) {
        this.countObs = countObs;
    }
}
