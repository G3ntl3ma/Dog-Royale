package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlerTest;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.model.messages.menu.RegisterForTournament;
import com.nexusvision.server.model.messages.menu.RegisteredForTournament;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.testutils.TournamentExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author felixwr
 */
@ExtendWith(TournamentExtension.class)
class RegisterForTournamentHandlerTest extends HandlerTest {

    RegisterForTournamentHandler handler = new RegisterForTournamentHandler();

    @Test
    void testHandle() {
        Tournament upcomingTournament = serverController.getTournamentOfPlayer(-201);

        RegisterForTournament request = new RegisterForTournament();
        request.setType(TypeMenue.registerForTournament.getOrdinal());
        request.setClientId(clientId1);
        request.setTournamentId(upcomingTournament.getTournamentId());

        RegisteredForTournament registeredForTournament = handleAndRetrieve(request, clientId1, messageListener1);

        assertEquals(TypeMenue.registeredForTournament.getOrdinal(), registeredForTournament.getType());
        assertTrue(registeredForTournament.isSuccess());
        assertEquals(upcomingTournament.getTournamentId(), registeredForTournament.getTournamentId());
        assertEquals(upcomingTournament.getMaxPlayer(), registeredForTournament.getMaxPlayer());
        assertNotNull(registeredForTournament.getPlayers());
        assertEquals(upcomingTournament.getPlayerElements(), registeredForTournament.getPlayers());
        assertEquals(upcomingTournament.getMaxGames(), registeredForTournament.getMaxGames());


        for (int i = 0; i < 5; i++) { // add 5 more players to fill lobby
            int clientId = serverController.createNewClient();
            messageBroker.addIdentifier(clientId, new MessageListener());
            upcomingTournament.addPlayer(clientId, "clientId:" + clientId);
        }
        // should fail because lobby should be full (max is 6)
        request.setClientId(clientId2);
        registeredForTournament = handleAndRetrieve(request, clientId2, messageListener2);

        assertEquals(TypeMenue.registeredForTournament.getOrdinal(), registeredForTournament.getType());
        assertFalse(registeredForTournament.isSuccess());
    }

    @Test
    void testHandleWrongTournament() {
        RegisterForTournament request = new RegisterForTournament();
        request.setType(TypeMenue.registerForTournament.getOrdinal());
        request.setClientId(clientId1);
        request.setTournamentId(-1000);

        assertThrows(HandlingException.class, () -> {
            handler.handle(request, clientId1);
        });
    }

    private RegisteredForTournament handleAndRetrieve(
            RegisterForTournament request, int clientId, MessageListener messageListener) {
        return handleAndRetrieve(request, handler, clientId, messageListener, RegisteredForTournament.class);
    }
}
