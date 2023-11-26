package src.main.java.org.example;
import java.util.ArrayList;
import java.util.List;

public class GameBuilder {
    private int id;
    private int mainFieldCount;
    private int figuresPerPlayer;
    private int playerCount;
    private int drawCardFieldCount;
    private int initialCardsPerPlayer;
    private int maximumTotalMoves;
    private int thinkTimePerMove;
    private org.example.Game.Penalty penalty;
    private org.example.Game.OrderType orderType;
    private int maximumGameDuration;
    private int visualizationTimePerMove;

    public GameBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public GameBuilder setMainFieldCount(int mainFieldCount) {
        this.mainFieldCount = mainFieldCount;
        return this;
    }

    public GameBuilder setFiguresPerPlayer(int figuresPerPlayer) {
        this.figuresPerPlayer = figuresPerPlayer;
        return this;
    }
    public GameBuilder setPlayerCount(int playerCount){
        this.playerCount = playerCount;
        return this;
    }

    public GameBuilder setInitialCardsPerPlayer(int initialCardsPerPlayer) {
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        return this;
    }

    public GameBuilder setMaximumTotalMoves(int maximumTotalMoves) {
        this.maximumTotalMoves = maximumTotalMoves;
        return this;
    }

    public GameBuilder setThinkTimePerMove(int thinkTimePerMove) {
        this.thinkTimePerMove = thinkTimePerMove;
        return this;
    }

    public GameBuilder setPenalty(org.example.Game.Penalty penalty) {
        this.penalty = penalty;
        return this;
    }

    public GameBuilder setOrderType(org.example.Game.OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public GameBuilder setMaximumGameDuration(int maximumGameDuration) {
        this.maximumGameDuration = maximumGameDuration;
        return this;
    }
    public GameBuilder setDrawCardFieldCount(int drawCardFieldCount) {
        this.drawCardFieldCount = drawCardFieldCount;
        return this;
    }


    public GameBuilder setVisualizationTimePerMove(int visualizationTimePerMove) {
        this.visualizationTimePerMove = visualizationTimePerMove;
        return this;
    }

    public org.example.Game build() {
        org.example.Game game = new org.example.Game(id, mainFieldCount, figuresPerPlayer,drawCardFieldCount, initialCardsPerPlayer, maximumTotalMoves,
                playerCount,thinkTimePerMove, penalty, orderType, maximumGameDuration, visualizationTimePerMove);

        return game;
    }
}
