package com.nexusvision.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.common.ChannelType;
import com.nexusvision.server.controller.GameLobby;
import com.nexusvision.server.controller.ServerController;
import com.nexusvision.server.controller.Tournament;
import com.nexusvision.server.model.entities.Client;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.gamelogic.LobbyConfig;
import com.nexusvision.server.model.messages.menu.ConnectedToGame;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.nexusvision.server.model.utils.ObserverElement;
import com.nexusvision.server.model.utils.PlayerElement;
import com.nexusvision.server.model.utils.PlayerOrder;
import com.nexusvision.server.service.PlayerService;
import com.nexusvision.utils.NewLineAppendingSerializer;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class CommandProcessor {

    private static final ServerController serverController = ServerController.getInstance();
    private static final Gson gson = new Gson();

    private final PlayerService playerService;

    public CommandProcessor() {
        playerService = new PlayerService();
    }

    public String processCommand(String command) {
        ArrayList<String> tokens = parseCommand(command);
        String action = tokens.get(0).toLowerCase();

        switch (action) {
            case "add-player":
                return addPlayer(tokens);
            case "exec":
                return runGame(tokens);
            case "help":
                return help();
            case "kill":
                return cancelGame(tokens);
            case "ls":
                return showGames(tokens);
            case "pause":
                return pauseGame(tokens);
            case "unpause":
                return unpauseGame(tokens);
            case "touch":
                return createGame(tokens);
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
                    + " <kind> needs to be one of {client, game, tournament}\n"
                + "'pause <game-id>': Pauses the game with <game-id>\n"
                + "'rm-player <client-id> <kind> <kind-id>': Removes player with <client-id> from <kind>"
                    + " with <kind-id>. <kind> needs to be one of {game, tournament}\n"
                + "'touch <kind> <config-file-path> [maxPlayers]': Creates a <kind> with config from"
                    + " <config-file-path>. <kind> needs to be one of {game, tournament}."
                    + " [maxPlayers] will be ignored when <kind> is game, but is strictly required"
                    + " when <kind> is tournament\n"
                + "'unpause <game-id>': Unpauses the game with <game-id>\n";
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
                lobbyResponse += "---------------\n";
                List<GameLobby> upcomingLobbyList = serverController.getStateGames(100, GameState.UPCOMING);
                lobbyResponse += getLobbyListString(upcomingLobbyList);
                lobbyResponse += "---------------\n";
                lobbyResponse += " \n";
                lobbyResponse += "Running games:\n";
                lobbyResponse += "---------------\n";
                List<GameLobby> runningLobbyList = serverController.getStateGames(100, GameState.RUNNING);
                lobbyResponse += getLobbyListString(runningLobbyList);
                lobbyResponse += "---------------\n";
                lobbyResponse += " \n";
                lobbyResponse += "Finished games:\n";
                lobbyResponse += "---------------\n";
                List<GameLobby> finishedLobbyList = serverController.getStateGames(100, GameState.FINISHED);
                lobbyResponse += getLobbyListString(finishedLobbyList);
                lobbyResponse += "---------------\n";
                return lobbyResponse;
            case "tournament":
                String tournamentResponse = "";
                tournamentResponse += "Upcoming tournaments:\n";
                tournamentResponse += "---------------\n";
                List<Tournament> tournamentsUpcoming = serverController.getStateTournaments(100, GameState.UPCOMING);
                tournamentResponse += getTournamentListString(tournamentsUpcoming);
                tournamentResponse += "---------------\n";
                tournamentResponse += " \n";
                tournamentResponse += "Running tournaments:\n";
                tournamentResponse += "---------------\n";
                List<Tournament> tournamentsRunning = serverController.getStateTournaments(100, GameState.RUNNING);
                tournamentResponse += getTournamentListString(tournamentsRunning);
                tournamentResponse += "---------------\n";
                tournamentResponse += " \n";
                tournamentResponse += "Finished tournaments:\n";
                tournamentResponse += "---------------\n";
                List<Tournament> tournamentsFinished = serverController.getStateTournaments(100, GameState.FINISHED);
                tournamentResponse += getTournamentListString(tournamentsFinished);
                tournamentResponse += "---------------\n";
                return tournamentResponse;
            case "client":
                String clientResponse = "";
                Set<Map.Entry<Integer, Client>> clientEntrySet = serverController.getClientEntrySet();

                clientResponse += "observers:\n";
                for (Map.Entry<Integer, Client> entry : clientEntrySet) {
                    Client client = entry.getValue();
                    int id = entry.getKey();
                    if (client.isObserver()) {
                        clientResponse += "    client-id: " + id + ", client-name: " + client.getName() + "\n";
                    }
                }

                clientResponse += "players:\n";
                for (Map.Entry<Integer, Client> entry : clientEntrySet) {
                    Client client = entry.getValue();
                    int id = entry.getKey();
                    if (!client.isObserver()) {
                        clientResponse += "    client-id: " + id + ", client-name: " + client.getName() + "\n";
                    }
                }

                return clientResponse;
            default:
                return "Invalid <kind>: {client, game, tournament}\n";
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
            return "Expected the argument to be an integer, but was actually " + tokens.get(2) + "\n";
        }

        switch (kind) {
            case "game":
                GameLobby game = serverController.getLobbyById(id);
                if (game == null) return "Error: game with game-id " + id + " doesn't exist\n";
                boolean gameSuccessful = game.runGame();
                if (!gameSuccessful) {
                    return "Couldn't start game with game-id " + id + ", game is not ready yet\n";
                }
                return "Started game with game-id " + id + " and name " + game.getLobbyConfig().getGameName()
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

    private String addPlayer(ArrayList<String> tokens) {
        if (tokens.size() < 4) return "Required argument: add-player <client-id> <kind> <kind-id>\n";

        String kind = tokens.get(2).toLowerCase();
        int clientId;
        try {
            clientId = Integer.parseInt(tokens.get(1));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(1) + "\n";
        }
        int kindId;
        try {
            kindId = Integer.parseInt(tokens.get(3));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(3) + "\n";
        }

        Client client = serverController.getClientById(clientId);
        if (client == null || client.isObserver()) return "Provided client-id is invalid\n";

        switch (kind) {
            case "game":
                GameLobby lobby = serverController.getLobbyById(kindId);
                if (lobby == null) return "Provided game-id is invalid\n";
                boolean successfulGame = playerService.movePlayerToLobby(clientId, lobby);
                if (!successfulGame) return "Couldn't add player with client-id " + clientId + "\n";
                break;
            case "tournament":
                Tournament tournament = serverController.getTournamentById(kindId);
                if (tournament == null) return "Provided tournament-id is invalid\n";
                boolean successfulTournament = tournament.addPlayer(clientId, client.getName());
                if (!successfulTournament) return "Couldn't add player with client-id " + clientId + "\n";
                break;
            default:
                return "Invalid <kind>: {game, tournament}\n";
        }

        return "Added player with client-id " + clientId + " to " + kind + " with " + kind + "-id " + kindId + "\n";
    }

    private String cancelGame(ArrayList<String> tokens) {
        if (tokens.size() < 2) return "Required argument: kill <game-id>\n";

        int gameId;
        try {
            gameId = Integer.parseInt(tokens.get(1));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(1) + "\n";
        }
        GameLobby game = serverController.getLobbyById(gameId);
        if (game == null) return "Error: game with game-id " + gameId + " doesn't exist\n";
        game.cancelGame();
        return "Canceled game with game-id " + gameId + "\n";
    }

    private String pauseGame(ArrayList<String> tokens) {
        if (tokens.size() < 2) return "Required argument: pause <game-id>\n";

        int gameId;
        try {
            gameId = Integer.parseInt(tokens.get(1));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(1) + "\n";
        }
        GameLobby game = serverController.getLobbyById(gameId);
        if (game == null) return "Error: game with game-id " + gameId + " doesn't exist\n";
        game.pauseGame();
        return "Paused game with game-id " + gameId + "\n";
    }

    private String unpauseGame(ArrayList<String> tokens) {
        if (tokens.size() < 2) return "Required argument: unpause <game-id>\n";

        int gameId;
        try {
            gameId = Integer.parseInt(tokens.get(1));
        } catch (NumberFormatException e) {
            return "Expected the argument to be an integer, but was actually " + tokens.get(1) + "\n";
        }
        GameLobby game = serverController.getLobbyById(gameId);
        if (game == null) return "Error: game with game-id " + gameId + " doesn't exist\n";
        game.unpauseGame();
        return "Unpaused game with game-id " + gameId + "\n";
    }

    private static String getLobbyListString(List<GameLobby> lobbyList) {
        String response = "";
        for (GameLobby lobby : lobbyList) {
            LobbyConfig lobbyConfig = lobby.getLobbyConfig();
            response += "game-id: ";
            response += lobby.getId();
            response += ", game-name: ";
            String gameName = lobby.getLobbyConfig().getGameName();
            response += gameName == null ? "null" : gameName;
            response += "\n";
            response += "    players: [";
            Iterator<PlayerElement> playerIterator = lobbyConfig.getPlayerOrder().getOrder().iterator();
            while (playerIterator.hasNext()) {
                PlayerElement playerElement = playerIterator.next();
                response += "{client-id=" + playerElement.getClientId() + ", name=" + playerElement.getName() + "}";
                if (playerIterator.hasNext()) response += ", ";
            }
            response += "]\n";
            response += "    observers: [";
            Iterator<ObserverElement> observerIterator = lobbyConfig.getObserverList().iterator();
            while (observerIterator.hasNext()) {
                ObserverElement observerElement = observerIterator.next();
                response += "{client-id=" + observerElement.getClientId() + ", name=" + observerElement.getName() + "}";
                if (observerIterator.hasNext()) response += ", ";
            }
            response += "]\n";
        }
        return response;
    }

    private static String getTournamentListString(List<Tournament> tournamentList) {
        String response = "";
        for (Tournament tournament : tournamentList) {
            response += "tournament-id: ";
            response += tournament.getTournamentId();
            response += "\n";
            response += "    players: [";
            Iterator<PlayerElement> playerIterator = tournament.getPlayerElements().iterator();
            while (playerIterator.hasNext()) {
                PlayerElement playerElement = playerIterator.next();
                response += "{client-id=" + playerElement.getClientId() + ", name=" + playerElement.getName() + "}";
                if (playerIterator.hasNext()) response += ", ";
            }
            response += "]\n";
        }
        return response;
    }
}
