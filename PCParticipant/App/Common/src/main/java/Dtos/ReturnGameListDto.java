package Dtos;

import Dtos.CustomClasses.GamesFinished;
import Dtos.CustomClasses.GamesProgressing;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ReturnGameListDto extends Dto {
    public final int type = TypeMenue.returnGameList.ordinal() + 100;
    private ArrayList<GamesProgressing> gamesUpcoming;
    private ArrayList<GamesProgressing> gamesRunning;
    private ArrayList<GamesFinished> gamesFinished;

    public ReturnGameListDto(ArrayList<GamesProgressing> gamesUpcoming, ArrayList<GamesProgressing> GamesProgressing,
                             ArrayList<GamesFinished> gamesFinished) {
        this.gamesUpcoming = gamesUpcoming;
        this.gamesRunning = GamesProgressing;
        this.gamesFinished = gamesFinished;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public ArrayList<GamesProgressing> getStartingGame() {
        return gamesUpcoming;
    }

    public void setStartingGame(ArrayList<GamesProgressing> startingGames) {
        this.gamesUpcoming = startingGames;
    }

    public ArrayList<GamesProgressing> getRunningGames() {
        return gamesRunning;
    }

    public void setRunningGames(ArrayList<GamesProgressing> GamesProgressings) {
        this.gamesRunning = GamesProgressings;
    }

    public ArrayList<GamesFinished> getFinishedGames() {
        return gamesFinished;
    }

    public void setFinishedGames(ArrayList<GamesFinished> gamesFinishedGames) {
        this.gamesFinished = gamesFinishedGames;
    }

}