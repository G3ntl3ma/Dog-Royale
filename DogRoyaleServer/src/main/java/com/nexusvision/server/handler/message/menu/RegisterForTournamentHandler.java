package com.nexusvision.server.handler.message.menu;

import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.handler.HandlingException;
import com.nexusvision.server.handler.message.MessageHandler;
import com.nexusvision.server.model.messages.menu.RegisterForTournament;
import com.nexusvision.server.model.messages.menu.RegisteredForTournament;
import com.nexusvision.server.model.messages.menu.TypeMenue;

/**
 * @author felixwr
 */
public class RegisterForTournamentHandler extends MessageHandler<RegisterForTournament> {

    private static final ServerController serverController = ServerController.getInstance();

    @Override
    protected void performHandle(RegisterForTournament message, int clientId) throws HandlingException {
        verifyClientId(clientId, message.getClientId());

        Tournament tournament = serverController.getTournamentById(message.getTournamentId());
        String name = serverController.getClientById(clientId).getName();
        boolean successful = tournament.addPlayer(clientId, name);

        RegisteredForTournament registeredForTournament = new RegisteredForTournament();
        registeredForTournament.setType(TypeMenue.registeredForTournament.getOrdinal());
        registeredForTournament.setTournamentId(tournament.getTournamentId());
        registeredForTournament.setMaxPlayer(tournament.getMaxPlayer());
        registeredForTournament.setPlayers(tournament.getPlayerElements());
        registeredForTournament.setMaxGames(tournament.getMaxGames());
        String response = gson.toJson(registeredForTournament);
        MessageBroker.getInstance().sendMessage(ChannelType.SINGLE, clientId, response);
    }
}
