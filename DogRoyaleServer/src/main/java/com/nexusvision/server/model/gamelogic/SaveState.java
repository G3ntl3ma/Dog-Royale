package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.FieldType;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.service.CardService;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
public final class SaveState {
    public final class FigureState {
        private Field field;
        private boolean isOnBench;
        private boolean isInHouse;
        
        public FigureState(Figure figure) {
            this.field = figure.getField();
            this.isOnBench = figure.isOnBench();
            this.isInHouse = figure.isInHouse();
        }

        public void loadField(Figure figure) {
            figure.setField(this.field);
            figure.setOnBench(this.isOnBench);
            figure.setInHouse(this.isInHouse);
        }
    }
    
    public final class FieldState {
        private int fieldId;
        private FieldType type;
        private Figure figure;
        
        public FieldState(Field field) {
            this.figure = field.getFigure();
        }

        public void loadField(Field field) {
            field.setFigure(this.figure);
        }
    }

    public final class PlayerState {
        private boolean outThisRound;
        private int figuresInBank;
        private int figuresInHouse;
        private int lastMoveCountFigureMovedIntoHouse;
        private ArrayList<Card> cardList;
        private ArrayList<FigureState> figureList;

        public PlayerState(Player player) {
            this.outThisRound = player.isOutThisRound();
            this.figuresInBank = player.getFiguresInBank();
            this.figuresInHouse = player.getFiguresInHouse();
            this.lastMoveCountFigureMovedIntoHouse = player.getLastMoveCountFigureMovedIntoHouse();
            this.cardList = new ArrayList<>(player.getCardList());
            this.figureList = new ArrayList<>();
            for(Figure figure : player.getFigureList()) {
                figureList.add(new FigureState(figure));
            }
        }

        public void loadPlayer(Player player) {
            player.setOutThisRound(this.outThisRound);
            player.setFiguresInBank(this.figuresInBank);
            player.setFiguresInHouse(this.figuresInHouse);
            player.setLastMoveCountFigureMovedIntoHouse(this.lastMoveCountFigureMovedIntoHouse);
            player.setCardList(new ArrayList<>(this.cardList));
            assert player.getFigureList().size() == this.figureList.size() : "FigureLists are not same size";
            for (int i = 0; i < player.getFigureList().size(); i++) {
                FigureState figureState = this.figureList.get(i);
                Figure figure = player.getFigureList().get(i);
                figureState.loadField(figure);
            }
        }
    }
    
    private int mainFieldCount;
    private int playerToStartColor;
    private int playerToMoveId;
    private int movesMade;
    private int playersRemaining;
    private int round;
    private boolean firstMoveOfRound;

    private FieldState[] board;
    private ArrayList<PlayerState> playerList;
    private ArrayList<Card> deck;
    private ArrayList<Card> pile;

    public SaveState(Game game) {
        // this.mainFieldCount = game.getMainFieldCount();
        this.playerToStartColor = game.getPlayerToStartColor();
        this.playerToMoveId = game.getPlayerToMoveId();
        this.movesMade = game.getMovesMade();
        this.playersRemaining = game.getPlayersRemaining();
        this.round = game.getRound();
        this.firstMoveOfRound = game.isFirstMoveOfRound();

        this.deck = new ArrayList<>(game.getDeck());
        this.pile = new ArrayList<>(game.getPile());
        
        this.board = new FieldState[game.getBoard().length];
        //for (Field field : game.getBoard()) {
        for (int i = 0; i < game.getBoard().length; i++) {
            Field field = game.getBoard()[i];
            this.board[i] = new FieldState(field);
        }
        this.playerList = new ArrayList<>();
        for (Player player : game.getPlayerList()) {
            this.playerList.add(new PlayerState(player));
        }
    }        

    public void loadState(Game game){
        // game.setMainFieldCount(this.mainFieldCount);
        game.setPlayerToStartColor(this.playerToStartColor);
        game.setPlayerToMoveId(this.playerToMoveId);
        game.setMovesMade(this.movesMade);
        game.setPlayersRemaining(this.playersRemaining);
        game.setRound(this.round);
        game.setFirstMoveOfRound(this.firstMoveOfRound);

        game.setDeck(new ArrayList<>(this.getDeck()));
        game.setPile(new ArrayList<>(this.getPile()));

        assert game.getBoard().length == this.board.length : "Boards are not same size";
        for (int i = 0; i < game.getBoard().length; i++) {
            this.board[i].loadField(game.getBoard()[i]);
        }

        assert game.getPlayerList().size() == this.playerList.size() : "Playerlists are not same size";
        for (int i = 0; i < game.getPlayerList().size(); i++) {
            this.playerList.get(i).loadPlayer(game.getPlayerList().get(i));
        }
    }
        
}
