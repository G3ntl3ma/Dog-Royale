/**@author: leisen
 * Diese Klasse enthält Informationen zu den einzelnen Spielen.
 * Momentan enthält sie nur Infos wichtig für die Anzeige in Spectate Running Games
 */
package com.example.myapplication;

public class Game {
    private String gameID;
    private int timeSeconds;
    private int currentPlayers;
    private int maxPlayers;
    private int fieldSize;
    private int figureCount;
    private int[] drawFields;
    private int[] startFields;

    public Game(String gameID, int timeSeconds, int currentPlayers, int maxPlayers, int fieldSize, int figureCount, int[] drawFields, int[] startFields) {
        this.gameID = gameID;
        this.timeSeconds = timeSeconds;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.fieldSize = fieldSize;
        this.figureCount = figureCount;
        this.drawFields = drawFields;
        this.startFields = startFields;
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

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public int getFigureCount() {
        return figureCount;
    }

    public void setFigureCount(int figureCount) {
        this.figureCount = figureCount;
    }

    public int[] getDrawFields() {
        return drawFields;
    }

    public void setDrawFields(int[] drawFields) {
        this.drawFields = drawFields;
    }

    public int[] getStartFields() {
        return startFields;
    }

    public void setStartFields(int[] startFields) {
        this.startFields = startFields;
    }
}
