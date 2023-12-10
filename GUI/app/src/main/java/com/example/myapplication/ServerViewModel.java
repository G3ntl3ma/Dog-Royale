package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.messages.menu.ReturnGameList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerViewModel extends ViewModel {

    List<Game> gamesList = new ArrayList<Game>();
    MutableLiveData<List<Game>> games = new MutableLiveData<>();

    MutableLiveData<List<Game>> spectateGames = new MutableLiveData<>(new ArrayList<Game>());
    MutableLiveData<List<Game>> runningGames = new MutableLiveData<>(new ArrayList<Game>());

    MutableLiveData<List<MatchHistory>> matchHistory = new MutableLiveData<>(new ArrayList<MatchHistory>());
    boolean setup = false;

    public void setMatchHistory(List<ReturnGameList.FinishedGame> matchHistory){
        for (ReturnGameList.FinishedGame match: matchHistory) {
            addMatchHistory(new MatchHistory(match.getGameId(), match.getWinnerPlayerId()));
        }
    }

    public LiveData<List<MatchHistory>> getMatchHistory(){

        //TODO: remove this
        this.matchHistory.setValue(new ArrayList<MatchHistory> (Arrays.asList(new MatchHistory(1, 1), new MatchHistory(2, 2)) ));
        return matchHistory;
    }

    public void addMatchHistory(MatchHistory matchHistory){
        List<MatchHistory> matchHistoryList = this.matchHistory.getValue();
        matchHistoryList.add(matchHistory);
        this.matchHistory.setValue(matchHistoryList);
    }

    public void removeMatchHistory(int pos){
        List<MatchHistory> matchHistoryList = this.matchHistory.getValue();
        matchHistoryList.remove(pos);
        this.matchHistory.setValue(matchHistoryList);
    }

    public void addMatchHistoryList(List<ReturnGameList.FinishedGame> matchHistory){
        for (ReturnGameList.FinishedGame match: matchHistory) {
            addMatchHistory(new MatchHistory(match.getGameId(), match.getWinnerPlayerId()));
        }
    }

    public void addRunningGameList(List<ReturnGameList.RunningGame> gameList){
        for (ReturnGameList.RunningGame game: gameList) {
            addRunningGame(new Game(game.getGameId(), 0,  game.getCurrentPlayerCount(), game.getMaxPlayerCount(), 100, 10,  new int[]{0}, new int []{0}));
        }
    }

    public void addRunningGame(Game game){
        List<Game> gamesRunning  = runningGames.getValue();
        gamesRunning.add(game);
        runningGames.setValue(gamesRunning);
    }

    public void addSpectateGamesList(List<ReturnGameList.StartingGame> gameList){
        for (ReturnGameList.StartingGame game: gameList) {
            addRunningGame(new Game(game.getGameId(), 0,  game.getCurrentPlayerCount(), game.getMaxPlayerCount(), 100, 10,  new int[]{0}, new int []{0}));
        }
    }
    public void addSpectateGame(Game game){
        List<Game> gamesSpectating  = spectateGames.getValue();
        gamesSpectating.add(game);
        spectateGames.setValue(gamesSpectating);
    }

    public MutableLiveData<List<Game>> getSpectateGames(){
        return spectateGames;
    }

    public void removeSpectateGame(int pos){
        List<Game> gamesSpectating  = spectateGames.getValue();
        gamesSpectating.remove(pos);
        spectateGames.setValue(gamesSpectating);
    }

    public MutableLiveData<List<Game>> getRunningGames(){
        return runningGames;
    }

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


    public class MatchHistory
    {
        private int gameId;
        private int winner;
        public MatchHistory(int gameId, int winner)
        {
            this.gameId = gameId;
            this.winner = winner;
        }

        public int getGameId()
        {
            return gameId;
        }


        public int getWinner()
        {
            return winner;
        }


    }
}
