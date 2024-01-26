package com.nexusvision.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.common.Publisher;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.LobbyConfig;
import com.nexusvision.server.model.gamelogic.Move;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.Error;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.model.utils.*;
import com.nexusvision.server.service.BoardStateService;
import com.nexusvision.server.service.KickService;
import com.nexusvision.server.service.UpdateDrawCardsService;
import com.nexusvision.utils.NewLineAppendingSerializer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The GameLobby represents a lobby and handles the management of players, observers and game-related information
 *
 * @author dgehse, felixwr
 */
@Log4j2
public class GameLobby {

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    @Getter
    private final int id;
    private final ServerController serverController;
    private final BoardStateService boardStateService;
    private final KickService kickService;

    private final Publisher lobbyPublisher;
    private final MessageBroker messageBroker;

    private final ScheduledExecutorService scheduler;
    @Getter
    private final LobbyConfig lobbyConfig;
    private ScheduledFuture<?> liveTimerTask;
    private ScheduledFuture<?> turnTimerTask;
    private int gameDuration; // TODO: Populate properly when starting
    private int turnTime;
    private int liveTimerSendCount;
    private int turnTimerSendCount;

    private Game game; // TODO: Populate
    @Getter
    private GameState gameState;
    @Getter
    private boolean isPaused;
    @Getter
    private boolean canceled;

    private ArrayList<Integer> receivedResponseList = new ArrayList<>();

    /**
     * Creates a GameLobby with the given id
     *
     * @param id The id to create the lobby with
     */
    public GameLobby(int id) {
        this.id = id;
        lobbyConfig = new LobbyConfig();
        this.gameState = GameState.STARTING;
        this.isPaused = false;
        this.serverController = ServerController.getInstance();
        this.boardStateService = new BoardStateService();
        this.kickService = new KickService();

        this.lobbyPublisher = new Publisher(ChannelType.LOBBY, id);
        this.messageBroker = MessageBroker.getInstance();

        this.scheduler = Executors.newScheduledThreadPool(2);
    }

    /**
     * Starts the live timer
     */
    public void startLiveTimer() {
        liveTimerSendCount = 0;
        liveTimerTask = scheduler.scheduleAtFixedRate(this::sendLiveTimer, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(this::countdownLiveTime, 0, TimeUnit.SECONDS);
    }

    /**
     * Stops the live timer
     */
    public void stopLiveTimer() {
        if (liveTimerTask != null && !liveTimerTask.isDone()) {
            liveTimerTask.cancel(true);
        }
    }

    /**
     * Starts the turn timer
     */
    public void startTurnTimer() {
        turnTimerSendCount = 0;
        turnTime = lobbyConfig.getThinkTimePerMove();
        turnTimerTask = scheduler.scheduleAtFixedRate(this::sendTurnTimer, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(this::countdownTurnTimer, 0, TimeUnit.SECONDS);
    }

    /**
     * Stops the turn timer
     */
    public void stopTurnTimer() {
        if (turnTimerTask != null && !turnTimerTask.isDone()) {
            turnTimerTask.cancel(true);
        }
    }

    private void sendLiveTimer() {
        if (gameState == GameState.IN_PROGRESS && !isPaused) {
            LiveTimer liveTimer = new LiveTimer();
            liveTimer.setType(TypeGame.liveTimer.getOrdinal());
            liveTimer.setLiveTime(gameDuration * 1000);
            String liveTimerMessage = gson.toJson(liveTimer, LiveTimer.class);
            if ((liveTimerSendCount = liveTimerSendCount % 5) == 0) {
                lobbyPublisher.publish(liveTimerMessage);
            } else {
                messageBroker.sendMessage(ChannelType.SINGLE, getClientToMoveId(), liveTimerMessage);
            }
            liveTimerSendCount++;
        }
    }

    private void countdownLiveTime() {
        while (gameDuration > 0) {
            if (gameState == GameState.IN_PROGRESS && !isPaused) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Sleeping thread got interrupted: " + e.getMessage());
                }
                gameDuration--;
            }
        }
    }

    private void sendTurnTimer() {
        if (gameState == GameState.IN_PROGRESS && !isPaused) {
            TurnTimer turnTimer = new TurnTimer();
            turnTimer.setType(TypeGame.turnTimer.getOrdinal());
            turnTimer.setTurnTime(turnTime * 1000);
            String turnTimerMessage = gson.toJson(turnTimer, TurnTimer.class);
            if ((turnTimerSendCount = turnTimerSendCount % 5) == 0) {
                lobbyPublisher.publish(turnTimerMessage);
            } else {
                messageBroker.sendMessage(ChannelType.SINGLE, getClientToMoveId(), turnTimerMessage);
            }
            turnTimerSendCount++;
        }
    }

    private void countdownTurnTimer() {
        while (turnTime > 0) {
            if (gameState == GameState.IN_PROGRESS && !isPaused) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Sleeping thread got interrupted: " + e.getMessage());
                }
                turnTime--;
            }
        }
        skipMove();
    }

    private void skipMove() {
        tryMove(0, true, 0, 0, 0, false, 0);
    }

    public Integer getWinnerPlayerId() {
        return game.getWinnerOrder().get(0);
    }

    /**
     * Receives and tracks responses from client
     *
     * @param clientId An Integer representing the Id of the client
     */
    public void receiveResponse(int clientId) {
        for (Integer receivedResponse : this.receivedResponseList) {
            if (clientId == receivedResponse) return;
        }
        this.receivedResponseList.add(clientId);
    }

    /**
     * Resets List of received responses
     */
    public void resetResponseList() {
        this.receivedResponseList = new ArrayList<>();
    }

    /**
     * Checks if it is the turn of the player with a certain clientId
     *
     * @param clientId An Integer representing the ID of the player to check if it is their turn
     * @return A Boolean true if it's the player's turn otherwise false
     */
    public boolean checkPlayerTurn(int clientId) {
        int playerId = getPlayerId(clientId);
        if (playerId != -1) {
            return game.getPlayerToMoveId() == playerId;
        }
        //client not a player
        return false;
    }

    //check if playerOrderList + observerList is subset of received responses

//    public boolean receivedFromEveryone() {
//        for (int idToFind : playerOrderList) {
//            //check if this id is in the list of responses
//            boolean found = false;
//            for (Integer receivedResponse : receivedResponseList) {
//                if (receivedResponse == idToFind) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) return false;
//        }
//        for (int idToFind : observerList) {
//            //check if this id is in the list of responses
//            boolean found = false;
//            for (Integer integer : receivedResponseList) {
//                if (integer == idToFind) {
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) return false;
//        }
//        return true;
//    }

    /**
     * Adds an observer to the lobby
     *
     * @param clientId The client id of the observer being added
     */
    public void addObserver(int clientId) {
        List<ObserverElement> observerList = lobbyConfig.getObserverList();
        String name = serverController.getClientById(clientId).getName();
        observerList.add(new ObserverElement(clientId, name));
        messageBroker.registerSubscriber(clientId, id);

        ConnectedToGame connectedToGame = new ConnectedToGame();
        connectedToGame.setType(TypeMenue.connectedToGame.getOrdinal());
        connectedToGame.setSuccess(true);
        messageBroker.sendMessage(ChannelType.SINGLE, clientId, gson.toJson(connectedToGame));

        JoinObs joinObs = new JoinObs();
        joinObs.setType(TypeGame.joinObs.getOrdinal());
        joinObs.setCountObs(observerList.size());
        lobbyPublisher.publish(gson.toJson(joinObs));
        // If game started already send him boardState
        gson.toJson(connectedToGame);
        if (gameState == GameState.IN_PROGRESS) {
            BoardState boardState = boardStateService.generateBoardState(game, lobbyConfig.getPlayerOrder());
            String boardStateMessage = gson.toJson(boardState);
            lobbyPublisher.publish(boardStateMessage);
        }
    }

    /**
     * Removes an observer from the lobby
     *
     * @param clientId The client id of the observer being removed
     */
    public void removeObserver(int clientId) {
        List<ObserverElement> observerList = lobbyConfig.getObserverList();
        observerList.remove(clientId);
        messageBroker.unregisterSubscriber(clientId, id);

        JoinObs joinObs = new JoinObs();
        joinObs.setType(TypeGame.joinObs.getOrdinal());
        joinObs.setCountObs(observerList.size());
        lobbyPublisher.publish(gson.toJson(joinObs));
    }

    /**
     * Adds a player to the lobby with a random, suiting color
     *
     * @param name     The name of the player to add
     * @param clientId The client id of the player being added
     */
    public void addPlayer(String name, int clientId) {
        Colors color = lobbyConfig.findUnusedColor();
        addPlayer(name, clientId, color);
    }

    /**
     * Adds a player to the lobby
     *
     * @param name     The name of the player to add
     * @param clientId The client id of the player being added
     * @param color    The color of the player being added
     */
    public void addPlayer(String name, int clientId, Colors color) {
        boolean successful = lobbyConfig.addPlayer(name, clientId);
        if (!successful) { // more players added than maxPlayerCount allows or when player exists already
            String errorMessage = String.format("Couldn't add player with name %s and clientId %d", name, clientId);
            sendError(clientId, errorMessage);
            return;
        }
        lobbyConfig.addColor(color, clientId);
        messageBroker.registerSubscriber(clientId, id);
    }

    /**
     * Removes a player from the lobby
     *
     * @param clientId The client id of the player being removed
     */
    public void removePlayer(int clientId) {
        int playerOrderIndex = getPlayerId(clientId);
        switch (gameState) {
            case IN_PROGRESS:
                Player player = game.getPlayerList().get(playerOrderIndex);
                game.removePlayerFromBoard(player);
                game.excludeFromGame(player);
                break;
            case FINISHED:
                return;
        }
        lobbyConfig.removePlayerByOrderIndex(playerOrderIndex);
        kickService.kick(lobbyConfig, clientId, id);
        // TODO: Again - there might be more to remove
        if (lobbyConfig.getPlayerOrder().getOrder().isEmpty()) {
            finishGame();
        }
    }

    /**
     * Sets GameState to <code>IN_PROGRESS</code> and sends board state to all clients
     */
    public void runGame() { // TODO: Check that min 2 players are connected before starting
        game = new Game(lobbyConfig, id);
        gameState = GameState.IN_PROGRESS;
        BoardState boardState = boardStateService.generateBoardState(game, lobbyConfig.getPlayerOrder()); // This is how Ausrichter starts the game. Sending board states to all clients
        String boardStateMessage = gson.toJson(boardState);
        serverController.startGameForLobby(this);
        lobbyPublisher.publish(boardStateMessage);

        game.initDeck();
        setupNewRound();
        startLiveTimer();
    }

    /**
     * Unpauses the game
     */
    public void unpauseGame() {
        isPaused = false;
        Unfreeze unfreeze = new Unfreeze();
        unfreeze.setType(TypeGame.unfreeze.getOrdinal());
        lobbyPublisher.publish(gson.toJson(unfreeze));
    }

    /**
     * Pause the game
     */
    public void pauseGame() {
        isPaused = true;
        Freeze freeze = new Freeze();
        freeze.setType(TypeGame.freeze.getOrdinal());
        lobbyPublisher.publish(gson.toJson(freeze));
    }

    /**
     * Attempts to make a move, also handling illegal moves with consequences.
     * Cleaning up by communicating with the <code>ServerController</code> and
     * sending notifications to all lobby members about the move's validity.
     *
     * @param clientId        The client id that is trying this move
     * @param skip            A Boolean indicating whether to skip the move or not
     * @param card            An Integer representing the card used for the move
     * @param selectedValue   An Integer representing a selected value for the move
     * @param pieceId         An Integer representing the ID of the figure involved in the move
     * @param isStarter       A Boolean indicating if the move is a starter move
     * @param opponentPieceId An Integer representing the ID of the opponents figure
     */
    public synchronized void tryMove(int clientId, boolean skip, int card, int selectedValue,
                                     int pieceId, boolean isStarter, Integer opponentPieceId) {
        stopTurnTimer();
        boolean isValidMove = game.tryMove(skip, card, selectedValue, pieceId, isStarter, opponentPieceId);
        MoveValid moveValid = new MoveValid();
        moveValid.setType(TypeGame.moveValid.getOrdinal());
        moveValid.setSkip(skip);
        moveValid.setCard(card);
        moveValid.setSelectedValue(selectedValue);
        moveValid.setPieceId(pieceId);
        moveValid.setStarter(isStarter);
        moveValid.setOpponentPieceId(opponentPieceId);
        moveValid.setValidMove(isValidMove);
        String moveValidMessage = gson.toJson(moveValid, MoveValid.class);
        //TODO check if the game over
        lobbyPublisher.publish(moveValidMessage);

        cleanUpAfterMove(clientId, isValidMove);
    }

    /**
     * Clean up game after performing a move
     *
     * @param clientId The client id that performed the move
     */
    private void cleanUpAfterMove(int clientId, boolean wasValidMove) {
        Player player = game.getCurrentPlayer();
        if (wasValidMove && player.generateMoves(game).isEmpty()) { // If player has no possible moves
            removeFromRound(clientId, player);
        } else if (!wasValidMove) {
            switch (lobbyConfig.getConsequencesForInvalidMove()) {
                case kickFromGame:
                    removePlayer(clientId);
                    break;
                case excludeFromRound:
                    removeFromRound(clientId, player);
                    break;
            }
        }

        BoardState boardState = boardStateService.generateBoardState(game, lobbyConfig.getPlayerOrder());
        String boardStateMessage = gson.toJson(boardState);

        lobbyPublisher.publish(boardStateMessage);

        if (!game.nextPlayer()) { // maybe round is over or maybe game is over
            if (game.checkGameOver()) {
                finishGame();
                return;
            } else {
                game.reInit();
                setupNewRound();
            }
        }
        int nextMoveClientId = getClientToMoveId();
        if (!serverController.setWaitingForMove(nextMoveClientId)) {
            throw new RuntimeException("Failed to keep up consistency because setting up next move failed");
        }
        startTurnTimer();
    }

    /**
     * Can be used internally or by Ausrichter to cancel/finish the game
     */
    public void finishGame() {
        gameState = GameState.FINISHED;
        stopLiveTimer();
        Cancel cancel = new Cancel();
        cancel.setType(TypeGame.cancel.getOrdinal());
        cancel.setWinnerOrder(game.getWinnerOrder());
        lobbyPublisher.publish(gson.toJson(cancel));
        canceled = true;
    }

    private synchronized void removeFromRound(int clientId, Player player) {
        DrawCards drawCards = new DrawCards();
        drawCards.setType(TypeGame.drawCards.getOrdinal());
        drawCards.setDroppedCards(player.getCardListInteger());
        drawCards.setDrawnCards(new ArrayList<>());
        String drawCardsMessage = gson.toJson(drawCards, DrawCards.class);

        game.discardHandCards();
        game.excludeFromRound(player);
        messageBroker.sendMessage(ChannelType.SINGLE, clientId, drawCardsMessage);

        UpdateDrawCardsService updateDrawCardsService = new UpdateDrawCardsService();
        UpdateDrawCards updateDrawCards = updateDrawCardsService.generateClientWithNoCards(clientId);
        String updateDrawCardsMessage = gson.toJson(updateDrawCards, UpdateDrawCards.class);

        // TODO: Only one player gets removed from the list? DEFINITELY CHECK THIS
        messageBroker.unregisterSubscriber(clientId, id);
        lobbyPublisher.publish(updateDrawCardsMessage); // TODO: Check: Observer will also receive this message...
        messageBroker.registerSubscriber(clientId, id);
    }

    /**
     * Sets up everything to begin a new round except for the init
     */
    private void setupNewRound() {
        game.distributeCards();
        DrawCards drawCards = new DrawCards();
        drawCards.setType(TypeGame.drawCards.getOrdinal());
        drawCards.setDroppedCards(new ArrayList<>());
        ArrayList<UpdateDrawCards.HandCard> handCards = new ArrayList<>();
        List<PlayerElement> order = lobbyConfig.getPlayerOrder().getOrder();
        for (int playerOrderIndex = 0; playerOrderIndex < order.size(); playerOrderIndex++) {
            int playerClientId = order.get(playerOrderIndex).getClientId();
            if (playerClientId == -1) continue;
            Player player = game.getPlayerList().get(playerOrderIndex);

            drawCards.setDrawnCards(player.getCardListInteger());
            String drawCardsMessage = gson.toJson(drawCards, DrawCards.class);
            messageBroker.sendMessage(ChannelType.SINGLE, playerClientId, drawCardsMessage);

            UpdateDrawCards.HandCard handCard = new UpdateDrawCards.HandCard();
            handCard.setClientId(playerClientId);
            handCard.setCount(player.getCardList().size());
            handCards.add(handCard);
        }
        UpdateDrawCards updateDrawCards = new UpdateDrawCards();
        updateDrawCards.setType(TypeGame.updateDrawCards.getOrdinal());
        updateDrawCards.setHandCards(handCards);
        String updateDrawCardsMessage = gson.toJson(updateDrawCards, UpdateDrawCards.class);
        lobbyPublisher.publish(updateDrawCardsMessage);
    }

    /**
     * Sets the configuration parameters for a game
     *
     * @param gameName                   The game name
     * @param maxPlayerCount             An Integer representing the number of maximum players in the game
     * @param fieldSize                  An Integer representing the size of the game field
     * @param figuresPerPlayer           An Integer representing the number of figures each player has
     * @param colorMap                   A mapping from colors to clientIds
     * @param drawCardFields             A List of Integers representing the position of the draw fields in the game
     * @param startFields                A List of Integers representing the positions of start fields on the game board
     * @param initialCardsPerPlayer      An Integer representing the initial number of cards each player receives
     * @param thinkTimePerMove           An Integer representing the maximum time a player has to make a move
     * @param visualizationTimePerMove   The time provided to visualize the move
     * @param consequencesForInvalidMove An Integer representing the consequences for an invalid move
     * @param maximumGameDuration        An Integer representing the maximum duration of a game
     * @param maximumTotalMoves          An Integer representing the maximum total number of moves allowed in the game
     */
    public void setConfiguration(String gameName,
                                 int maxPlayerCount,
                                 int fieldSize,
                                 int figuresPerPlayer,
                                 List<ColorMapping> colorMap,
                                 DrawCardFields drawCardFields,
                                 StartFields startFields,
                                 int initialCardsPerPlayer,
                                 int thinkTimePerMove,
                                 int visualizationTimePerMove,
                                 int consequencesForInvalidMove,
                                 int maximumGameDuration,
                                 int maximumTotalMoves) {
        lobbyConfig.importLobbyConfig(
                gameName,
                maxPlayerCount,
                fieldSize,
                figuresPerPlayer,
                colorMap,
                drawCardFields,
                startFields,
                initialCardsPerPlayer,
                thinkTimePerMove,
                visualizationTimePerMove,
                consequencesForInvalidMove,
                maximumGameDuration,
                maximumTotalMoves);

//        this.game = new Game(fieldStringBuild.toString(), figuresPerPlayer, initialCardsPerPlayer, maxTotalMoves, consequencesForInvalidMove);
    }

    /**
     * Checks whether responses have been received from every player and observer in the game lobby
     *
     * @return A Boolean indicating if responses from every player and observer have been received or not
     */
    private int getPlayerId(int clientId) {
        return lobbyConfig.getPlayerOrder().find(clientId);
    }

    private Integer getClientId(int playerId) {
        return lobbyConfig.getPlayerOrder().getOrder().get(playerId).getClientId();
    }

    private Integer getClientToMoveId() {
        return getClientId(game.getPlayerToMoveId());
    }

    private void sendError(int clientId, String errorMessage) {
        log.error(errorMessage);
        Error error = new Error();
        error.setType(TypeMenue.error.getOrdinal());
        error.setMessage(errorMessage);
        String response = gson.toJson(error, Error.class);
        messageBroker.sendMessage(ChannelType.SINGLE, clientId, response);
    }

    private void randomMoveMatch() {
        for (int i = 0; i < lobbyConfig.getMaxPlayerCount(); i++) {
            //clientId = playerId
            lobbyConfig.addPlayer("test", i);
            lobbyConfig.addColor(Colors.values()[i], i);
        }
        while (true) {
            stopTurnTimer();
            Move move = game.getRandomMove();
            boolean skip = true;
            Card card = null;
            int selectedValue = 0;
            int pieceId = 0;
            boolean isStarter = false;
            int opponentPieceId = 0;
            int fakeClientId = 1; // game.getCurrentPlayer().getPlayerId(); TODO: I uncommented because playerId doesn't exist anymore

            if (move != null) {
                skip = false;
                card = move.getCardUsed();
                selectedValue = move.getSelectedValue();
                //selected figure could be on bench
                if (move.isStartMove()) {
                    pieceId = game.getCurrentPlayer().getFirstOnBench().getFigureId();
                } else {
                    pieceId = move.getFrom().getFigure().getFigureId();

                }
                isStarter = move.isStartMove();
                if (move.isSwapMove()) {
                    opponentPieceId = move.getTo().getFigure().getFigureId();
                } else {
                    opponentPieceId = -1; //dont care
                }

            }
            move.execute(game);

            MoveValid moveValid = new MoveValid();

            moveValid.setType(TypeGame.moveValid.getOrdinal());
            moveValid.setSkip(skip);
            moveValid.setCard(card.ordinal());
            moveValid.setSelectedValue(selectedValue);
            moveValid.setPieceId(pieceId);
            moveValid.setStarter(isStarter);
            moveValid.setOpponentPieceId(opponentPieceId);
            moveValid.setValidMove(true);

            String moveValidMessage = gson.toJson(moveValid, MoveValid.class);

            lobbyPublisher.publish(moveValidMessage);

            cleanUpAfterMove(fakeClientId, true);

            try {
                Thread.sleep(lobbyConfig.getThinkTimePerMove() * 1000L / 2);
            } catch (InterruptedException e) {
                log.error("Sleeping thread got interrupted: " + e.getMessage());
            }
        }
    }
}
