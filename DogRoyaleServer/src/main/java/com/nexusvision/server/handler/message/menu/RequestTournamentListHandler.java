package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.RequestTournamentList;
import com.nexusvision.server.model.messages.menu.ReturnTournamentList;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author felixwr
 */
public class RequestTournamentListHandler extends MessageHandler<RequestTournamentList> {

    @Override
    protected void performHandle(RequestTournamentList message, int clientId) throws HandlingException {
        verifyClientId(clientId, message.getClientId());
        ServerController serverController = ServerController.getInstance();

        List<Tournament> tournamentsUpcoming = serverController.getStateTournaments(
                message.getTournamentsUpcomingCount(), GameState.UPCOMING);
        List<Tournament> tournamentsRunning = serverController.getStateTournaments(
                message.getTournamentsRunningCount(), GameState.RUNNING);
        List<Tournament> tournamentsFinished = serverController.getStateTournaments(
                message.getTournamentsFinishedCount(), GameState.FINISHED);

        List<ReturnTournamentList.UpcomingTournament> tournamentsUpcomingMessage = new ArrayList<>();
        List<ReturnTournamentList.RunningTournament> tournamentsRunningMessage = new ArrayList<>();
        List<ReturnTournamentList.FinishedTournament> tournamentsFinishedMessage = new ArrayList<>();

        for (Tournament tournament : tournamentsUpcoming) {
            tournamentsUpcomingMessage.add(getUpcomingTournament(tournament));
        }

        for (Tournament tournament : tournamentsRunning) {
            tournamentsRunningMessage.add(getRunningTournament(tournament));
        }

        for (Tournament tournament : tournamentsFinished) {
            tournamentsFinishedMessage.add(getFinishedTournament(tournament));
        }

        ReturnTournamentList returnTournamentList = new ReturnTournamentList();
        returnTournamentList.setType(TypeMenue.returnTournamentList.getOrdinal());
        returnTournamentList.setClientId(clientId);
        returnTournamentList.setTournamentsUpcoming(tournamentsUpcomingMessage);
        returnTournamentList.setTournamentsRunning(tournamentsRunningMessage);
        returnTournamentList.setTournamentsFinished(tournamentsFinishedMessage);

        String response = gson.toJson(returnTournamentList);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }

    private static ReturnTournamentList.UpcomingTournament getUpcomingTournament(Tournament tournament) {
        ReturnTournamentList.UpcomingTournament upcomingTournament = new ReturnTournamentList.UpcomingTournament();
        upcomingTournament.setTournamentId(tournament.getTournamentId());
        upcomingTournament.setMaxPlayer(tournament.getMaxPlayer());
        upcomingTournament.setMaxGames(tournament.getMaxGames());
        upcomingTournament.setCurrentPlayerCount(tournament.getCurrentPlayerCount());
        return upcomingTournament;
    }

    private static ReturnTournamentList.RunningTournament getRunningTournament(Tournament tournament) {
        ReturnTournamentList.RunningTournament runningTournament = new ReturnTournamentList.RunningTournament();
        runningTournament.setTournamentId(tournament.getTournamentId());
        runningTournament.setMaxPlayer(tournament.getMaxPlayer());
        runningTournament.setMaxGames(tournament.getMaxGames());
        runningTournament.setGameRunning(tournament.getGameRunning());
        return runningTournament;
    }

    private static ReturnTournamentList.FinishedTournament getFinishedTournament(Tournament tournament) {
        ReturnTournamentList.FinishedTournament finishedTournament = new ReturnTournamentList.FinishedTournament();
        finishedTournament.setTournamentId(tournament.getTournamentId());
        finishedTournament.setWinnerOrder(tournament.getWinnerOrder());
        return finishedTournament;
    }
}
