package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.model.messages.menu.RequestTournamentList;
import com.nexusvision.server.model.messages.menu.ReturnTournamentList;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.TournamentExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author felixwr
 */
@ExtendWith(TournamentExtension.class)
class RequestTournamentListHandlerTest extends HandlerTest {

    RequestTournamentListHandler handler = new RequestTournamentListHandler();

    @Test
    void testHandle() {
        RequestTournamentList request = new RequestTournamentList();
        request.setType(TypeMenue.requestTournamentList.getOrdinal());
        request.setClientId(clientId1);
        request.setTournamentsUpcomingCount(1);
        request.setTournamentsRunningCount(1);
        request.setTournamentsFinishedCount(1);

        ReturnTournamentList returnTournamentList = handleAndRetrieve(request, clientId1, messageListener1);

        assertEquals(TypeMenue.returnTournamentList.getOrdinal(), returnTournamentList.getType());
        assertNotNull(returnTournamentList.getTournamentsUpcoming());
        assertNotNull(returnTournamentList.getTournamentsRunning());
        assertNotNull(returnTournamentList.getTournamentsFinished());
        assertEquals(1, returnTournamentList.getTournamentsUpcoming().size());
        assertEquals(1, returnTournamentList.getTournamentsRunning().size());
        assertEquals(1, returnTournamentList.getTournamentsFinished().size());

        ReturnTournamentList.UpcomingTournament upcomingTournament = returnTournamentList.getTournamentsUpcoming().get(0);
        checkUpcomingTournament(upcomingTournament);

        ReturnTournamentList.RunningTournament runningTournament = returnTournamentList.getTournamentsRunning().get(0);
        checkRunningTournament(runningTournament);

        ReturnTournamentList.FinishedTournament finishedTournament = returnTournamentList.getTournamentsFinished().get(0);
        checkFinishedTournament(finishedTournament);
    }

    private static void checkUpcomingTournament(ReturnTournamentList.UpcomingTournament tournamentResponse) {
        Tournament tournament = serverController.getTournamentById(tournamentResponse.getTournamentId());
        assertNotNull(tournament);
        assertEquals(tournament.getTournamentId(), tournamentResponse.getTournamentId());
        assertEquals(tournament.getMaxPlayer(), tournamentResponse.getMaxPlayer());
        assertEquals(tournament.getMaxGames(), tournamentResponse.getMaxGames());
        assertEquals(tournament.getCurrentPlayerCount(), tournamentResponse.getCurrentPlayerCount());
    }

    private static void checkRunningTournament(ReturnTournamentList.RunningTournament tournamentResponse) {
        Tournament tournament = serverController.getTournamentById(tournamentResponse.getTournamentId());
        assertNotNull(tournament);
        assertEquals(tournament.getTournamentId(), tournamentResponse.getTournamentId());
        assertEquals(tournament.getMaxPlayer(), tournamentResponse.getMaxPlayer());
        assertEquals(tournament.getMaxGames(), tournamentResponse.getMaxGames());
        assertEquals(tournament.getGameRunning(), tournamentResponse.getGameRunning());
    }

    private static void checkFinishedTournament(ReturnTournamentList.FinishedTournament tournamentResponse) {
        Tournament tournament = serverController.getTournamentById(tournamentResponse.getTournamentId());
        assertNotNull(tournament);
        assertEquals(tournament.getTournamentId(), tournamentResponse.getTournamentId());
        System.out.println(tournament.getWinnerOrder());
        assertEquals(tournament.getWinnerOrder(), tournamentResponse.getWinnerOrder());
    }

    private ReturnTournamentList handleAndRetrieve(RequestTournamentList request,
                                                   int clientId, MessageListener messageListener) {
        return handleAndRetrieve(request, handler, clientId, messageListener, ReturnTournamentList.class);
    }
}