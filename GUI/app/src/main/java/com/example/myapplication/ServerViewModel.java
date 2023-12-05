package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServerViewModel extends ViewModel {

    List<Game> gamesList = new ArrayList<Game>();
    MutableLiveData<List<Game>> games = new MutableLiveData<>();
    boolean setup = false;


    public void addGame(Game game){
        gamesList.add(game);
        games.setValue(gamesList);
    }
    public MutableLiveData<List<Game>> getGames(){
        games.setValue(gamesList);
        return games;
    }

    public void removeGame(int pos){
        gamesList.remove(pos);
        games.setValue(gamesList);
    }

    public void changeCurrentPlayers(int pos, int players){
        gamesList.get(pos).setCurrentPlayers(players);
        games.setValue(gamesList);
    }

}
