package Dtos;

import Dtos.CustomClasses.GamesFinished;
import Dtos.CustomClasses.GamesProgressing;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ReturnGameListDto extends Dto {
    private ArrayList<GamesProgressing> gamesUpcoming;
    private ArrayList<GamesProgressing> gamesRunning;
    private ArrayList<GamesFinished> gamesFinished;

    public ReturnGameListDto(ArrayList<GamesProgressing> gamesUpcoming, ArrayList<GamesProgressing> gamesRunning,
                             ArrayList<GamesFinished> gamesFinished) {
        super(TypeMenue.returnGameList.ordinal() + 100);
        this.gamesUpcoming = gamesUpcoming;
        this.gamesRunning = gamesRunning;
        this.gamesFinished = gamesFinished;
    }

    public ReturnGameListDto() {
        super(TypeMenue.returnGameList.ordinal() + 100);
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

    public void setRunningGames(ArrayList<GamesProgressing> gamesRunning) {
        this.gamesRunning = gamesRunning;
    }

    public ArrayList<GamesFinished> getFinishedGames() {
        return gamesFinished;
    }

    public void setFinishedGames(ArrayList<GamesFinished> gamesFinishedGames) {
        this.gamesFinished = gamesFinishedGames;
    }

}
