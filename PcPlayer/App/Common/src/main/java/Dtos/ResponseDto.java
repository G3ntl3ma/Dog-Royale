package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class ResponseDto extends Dto {

    public final int type = TypeGame.response.ordinal() + 200;
    private final boolean updated = true;

    public ResponseDto() {}

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public boolean isUpdated() {
        return updated;
    }

}
