package com.nexusvision.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.common.Publisher;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.gamelogic.Game;
import com.nexusvision.server.model.gamelogic.Move;
import com.nexusvision.server.model.gamelogic.Player;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.service.BoardStateService;
import com.nexusvision.server.service.PlayerService;
import com.nexusvision.server.service.UpdateDrawCardsService;
import com.nexusvision.utils.NewLineAppendingSerializer;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
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
@Data
public class GameLobby {

    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    private final int id;
    private final ServerController serverController;
    private final BoardStateService boardStateService;
    private final PlayerService playerService;

    private final Publisher lobbyPublisher;
    private final MessageBroker messageBroker;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> liveTimerTask;
    private ScheduledFuture<?> turnTimerTask;
    private int gameDuration;
    private int thinkTimePerMove;
    private int turnTime;
    private int visualizationTimePerMove; // unused for now
    private int liveTimerSendCount;
    private int turnTimerSendCount;

    private Game game;
    private GameState gameState;
    private boolean isPaused;

    private int maxPlayerCount;

    private ArrayList<Integer> playerOrderList;
    private HashMap<Integer, Colors> playerColorMap;
    private ArrayList<Integer> observerList;

    private ArrayList<Integer> receivedResponseList = new ArrayList<>();

    /**
     * Creates a GameLobby with the given parameters
     *
     * @param playerOrderList The player order list
     * @param observerList    The observer ID list
     * @param playerColorMap  The player color list
     */
    public GameLobby(int id, ArrayList<Integer> playerOrderList, HashMap<Integer, Colors> playerColorMap,
                     ArrayList<Integer> observerList) {
        this.id = id;
        this.playerOrderList = playerOrderList;
        this.playerColorMap = playerColorMap;
        this.observerList = observerList;
        this.gameState = GameState.STARTING;
        this.isPaused = false;
        this.serverController = ServerController.getInstance();
        this.boardStateService = new BoardStateService();
        this.playerService = new PlayerService();

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
        turnTime = thinkTimePerMove;
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
        tryMove(0, true, 0, 0, 0, false, 0);
    }

    private Integer getClientId(int playerId) {
        return playerOrderList.get(playerId);
    }

    private Integer getClientToMoveId() {
        return getClientId(game.getPlayerToMoveId());
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

    public int getPlayerId(int clientId) {
        return playerOrderList.indexOf(clientId);
    }

    //check if playerOrderList + observerList is subset of received responses

    /**
     * Checks whether responses have been received from every player and observer in the game lobby
     *
     * @return A Boolean indicating if responses from every player and observer have been received or not
     */
    public boolean receivedFromEveryone() {
        for (int idToFind : playerOrderList) {
            //check if this id is in the list of responses
            boolean found = false;
            for (Integer receivedResponse : receivedResponseList) {
                if (receivedResponse == idToFind) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        for (int idToFind : observerList) {
            //check if this id is in the list of responses
            boolean found = false;
            for (Integer integer : receivedResponseList) {
                if (integer == idToFind) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    /**
     * Adds an observer to the lobby
     *
     * @param clientId The client id of the observer being added
     */
    public void addObserver(int clientId) {
        observerList.add(clientId);
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
            BoardState boardState = boardStateService.generateBoardState(game, playerOrderList);
            String boardStateMessage = gson.toJson(boardState);
            lobbyPublisher.publish(boardStateMessage);
        }
    }

    /**
     * Removes an observer from the lobby
     *
     * @param clientId The client Id of the observer being removed
     */
    public void removeObserver(int clientId) {
        observerList.remove(clientId);
        messageBroker.unregisterSubscriber(clientId, id);

        JoinObs joinObs = new JoinObs();
        joinObs.setType(TypeGame.joinObs.getOrdinal());
        joinObs.setCountObs(observerList.size());
        lobbyPublisher.publish(gson.toJson(joinObs));
    }

    /**
     * Adds a player to the lobby
     *
     * @param clientId The client id of the player being added
     * @param color    The color of the player being added
     */
    public void addPlayer(int clientId, Colors color) {
        playerOrderList.add(clientId);
        playerColorMap.put(clientId, color);
        // TODO: What if more players get added than maxPlayerCount allows? How to add with nickname?
        messageBroker.registerSubscriber(clientId, id);
        if (playerOrderList.size() == maxPlayerCount) {
            gameState = GameState.IN_PROGRESS; // TODO: Ausrichter decides when game gets started
            // TODO: Also for starting you need to use the runGame method
        }
    }

    /**
     * Removes a player from the lobby
     *
     * @param clientId The client id of the player being removed
     */
    public void removePlayer(int clientId) {
        if (gameState == GameState.IN_PROGRESS) {
            int playerId = getPlayerId(clientId);
            if (playerId != -1) {
                Player player = this.game.getPlayerList().get(playerId);
                game.removePlayerFromBoard(player);
                game.excludeFromGame(player);
                playerOrderList.set(playerId, null); //keep playerId
            }
        } else if (gameState == GameState.STARTING) {
            int playerId = getPlayerId(clientId);
            if (playerId != -1) {
                playerOrderList.remove(playerId);
            }
        }

        playerColorMap.remove(clientId);
        messageBroker.unregisterSubscriber(clientId, id);

        Kick kick = new Kick();
        kick.setType(TypeGame.kick.getOrdinal());
        kick.setClientId(clientId);
        kick.setReason("Player decided to leave");
        lobbyPublisher.publish(gson.toJson(kick));

        if (playerOrderList.isEmpty()) {
            finishGame();
        }
    }

    public Colors getColorOfPlayer(int clientId) { //return colortype
        return this.playerColorMap.get(clientId);
    }

    /**
     * The current placer count
     *
     * @return The current player count
     */
    public int getCurrentPlayerCount() {
        //return playerOrderList.size();
        int count = 0;
        for (Integer playerId : this.playerOrderList) {
            if (playerId != null) count++;
        }
        return count;
    }

    /**
     * Sets GameState to <code>IN_PROGRESS</code>
     */
    // This is how Ausrichter starts the game. Sending board states to all clients
    public void runGame() {
        gameState = GameState.IN_PROGRESS;
        BoardState boardState = boardStateService.generateBoardState(game, playerOrderList);
        String boardStateMessage = gson.toJson(boardState);
        boolean successful = serverController.startGameForLobby(this);
        if (successful) { // TODO: Might be dumb because some client handlers might have changed states but some not
            lobbyPublisher.publish(boardStateMessage);
            startLiveTimer();
        }
        // if simulate game make random move every 3 seconds or so
    }

    private void randomMoveMatch() {
        for (int i = 0; i < maxPlayerCount; i++) {
            //clientId = playerId
            playerOrderList.add(i);
            playerColorMap.put(i, Colors.values()[i]);
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
            int fakeClientId = game.getCurrentPlayer().getPlayerId();

            if (move != null) {
                skip = false;
                card = move.getCardUsed();
                selectedValue = move.getSelectedValue();
                //selected figure could be on bench
                if (move.isStartMove()) {
                    pieceId = game.getCurrentPlayer().getFirstOnBench().getFigureId(this.game);
                } else {
                    pieceId = move.getFrom().getFigure().getFigureId(this.game);

                }
                isStarter = move.isStartMove();
                if (move.isSwapMove()) {
                    opponentPieceId = move.getTo().getFigure().getFigureId(this.game);
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
                Thread.sleep(thinkTimePerMove * 1000L / 2);
            } catch (InterruptedException e) {
                log.error("Sleeping thread got interrupted: " + e.getMessage());
            }
        }
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

    //success boolean

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
    public void tryMove(int clientId, boolean skip, int card, int selectedValue,
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
        } else if (!wasValidMove && game.getConsequencesForInvalidMove() == Penalty.excludeFromRound) {
            removeFromRound(clientId, player);
        } else if (!wasValidMove && game.getConsequencesForInvalidMove() == Penalty.kickFromGame) { //3.14 send kick message to everyone
            removePlayer(clientId); // remove clientId from the arraylist in the lobby
            Kick kick = new Kick();
            kick.setType(TypeGame.kick.getOrdinal());
            kick.setClientId(clientId);
            kick.setReason("illegal move");
            lobbyPublisher.publish(gson.toJson(kick));
        }

        BoardState boardState = boardStateService.generateBoardState(game, playerOrderList);
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

    private void finishGame() {
        gameState = GameState.FINISHED;
        stopLiveTimer();
        Cancel cancel = new Cancel();
        cancel.setType(TypeGame.cancel.getOrdinal());
        cancel.setWinnerOrder(game.getWinnerOrder());
        lobbyPublisher.publish(gson.toJson(cancel));
    }

    private void removeFromRound(int clientId, Player player) {
        ArrayList<Integer> clientList = new ArrayList<>();
        clientList.addAll(playerOrderList);
        clientList.addAll(observerList);

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
        ArrayList<Integer> clientListReduced = new ArrayList<Integer>(clientList);
        clientListReduced.remove(Integer.valueOf(player.getPlayerId()));
        for (int client : clientListReduced) {
            messageBroker.sendMessage(ChannelType.SINGLE, clientId, updateDrawCardsMessage);
        }
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
        for (int playerClientId : playerOrderList) {
            int playerId = playerOrderList.indexOf(playerClientId);
            Player player = playerService.getPlayer(playerId, game);
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
     * @param playerCount                An Integer representing the number of players in the game
     * @param fieldSize                  An Integer representing the size of the gamefield
     * @param figuresPerPlayer           An Integer representing the number of figures each player has
     * @param drawCardFields             A List of Integers representing the position of the drawfields in the game
     * @param startFields                A List of Integers representing the positions of start fields on the game board
     * @param initialCardsPerPlayer      An Integer representing the initial number of cards each player recieves
     * @param thinkingTimePerMove        An Integer representing the maximum time a player has to make a move
     * @param consequencesForInvalidMove An Integer representing the consequences for an invalid move
     * @param maxGameDuration            An Integer representing the maximum duration of a game
     * @param maxTotalMoves              An Integer representing the maximum total number of moves allowed in the game
     */
    //success boolean
    public boolean setConfiguration(int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawCardFields,
                                    List<Integer> startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                    int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        //constructing a string that can be parsed by the game
        this.maxPlayerCount = playerCount;
        this.thinkTimePerMove = thinkingTimePerMove;
        this.gameDuration = maxGameDuration;
        StringBuilder fieldStringBuild = new StringBuilder();
        fieldStringBuild.append("n".repeat(Math.max(0, fieldSize)));

        for (int i = 0; i < startFields.size(); i++) {
            fieldStringBuild.setCharAt(i, 's');
        }

        for (int i = 0; i < drawCardFields.size(); i++) {
            fieldStringBuild.setCharAt(i, 'k');
        }

        String fieldString = fieldStringBuild.toString();

        if (playerCount == getCurrentPlayerCount()) {
            gameState = GameState.IN_PROGRESS;
            isPaused = true;
        }

        this.game = new Game(fieldStringBuild.toString(), figuresPerPlayer, initialCardsPerPlayer, maxTotalMoves, consequencesForInvalidMove);

        return true; // TODO: only true?
    }

}
