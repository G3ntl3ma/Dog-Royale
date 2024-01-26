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
     * @param clientId An Integer representing the id of the requesting client
     */
    @Override
    protected void performHandle(RequestGameList message, int clientId) throws HandlingException {
        verifyClientID(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        ArrayList<GameLobby> startingLobbyList = serverController.getStateGames(message.getGamesUpcomingCount(), GameState.STARTING);
        ArrayList<GameLobby> runningLobbyList = serverController.getStateGames(message.getGamesRunningCount(), GameState.IN_PROGRESS);
        ArrayList<GameLobby> finishedLobbyList = serverController.getStateGames(message.getGamesFinishedCount(), GameState.FINISHED);

        ArrayList<ReturnGameList.NotFinishedGame> startingGameList = new ArrayList<>();
        ArrayList<ReturnGameList.NotFinishedGame> runningGameList = new ArrayList<>();
        ArrayList<ReturnGameList.FinishedGame> finishedGameList = new ArrayList<>();

        for (GameLobby lobby : startingLobbyList) {
            ReturnGameList.NotFinishedGame game = getNotFinishedGame(lobby);
            startingGameList.add(game);
        }

        for (GameLobby lobby : runningLobbyList) {
            ReturnGameList.NotFinishedGame game = getNotFinishedGame(lobby);
            runningGameList.add(game);
        }

        for (GameLobby lobby : finishedLobbyList) {
            ReturnGameList.FinishedGame game = getFinishedGame(lobby);
            finishedGameList.add(game);
        }

        ReturnGameList returnGameList = new ReturnGameList();
        returnGameList.setType(TypeMenue.returnGameList.getOrdinal());
        returnGameList.setGamesUpcoming(startingGameList);
        returnGameList.setGamesRunning(runningGameList);
        returnGameList.setGamesFinished(finishedGameList);

        String response = gson.toJson(returnGameList);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }

    private static ReturnGameList.NotFinishedGame getNotFinishedGame(GameLobby lobby) {
        ReturnGameList.NotFinishedGame game = new ReturnGameList.NotFinishedGame();
        game.setGameId(lobby.getId());
        game.setGameName(lobby.getLobbyConfig().getGameName());
        game.setPlayerOrder(lobby.getLobbyConfig().getPlayerOrder().getOrder());
        game.setWinnerOrder(lobby.getLobbyConfig().getWinnerOrder());
        game.setMaxPlayerCount(lobby.getLobbyConfig().getMaxPlayerCount());
        return game;
    }

    private static ReturnGameList.FinishedGame getFinishedGame(GameLobby lobby) {
        ReturnGameList.FinishedGame game = new ReturnGameList.FinishedGame();
        game.setGameId(lobby.getId());
        game.setGameName(lobby.getLobbyConfig().getGameName());
        game.setWasCanceled(lobby.isCanceled());
        game.setWinnerOrder(lobby.getLobbyConfig().getWinnerOrder());
        return game;
    }
}

