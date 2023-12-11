package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.messages.menu.ReturnGameList;

import java.util.ArrayList;
import java.util.List;

public class StartingGamesViewmodel extends ViewModel {
    MutableLiveData<List<ReturnGameList.StartingGame>> gamesList = new MutableLiveData<>();

    /**
     * Set a List of StartingGames to be displayed in the Starting Games Fragment
     * @param gamesList
     */
    public void setGamesList(List<ReturnGameList.StartingGame> gamesList) {
        this.gamesList.setValue(gamesList);
    }

    public MutableLiveData<List<ReturnGameList.StartingGame>> getGamesList() {
       //UNCOMMENT FOR TESTING
        /*List<ReturnGameList.StartingGame> games = new ArrayList<>();
        games.add(new ReturnGameList.StartingGame(1234, 6, 7));
        games.add(new ReturnGameList.StartingGame(5678, 3,4));
        games.add(new ReturnGameList.StartingGame(9876, 1,4));
        this.gamesList.setValue(games); */
        return gamesList;
    }
}
