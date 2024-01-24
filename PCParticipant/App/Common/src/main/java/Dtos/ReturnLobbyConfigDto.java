package Dtos;

import Dtos.CustomClasses.PlayerColor;
import Dtos.CustomClasses.PlayerName;
import Dtos.CustomClasses.PlayerOrder;
import Enums.OrderType;
import Enums.TypeMenue;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReturnLobbyConfigDto extends Dto {
    public final int type = TypeMenue.returnLobbyConfig.ordinal() + 100;
    private String name;
    private String date;
    private int playerCount;
    private int fieldsize;
    private int figuresPerPlayer;
    private ArrayList<PlayerColor> colors;
    private FieldsDto drawCardFields;
    private FieldsDto startFields;
    private int initialCardsPerPlayer;
    private PlayerOrder playerOrder;
    private ArrayList<PlayerName> observer;
    private int thinkTimePerMove;
    private int visualizationTimePerMove;
    private int consequencesForInvalidMove;
    private int maximumGameDuration;
    private int maximumTotalMoves;

    public ReturnLobbyConfigDto(String name, String date, int playerCount, int fieldsize, int figuresPerPlayer, ArrayList<PlayerColor> colors,
                                FieldsDto drawCardFields, FieldsDto startFields, int initialCardsPerPlayer, PlayerOrder playerOrder,
                                ArrayList<PlayerName> observer, int thinkTimePerMove, int visualizationTimePerMove,
                                int consequencesForInvalidMove, int maximumGameDuration, int maximumTotalMoves) {
        this.name = name;
        this.date = date;
        this.playerCount = playerCount;
        this.fieldsize = fieldsize;
        this.figuresPerPlayer = figuresPerPlayer;
        this.colors = colors;
        this.drawCardFields = drawCardFields;
        this.startFields = startFields;
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        this.playerOrder = playerOrder;
        this.observer = observer;
        this.thinkTimePerMove = thinkTimePerMove;
        this.visualizationTimePerMove = visualizationTimePerMove;
        this.consequencesForInvalidMove = consequencesForInvalidMove;
        this.maximumGameDuration = maximumGameDuration;
        this.maximumTotalMoves = maximumTotalMoves;
    }

    public ReturnLobbyConfigDto(String name, String date, int playerCount, int fieldsize, int figuresPerPlayer, ArrayList<PlayerColor> colors,
                                FieldsDto drawCardFields, FieldsDto startFields, int initialCardsPerPlayer, int thinkTimePerMove, int visualizationTimePerMove,
                                int consequencesForInvalidMove, int maximumGameDuration, int maximumTotalMoves) {
        this(name, date, playerCount, fieldsize, figuresPerPlayer, colors, drawCardFields, startFields, initialCardsPerPlayer, new PlayerOrder(OrderType.fixed.ordinal(), new ArrayList<PlayerName>()), new ArrayList<PlayerName>(), thinkTimePerMove, visualizationTimePerMove, consequencesForInvalidMove, maximumGameDuration, maximumTotalMoves);
    }


    public ReturnLobbyConfigDto() {
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
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

    public FieldsDto getDrawCardFields() {
        return drawCardFields;
    }

    public void setDrawCardFields(FieldsDto drawCardFields) {
        this.drawCardFields = drawCardFields;
    }

    public void setStartFields(FieldsDto drawCardFields) {
        this.drawCardFields = drawCardFields;
    }

    public FieldsDto getStartFields() {
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