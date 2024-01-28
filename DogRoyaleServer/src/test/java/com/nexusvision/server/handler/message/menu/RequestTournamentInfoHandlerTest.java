package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.RequestTournamentInfo;
import com.nexusvision.server.model.messages.menu.ReturnTournamentInfo;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.TournamentExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TournamentExtension.class)
class RequestTournamentInfoHandlerTest extends HandlerTest {

    RequestTournamentInfoHandler handler = new RequestTournamentInfoHandler();

    @Test
    void testHandle() {
        Tournament runningTournament = serverController.getTournamentOfPlayer(-204);

        RequestTournamentInfo request = new RequestTournamentInfo();
        request.setType(TypeMenue.requestTournamentInfo.getOrdinal());
        request.setClientId(clientId1);
        request.setTournamentId(runningTournament.getTournamentId());

        ReturnTournamentInfo returnTournamentInfo = handleAndRetrieve(request, clientId1, messageListener1);

        assertEquals(TypeMenue.returnTournamentInfo.getOrdinal(), returnTournamentInfo.getType());
        ReturnTournamentInfo.TournamentInfo tournamentInfo = returnTournamentInfo.getTournamentInfo();
        assertNotNull(tournamentInfo);
        assertEquals(runningTournament.getTournamentId(), tournamentInfo.getTournamentId());
        assertEquals(runningTournament.getRunningGame(), tournamentInfo.getGameRunning());
        assertEquals(runningTournament.getUpcomingGamesList(), tournamentInfo.getGamesUpcoming());
        assertEquals(runningTournament.getFinishedGamesList(), tournamentInfo.getGamesFinished());
        assertEquals(runningTournament.getCurrentRankings(), tournamentInfo.getCurrentRankings());
    }

    @Test
    void testHandleWrongTournament() {
        RequestTournamentInfo request = new RequestTournamentInfo();
        request.setType(TypeMenue.requestTournamentInfo.getOrdinal());
        request.setClientId(clientId1);
        request.setTournamentId(-1000);

        assertThrows(HandlingException.class, () -> {
            handler.handle(request, clientId1);
        });
    }

    private ReturnTournamentInfo handleAndRetrieve(RequestTournamentInfo request, int clientId, MessageListener messageListener) {
        return handleAndRetrieve(request, handler, clientId, messageListener, ReturnTournamentInfo.class);
    }
}
