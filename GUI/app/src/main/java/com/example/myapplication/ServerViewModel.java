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

    /** Füge ein Spiel zu der SpectateGamesListe hinzu
     *
     * @param game
     */
    public void addGame(Game game){
        gamesList.add(game);
        games.setValue(gamesList);
    }
    public MutableLiveData<List<Game>> getGames(){
        games.setValue(gamesList);
        return games;
    }

    /** Entferne ein Spiel aus der SpectateGames Liste
     * @param pos
     */
    public void removeGame(int pos){
        gamesList.remove(pos);
        games.setValue(gamesList);
    }

    /**
     * Setze die Anzahl der Spieler in einem Spiel neu
     * @param pos Das Spiel, das verändert wird
     * @param players Die neue Anzahl von Spielern
     */
    public void changeCurrentPlayers(int pos, int players){
        gamesList.get(pos).setCurrentPlayers(players);
        games.setValue(gamesList);
    }

}
