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

public class RequestGameListHandler extends Handler implements MenuMessageHandler<RequestGameList> {

    @Override
    public String handle(RequestGameList message, int clientID) throws HandlingException {
        try {
            ServerController serverController = ServerController.getInstance();

//            if (message.getGameCountStarting() == null
//                    || message.getGameCountInProgress() == null
//                    || message.getGameCountFinished() == null
//                    || message.getClientID() == null) {
//                return handleError("Request failed, malformed request",
//                        TypeMenue.requestGameList.getOrdinal());
//            }

            ReturnGameList returnGameList = new ReturnGameList();
            returnGameList.setType(TypeMenue.returnGameList.getOrdinal());

            ArrayList<GameLobby> startingGames = serverController.getStateGames(message.getGameCountStarting(), GameState.STARTING);
            ArrayList<GameLobby> runningGames = serverController.getStateGames(message.getGameCountInProgress(), GameState.IN_PROGRESS);
            ArrayList<GameLobby> finishedGames = serverController.getStateGames(message.getGameCountFinished(), GameState.FINISHED);

            ReturnGameList _returnGameList = new ReturnGameList();

            ArrayList<ReturnGameList.StartingGame> startingGameList = new ArrayList<>();
            ArrayList<ReturnGameList.RunningGame> runningGameList = new ArrayList<>();
            ArrayList<ReturnGameList.FinishingGame> finishingGameList = new ArrayList<>();

            for (int i = 0; i < startingGames.size(); i++) {
                GameLobby game = startingGames.get(i);
                ReturnGameList.StartingGame startingGame = new ReturnGameList.StartingGame();
                // TODO: shoudn't be commented
//                startingGame.setGameId(serverController.getGameId(game));
//                startingGame.setCurrentPlayerCount(serverController.getCurrentPlayerCount(game));
//                startingGame.setMaxPlayerCount(serverController.getMaxPlayerCount(game));
                startingGameList.add(startingGame);
            }

            for (int i = 0; i < runningGames.size(); i++) {
                GameLobby game = runningGames.get(i);
                ReturnGameList.RunningGame runningGame = new ReturnGameList.RunningGame();
                // TODO: shoudn't be commented
//                runningGame.setGameId(serverController.getGameId(game));
//                runningGame.setCurrentPlayerCount(serverController.getCurrentPlayerCount(game));
//                runningGame.setMaxPlayerCount(serverController.getMaxPlayerCount(game));
                runningGameList.add(runningGame);
            }

            for (int i = 0; i < finishedGames.size(); i++) {
                GameLobby game = finishedGames.get(i);
                ReturnGameList.FinishingGame finishGame = new ReturnGameList.FinishingGame();
                // TODO: shoudn't be commented
//                finishGame.setGameId(serverController.getGameId(game));
                finishGame.setWinnerPlayerId(10 /*TODO getwinnerplayer*/);
                finishingGameList.add(finishGame);
            }

            _returnGameList.setStartingGames(startingGameList);
            _returnGameList.setRunningGames(runningGameList);
            _returnGameList.setFinishedGames(finishingGameList);

            return gson.toJson(_returnGameList);
        } catch (Exception e) {
            throw new HandlingException("Exception while handling requestGameList",
                    e, TypeMenue.requestGameList.getOrdinal());
        }
    }
}
