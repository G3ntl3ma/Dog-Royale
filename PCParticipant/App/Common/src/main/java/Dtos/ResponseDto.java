package Dtos;

import Enums.TypeGame;
import com.google.gson.Gson;

public class ResponseDto extends Dto {
    private final boolean updated = true;

    public ResponseDto() {
        super(TypeGame.response.ordinal() + 200);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isUpdated() {
        return updated;
    }

}
