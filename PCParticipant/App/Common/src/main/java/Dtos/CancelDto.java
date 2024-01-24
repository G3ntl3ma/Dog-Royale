package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class CancelDto extends Dto {

    public final int type = TypeGame.cancel.ordinal() + 200;
    private int[] winnerOrder;

    public CancelDto(int[] winnerOrder) {
        this.winnerOrder = winnerOrder;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public int[] getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(int[] winnerOrder) {
        this.winnerOrder = winnerOrder;
    }
}
