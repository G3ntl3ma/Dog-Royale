package Dtos;

import Dtos.CustomClasses.FinishedGames;
import Dtos.CustomClasses.RunningGame;
import Enums.TypeMenue;
import com.google.gson.Gson;
import java.util.ArrayList;

public class ReturnGameListDto extends Dto {
    public final int type = TypeMenue.returnGameList.ordinal() + 100;
    private ArrayList<RunningGame> startingGames;
    private ArrayList<RunningGame> runningGames;
    private ArrayList<FinishedGames> finishedGames;

    public ReturnGameListDto(ArrayList<RunningGame> startingGames, ArrayList<RunningGame> runningGames,
                             ArrayList<FinishedGames> finishedGames) {
        this.startingGames = startingGames;
        this.runningGames = runningGames;
        this.finishedGames = finishedGames;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return type;
    }

    public ArrayList<RunningGame> getStartingGame() {
        return startingGames;
    }

    public void setStartingGame(ArrayList<RunningGame> startingGames) {
        this.startingGames = startingGames;
    }

    public ArrayList<RunningGame> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(ArrayList<RunningGame> runningGames) {
        this.runningGames = runningGames;
    }

    public ArrayList<FinishedGames> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(ArrayList<FinishedGames> finishedGames) {
        this.finishedGames = finishedGames;
    }

}
