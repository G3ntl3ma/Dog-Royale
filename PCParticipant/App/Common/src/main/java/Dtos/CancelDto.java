package Dtos;

import Dtos.CustomClasses.PlayerPoints;
import Enums.TypeGame;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CancelDto extends Dto {

    private ArrayList<PlayerPoints> winnerOrder;

    public CancelDto(ArrayList<PlayerPoints> winnerOrder) {
        super(TypeGame.cancel.ordinal() + 200);
        this.winnerOrder = winnerOrder;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }
}
