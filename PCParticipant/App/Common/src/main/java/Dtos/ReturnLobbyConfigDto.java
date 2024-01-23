package Dtos;

import Dtos.CustomClasses.*;
import Enums.OrderType;
import Enums.TypeMenue;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReturnLobbyConfigDto extends Dto {
    public final int type = TypeMenue.returnLobbyConfig.ordinal() + 100;
    private String gameName;
    private int maxPlayerCount;
    private int fieldsize;
    private int figuresPerPlayer;
    private ArrayList<PlayerColor> colors;
    private Field drawCardFields;
    private Field startFields;
    private int initialCardsPerPlayer;
    private PlayerOrder playerOrder;



    private ArrayList<PlayerPoints> winnerOrder;
    private ArrayList<PlayerName> observer;
    private int thinkTimePerMove;
    private int visualizationTimePerMove;
    private int consequencesForInvalidMove;
    private int maximumGameDuration;
    private int maximumTotalMoves;

    public ReturnLobbyConfigDto(String gameName, int maxPlayerCount, int fieldsize, int figuresPerPlayer, ArrayList<PlayerColor> colors,
                                Field drawCardFields, Field startFields, int initialCardsPerPlayer, PlayerOrder playerOrder, ArrayList<PlayerPoints> winnerOrder,
                                ArrayList<PlayerName> observer, int thinkTimePerMove, int visualizationTimePerMove,
                                int consequencesForInvalidMove, int maximumGameDuration, int maximumTotalMoves) {
        this.gameName = gameName;
        this.maxPlayerCount = maxPlayerCount;
        this.fieldsize = fieldsize;
        this.figuresPerPlayer = figuresPerPlayer;
        this.colors = colors;
        this.drawCardFields = drawCardFields;
        this.startFields = startFields;
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        this.playerOrder = playerOrder;
        this.winnerOrder = winnerOrder;
        this.observer = observer;
        this.thinkTimePerMove = thinkTimePerMove;
        this.visualizationTimePerMove = visualizationTimePerMove;
        this.consequencesForInvalidMove = consequencesForInvalidMove;
        this.maximumGameDuration = maximumGameDuration;
        this.maximumTotalMoves = maximumTotalMoves;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getType() {
        return type;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    public int getFieldsize() {
        return fieldsize;
    }

    public void setFieldsize(int fieldsize) {
        this.fieldsize = fieldsize;
    }

    public int getFiguresPerPlayer() {
        return figuresPerPlayer;
    }

    public void setFiguresPerPlayer(int figuresPerPlayer) {
        this.figuresPerPlayer = figuresPerPlayer;
    }

    public ArrayList<PlayerColor> getColors() {
        return colors;
    }

    public void setColors(ArrayList<PlayerColor> colors) {
        this.colors = colors;
    }

    public Field getDrawCardFields() {
        return drawCardFields;
    }

    public void setDrawCardFields(Field drawCardFields) {
        this.drawCardFields = drawCardFields;
    }

    public void setStartFields(Field startFields) {
        this.startFields = startFields;
    }

    public Field getStartFields() {
        return startFields;
    }

    public int getInitialCardsPerPlayer() {
        return initialCardsPerPlayer;
    }

    public void setInitialCardsPerPlayer(int initialCardsPerPlayer) {
        this.initialCardsPerPlayer = initialCardsPerPlayer;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(PlayerOrder playerOrder) {
        this.playerOrder = playerOrder;
    }
    public ArrayList<PlayerPoints> getWinnerOrder() {
        return winnerOrder;
    }

    public void setWinnerOrder(ArrayList<PlayerPoints> winnerOrder) {
        this.winnerOrder = winnerOrder;
    }
    public ArrayList<PlayerName> getObserver() {
        return observer;
    }

    public void setObserver(ArrayList<PlayerName> observer) {
        this.observer = observer;
    }

    public int getThinkTimePerMove() {
        return thinkTimePerMove;
    }

    public void setThinkTimePerMove(int thinkTimePerMove) {
        this.thinkTimePerMove = thinkTimePerMove;
    }

    public int getVisualizationTimePerMove() {
        return visualizationTimePerMove;
    }

    public void setVisualizationTimePerMove(int visualizationTimePerMove) {
        this.visualizationTimePerMove = visualizationTimePerMove;
    }

    public int getConsequencesForInvalidMove() {
        return consequencesForInvalidMove;
    }

    public void setConsequencesForInvalidMove(int consequencesForInvalidMove) {
        this.consequencesForInvalidMove = consequencesForInvalidMove;
    }

    public int getMaximumGameDuration() {
        return maximumGameDuration;
    }

    public void setMaximumGameDuration(int maximumGameDuration) {
        this.maximumGameDuration = maximumGameDuration;
    }

    public int getMaximumTotalMoves() {
        return maximumTotalMoves;
    }

    public void setMaximumTotalMoves(int maximumTotalMoves) {
        this.maximumTotalMoves = maximumTotalMoves;
    }
}