package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class JoinObsDto extends Dto{
    private int countObs;

    public JoinObsDto(int countObs) {
        super(TypeGame.joinObs.ordinal() + 200);
        this.countObs = countObs;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getCountObs() {
        return countObs;
    }

    public void setCountObs(int countObs) {
        this.countObs = countObs;
    }
}
