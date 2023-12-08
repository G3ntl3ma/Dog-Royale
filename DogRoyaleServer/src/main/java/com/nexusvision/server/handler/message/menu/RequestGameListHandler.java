package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.handler.Handler;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.RequestGameList;
import com.nexusvision.server.model.messages.menu.ReturnGameList;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.util.ArrayList;

/**
 * Handling all requests of type requestGameList
 *
 * @author felixwr
 */
public class RequestGameListHandler extends Handler implements MenuMessageHandler<RequestGameList> {

    @Override
    public String handle(RequestGameList message, int clientID) throws HandlingException {
        try {
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
                game.setWinnerPlayerId(lobby.getWinnerID());
                finishedGameList.add(game);
            }

            ReturnGameList returnGameList = new ReturnGameList();
            returnGameList.setType(TypeMenue.returnGameList.getOrdinal());
            returnGameList.setStartingGames(startingGameList);
            returnGameList.setRunningGames(runningGameList);
            returnGameList.setFinishedGames(finishedGameList);

            return gson.toJson(returnGameList);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling requestGameList",
                    e, TypeMenue.requestGameList.getOrdinal());
        }
    }
}

