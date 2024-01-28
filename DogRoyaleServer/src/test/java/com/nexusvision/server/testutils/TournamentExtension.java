package com.nexusvision.server.testutils;

import com.nexusvision.server.common.MessageListener;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.MessageBroker;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TournamentExtension implements BeforeAllCallback, AfterAllCallback {

    private static ServerController serverController;
    private static MessageBroker messageBroker;

    private final static String JSON_FILE_PATH = "src/test/java/com/nexusvision/server/testutils/";

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        serverController = ServerController.getInstance();
        messageBroker = MessageBroker.getInstance();

        int tournamentId1 = serverController.createNewTournament(10);
        int tournamentId2 = serverController.createNewTournament(20);
        int tournamentId3 = serverController.createNewTournament(30);
        int tournamentId4 = serverController.createNewTournament(40);
        int tournamentId5 = serverController.createNewTournament(50);

        Tournament tournament1 = serverController.getTournamentById(tournamentId1); // UPCOMING
        Tournament tournament2 = serverController.getTournamentById(tournamentId2); // UPCOMING
        Tournament tournament3 = serverController.getTournamentById(tournamentId3); // RUNNING
        Tournament tournament4 = serverController.getTournamentById(tournamentId4); // RUNNING
        Tournament tournament5 = serverController.getTournamentById(tournamentId5); // FINISHED

        runTournament(tournament3, "lobby4config.json", 8);
        runTournament(tournament4, "lobby4config.json", 11);
        runTournament(tournament5, "lobby4config.json", 15);

        boolean successful;
        do {
            int tempLobbyId = tournament5.getGameRunning();
            GameLobby tempLobby = serverController.getLobbyById(tempLobbyId);
            tempLobby.finishGame();
            successful = tournament5.startNextGameAndFinishUpCurrent();
        } while (successful);
    }

    private void runTournament(Tournament tournament, String fileName, int playerCount) {
        tournament.setConfiguration(JSON_FILE_PATH + fileName);
        for (int i = 0; i < playerCount; i++) {
            int clientId = serverController.createNewClient();
            messageBroker.addIdentifier(clientId, new MessageListener());
            tournament.addPlayer(clientId, "clientId:" + clientId);
        }
        tournament.startTournament();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        serverController = null;
        messageBroker = null;
    }
}
