package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.util.ArrayList;

/**
 * Handling all requests of type <code>requestGameList</code>
 *
 * @author felixwr
 */
public class RequestGameListHandler extends MessageHandler<RequestGameList> {

    /**
     * Handles a client's request for a list of games, retrieves game lobbies based on their states,
     * prepares lists of game information, creates a ReturnGameList object.
     * The response includes information about starting, running, and finished games.
     *
     * @param message An Instance of the <code>RequestGameList</code> representing a client's request for a list of games
     * @param clientId An Integer representing the Id of the requesting client
     */
    @Override
    protected void performHandle(RequestGameList message, int clientId) throws HandlingException {
        verifyClientID(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        ArrayList<GameLobby> startingLobbyList = serverController.getStateGames(message.getGameCountStarting(), GameState.STARTING);
        ArrayList<GameLobby> runningLobbyList = serverController.getStateGames(message.getGameCountInProgress(), GameState.IN_PROGRESS);
        ArrayList<GameLobby> finishedLobbyList = serverController.getStateGames(message.getGameCountFinished(), GameState.FINISHED);

        ArrayList<ReturnGameList.Game> startingGameList = new ArrayList<>();
        ArrayList<ReturnGameList.Game> runningGameList = new ArrayList<>();
        ArrayList<ReturnGameList.Game> finishedGameList = new ArrayList<>();

        for (GameLobby lobby : startingLobbyList) {
            ReturnGameList.Game game = new ReturnGameList.Game();
            game.setGameId(lobby.getId());
            game.setCurrentPlayerCount(lobby.getCurrentPlayerCount());
            game.setMaxPlayerCount(lobby.getMaxPlayerCount());
            startingGameList.add(game);
        }

        for (GameLobby lobby : runningLobbyList) {
            ReturnGameList.Game game = new ReturnGameList.Game();
            game.setGameId(lobby.getId());
            game.setCurrentPlayerCount(lobby.getCurrentPlayerCount());
            game.setMaxPlayerCount(lobby.getMaxPlayerCount());
            runningGameList.add(game);
        }

        for (GameLobby lobby : finishedLobbyList) {
            ReturnGameList.Game game = new ReturnGameList.Game();
            game.setGameId(lobby.getId());
            game.setWinnerPlayerId(lobby.getWinnerPlayerId());
            finishedGameList.add(game);
        }

        ReturnGameList returnGameList = new ReturnGameList();
        returnGameList.setType(TypeMenue.returnGameList.getOrdinal());
        returnGameList.setStartingGames(startingGameList);
        returnGameList.setRunningGames(runningGameList);
        returnGameList.setFinishedGames(finishedGameList);

        String response = gson.toJson(returnGameList);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }
}

