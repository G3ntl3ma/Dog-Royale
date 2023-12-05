/**@author: leisen
 * Diese Klasse enthält Informationen zu den einzelnen Spielen.
 * Momentan enthält sie nur Infos wichtig für die Anzeige in Spectate Running Games
 */
package com.example.myapplication;

public class Game {
    String gameID;
    int timeSeconds;
    int currentPlayers;
    int maxPlayers;

    public Game(String gameID, int timeSeconds, int currentPlayers, int maxPlayers) {
        this.gameID = gameID;
        this.timeSeconds = timeSeconds;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
