package com.nexusvision.client;

import com.google.gson.Gson;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class CommandProcessor {

    private static final ServerController serverController = ServerController.getInstance();

    public String processCommand(String command) {
        String[] tokens = command.split(" ");
        String action = tokens[0].toLowerCase();

        switch (action) {
            case "ls":
                return showGames(tokens);
            case "touch":
                return createGame(tokens);
            default:
                return "No such command: " + action;
        }
    }

    private String showGames(String[] tokens) {
        if (tokens.length < 2) {
            return "Required argument: ls <kind>";
        }

        String kind = tokens[1].toLowerCase();
        switch (kind) {
            case "game":
                String lobbyResponse = "";
                lobbyResponse += "Upcoming games:\n";
                List<GameLobby> upcomingLobbyList = serverController.getStateGames(100, GameState.UPCOMING);
                lobbyResponse += getLobbyListString(upcomingLobbyList);
                lobbyResponse += "Running games:\n";
                List<GameLobby> runningLobbyList = serverController.getStateGames(100, GameState.RUNNING);
                lobbyResponse += getLobbyListString(runningLobbyList);
                lobbyResponse += "Finished games:\n";
                List<GameLobby> finishedLobbyList = serverController.getStateGames(100, GameState.FINISHED);
                lobbyResponse += getLobbyListString(finishedLobbyList);
                return lobbyResponse;
            case "tournament":
                String tournamentResponse = "";
                tournamentResponse += "Upcoming tournaments:\n";
                List<Tournament> tournamentsUpcoming = serverController.getStateTournaments(100, GameState.UPCOMING);
                tournamentResponse += getTournamentListString(tournamentsUpcoming);
                tournamentResponse += "Running tournaments:\n";
                List<Tournament> tournamentsRunning = serverController.getStateTournaments(100, GameState.RUNNING);
                tournamentResponse += getTournamentListString(tournamentsRunning);
                tournamentResponse += "Finished tournaments:\n";
                List<Tournament> tournamentsFinished = serverController.getStateTournaments(100, GameState.FINISHED);
                tournamentResponse += getTournamentListString(tournamentsFinished);
                return tournamentResponse;
            default:
                return "Invalid <kind>: {game, tournament}";
        }
    }

    private String createGame(String[] tokens) {
        if (tokens.length < 3) return "Required argument: touch <kind> <./configuration.json> [maxPlayers]";

        String kind = tokens[1].toLowerCase();
        String configuration = tokens[2].toLowerCase();

        Gson gson = new Gson();
        ReturnLobbyConfig returnLobbyConfig;
        try (Reader reader = new FileReader(configuration)) {
            returnLobbyConfig = gson.fromJson(reader, ReturnLobbyConfig.class);
        } catch (Exception e) {
            return "Invalid configuration provided: needs to be a file path to a valid configuration.json";
        }

        switch (kind) {
            case "game":
                int lobbyId = serverController.createNewLobby();
                serverController.setConfiguration(lobbyId, returnLobbyConfig);
                return "Created game with id: " + lobbyId;
            case "tournament":
                if (tokens.length <= 3) return "Required argument: touch tournament <./configuration.json> maxPlayers";
                int maxPlayers;
                try {
                    maxPlayers = Integer.parseInt(tokens[3]);
                } catch (NumberFormatException e) {
                    return "Expected the argument to be an integer, but was actually " + tokens[3];
                }
                int tournamentId = serverController.createNewTournament(maxPlayers);
                Tournament tournament = serverController.getTournamentById(tournamentId);
                tournament.setConfiguration(returnLobbyConfig);
                return "Created tournament with id: " + tournamentId;
            default:
                return "Invalid <kind>: {game, tournament}";
        }
    }

    private static String getLobbyListString(List<GameLobby> lobbyList) {
        String response = "";
        for (GameLobby lobby : lobbyList) {
            response += "gameId: ";
            response += lobby.getId();
            response += ", gameName: ";
            String gameName = lobby.getLobbyConfig().getGameName();
            response += gameName == null ? "null" : gameName;
            response += "\n";
        }
        return response;
    }

    private static String getTournamentListString(List<Tournament> tournamentList) {
        String response = "";
        for (Tournament tournament : tournamentList) {
            response += "tournamentId: ";
            response += tournament.getTournamentId();
            response += ", tournamentName: ";
            String tournamentName = tournament.getLobbyConfig() == null ? "null" : tournament.getLobbyConfig().getGameName();
            response += tournamentName;
            response += "\n";
        }
        return response;
    }
}
