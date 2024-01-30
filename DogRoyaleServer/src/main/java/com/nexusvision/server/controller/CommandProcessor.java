package com.nexusvision.server.controller;

import com.google.gson.Gson;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {

    private static final ServerController serverController = ServerController.getInstance();

    public String processCommand(String command) {
        ArrayList<String> tokens = parseCommand(command);
        String action = tokens.get(0).toLowerCase();

        switch (action) {
            case "help":
                return help();
            case "ls":
                return showGames(tokens);
            case "touch":
                return createGame(tokens);
            case "exec":
                return runGame(tokens);
            default:
                return "No such command: " + action + "\n";
        }
    }

    private static ArrayList<String> parseCommand(String command) {
        ArrayList<String> tokens = new ArrayList<>();
        String[] tempTokens = command.split(" ");
        String quot = "";
        boolean constructingQuot = false;
        for (String tempToken : tempTokens) {
            if (constructingQuot) {
                if (tempToken.charAt(tempToken.length() - 1) == '"') {
                    constructingQuot = false;
                    quot += tempToken.substring(0, tempToken.length() - 1);
                    tokens.add(quot);
                    continue;
                }
                quot += tempToken + " ";
                continue;
            }
            if (!tempToken.isEmpty() && tempToken.charAt(0) == '"') {
                if (tempToken.charAt(tempToken.length() - 1) == '"') {
                    tokens.add(tempToken.substring(1, tempToken.length() - 1));
                    continue;
                }
                constructingQuot = true;
                quot = tempToken.substring(1) + " ";
                continue;
            }
            tokens.add(tempToken);
        }
        if (constructingQuot) tokens.add(quot);
        return tokens;
    }

    private String help() {
        return "Type 'help' to see this list.\n"
                + "Angles <arg> mean that this argument is required.\n"
                + "Brackets [arg] mean that this argument is optional.\n"
                + "Quotation marks \"file path\" allow you to pass arguments with spaces, such as a filepath"
                + " that contains spaces for example.\n"
                + "---COMMANDS---\n"
                + "'add-player <client-id> <kind> <kind-id>': Adds player with <client-id> to <kind>"
                    + " with <kind-id>. <kind> needs to be one of {game, tournament}\n"
                + "'exec <kind> <kind-id>': Starts <kind> with <kind-id>."
                    + " <kind> needs to be one of {game, tournament}\n"
                + "'help': Shows this list\n"
                + "'kill <game-id>': Cancels the game with <game-id>\n"
                + "'ls <kind>': Lists all of <kind> with all necessary information."
                    + " <kind> needs to be one of {game, tournament}\n"
                + "'pause <game-id>: Pauses the game with <game-id>\n"
                + "'rm-player' <client-id> <kind> <kind-id>: Removes player with <client-id> from <kind>"
                    + " with <kind-id>. <kind> needs to be one of {game, tournament}\n"
                + "'touch <kind> <config-file-path> [maxPlayers]': Creates a <kind> with config from"
                    + " <config-file-path>. <kind> needs to be one of {game, tournament}."
                    + " [maxPlayers] will be ignored when <kind> is game, but is strictly required"
                    + " when <kind> is tournament\n"
                + "'unpause <game-id>: Unpauses the game with <game-id>\n";
    }

    private String showGames(ArrayList<String> tokens) {
        if (tokens.size() < 2) {
            return "Required argument: ls <kind>\n";
        }

        String kind = tokens.get(1).toLowerCase();
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
                return "Invalid <kind>: {game, tournament}\n";
        }
    }

    private String createGame(ArrayList<String> tokens) {
        if (tokens.size() < 3) return "Required argument: touch <kind> <./configuration.json> [maxPlayers]\n";

        String kind = tokens.get(1).toLowerCase();
        String configuration = tokens.get(2);

        Gson gson = new Gson();
        ReturnLobbyConfig returnLobbyConfig;
        try (Reader reader = new FileReader(configuration)) {
            returnLobbyConfig = gson.fromJson(reader, ReturnLobbyConfig.class);
        } catch (Exception e) {
            return "Invalid configuration provided: needs to be a file path to a valid configuration.json\n";
        }

        switch (kind) {
            case "game":
                int lobbyId = serverController.createNewLobby();
                serverController.setConfiguration(lobbyId, returnLobbyConfig);
                return "Created game with id: " + lobbyId + "\n";
            case "tournament":
                if (tokens.size() <= 3) return "Required argument: touch tournament <./configuration.json> maxPlayers\n";
                int maxPlayers;
                try {
                    maxPlayers = Integer.parseInt(tokens.get(3));
                } catch (NumberFormatException e) {
                    return "Expected the argument to be an integer, but was actually " + tokens.get(3) + "\n";
                }
                int tournamentId = serverController.createNewTournament(maxPlayers);
                Tournament tournament = serverController.getTournamentById(tournamentId);
                tournament.setConfiguration(returnLobbyConfig);
                return "Created tournament with id: " + tournamentId + "\n";
            default:
                return "Invalid <kind>: {game, tournament}\n";
        }
    }

    private String runGame(ArrayList<String> tokens) {
        if (tokens.size() < 3) return "Required argument: exec <kind> <kind-id>\n";

        String kind = tokens.get(1).toLowerCase();
        int id;
        try {
            id = Integer.parseInt(tokens.get(2));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(1) + "\n";
        }

        switch (kind) {
            case "game":
                GameLobby game = serverController.getLobbyById(id);
                if (game == null) return "Error: game with gameId " + id + " doesn't exist\n";
                boolean gameSuccessful = game.runGame();
                if (!gameSuccessful) {
                    return "Couldn't start game with gameId " + id + ", game is not ready yet\n";
                }
                return "Started game with gameId " + id + " and name " + game.getLobbyConfig().getGameName()
                        + ": " + game.getGameState() + "\n";
            case "tournament":
                Tournament tournament = serverController.getTournamentById(id);
                if (tournament == null) return "Error: tournament with " + id + " doesn't exist\n";
                boolean tournamentSuccessful = tournament.startTournament();
                if (!tournamentSuccessful) {
                    return "Couldn't start tournament with tournamentId " + id + ", tournament is not ready yet\n";
                }
                return "Started tournament with tournamentId " + id + " and name " + tournament.getLobbyConfig().getGameName()
                        + ": " + tournament.getTournamentState() + "\n";
            default:
                return "Invalid <kind>: {game, tournament}\n";
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
