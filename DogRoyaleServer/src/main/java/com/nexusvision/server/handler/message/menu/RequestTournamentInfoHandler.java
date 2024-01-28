package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.RequestTournamentInfo;
import com.nexusvision.server.model.messages.menu.ReturnTournamentInfo;
import com.nexusvision.server.model.messages.menu.ReturnTournamentList;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.util.List;

public class RequestTournamentInfoHandler extends MessageHandler<RequestTournamentInfo> {

    private static final ServerController serverController = ServerController.getInstance();

    @Override
    protected void performHandle(RequestTournamentInfo message, int clientId) throws HandlingException {
        verifyClientId(clientId, message.getClientId());

        Tournament tournament = serverController.getTournamentById(message.getTournamentId());

        ReturnTournamentInfo returnTournamentInfo = new ReturnTournamentInfo();
        returnTournamentInfo.setType(TypeMenue.requestTournamentInfo.getOrdinal());

        ReturnTournamentInfo.TournamentInfo tournamentInfo = getTournamentInfo(tournament);
        returnTournamentInfo.setTournamentInfo(tournamentInfo);

        String response = gson.toJson(returnTournamentInfo);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }

    private static ReturnTournamentInfo.TournamentInfo getTournamentInfo(Tournament tournament) {
        ReturnTournamentInfo.TournamentInfo tournamentInfo = new ReturnTournamentInfo.TournamentInfo();
        tournamentInfo.setTournamentId(tournament.getTournamentId());

        ReturnTournamentInfo.TournamentInfo.RunningGame runningGame = tournament.getRunningGame();
        tournamentInfo.setGameRunning(runningGame);

        List<ReturnTournamentInfo.TournamentInfo.UpcomingGames> upcomingGames = tournament.getUpcomingGamesList();
        tournamentInfo.setGamesUpcoming(upcomingGames);

        List<ReturnTournamentInfo.TournamentInfo.FinishedGames> finishedGames = tournament.getFinishedGamesList();
        tournamentInfo.setGamesFinished(finishedGames);

        tournamentInfo.setCurrentRankings(tournament.getCurrentRankings());
        return tournamentInfo;
    }
}
