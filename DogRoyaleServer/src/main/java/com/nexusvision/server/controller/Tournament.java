package com.nexusvision.server.controller;

import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.gamelogic.LobbyConfig;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.utils.TournamentPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

public class Tournament {
    private static final int[] SCORE_LOOKUP_TABLE = {10,7,4,1};
    private final ServerController serverController;
    private final HashMap<Integer, TournamentPlayer> tournamentPlayerMap;
    private final int tournamentId;
    private int maxPlayer;
    private int maxGames;

    private ArrayList<GameLobby> lobbyList;
    private int runningLobbyId;

    private ReturnLobbyConfig lobbyConfig; // TODO: check that lobbyConfig has random PlayerOrder.OrderType
    // TODO: check that maxPlayer in lobbyConfig is 6
    // TODO: Implement a way to set lobbyConfig

    public Tournament(int tournamentId, int maxPlayer) {
        serverController = ServerController.getInstance();
        tournamentPlayerMap = new HashMap<>();
        this.tournamentId = tournamentId;
        this.maxPlayer = maxPlayer;
    }

    public void startTournament() {
        initLobbies();
    }

    private void initLobbies() {
        int[][] lobbyMatrix = randomPartitionPlayers();
        for (int gameIndex = 0; gameIndex < maxGames; gameIndex++) {
            int lobbyId = serverController.createNewLobby();
            GameLobby lobby = serverController.getLobbyById(lobbyId);
            lobbyList.add(lobby);
            lobby.setConfiguration(lobbyConfig);
            for (int lobbyOrderIndex = 0; lobbyOrderIndex < 6; lobbyOrderIndex++) {
                int playerId = lobbyMatrix[gameIndex][lobbyOrderIndex];
                TournamentPlayer player = tournamentPlayerMap.get(playerId);
                lobby.addPlayer(player.getName(), playerId);
            }
        }
    }

    private int[][] randomPartitionPlayers() {
        int lcm = 111111110; // TODO
        maxGames = lcm/6;
        int gamesPerPlayer = lcm/tournamentPlayerMap.size();
        int[][] lobbyMatrix = new int[maxGames][6];

        Queue<Integer> players = new LinkedList<>(tournamentPlayerMap.keySet());
        int player = players.remove();
        int gameCountForPlayer = 0;

        List<Integer> openSlotsForOrderIndex = new ArrayList<>();
        setupUrn(openSlotsForOrderIndex);
        for (int slot : openSlotsForOrderIndex) {
            if (gameCountForPlayer == gamesPerPlayer) {
                player = players.remove();
                gameCountForPlayer = 0;
            }
            lobbyMatrix[slot][0] = player;
            gameCountForPlayer++;
        }

        for (int lobbyOrderIndex = 1; lobbyOrderIndex <= 5; lobbyOrderIndex++) {
            setupUrn(openSlotsForOrderIndex);
            while (!openSlotsForOrderIndex.isEmpty()) {
                int slot = openSlotsForOrderIndex.remove(0);
                if (lobbyMatrix[slot][lobbyOrderIndex-1] == player) {
                    openSlotsForOrderIndex.add(slot);
                    continue;
                }
                if (gameCountForPlayer == gamesPerPlayer) {
                    player = players.remove();
                    gameCountForPlayer = 0;
                }
                lobbyMatrix[slot][lobbyOrderIndex] = player;
                gameCountForPlayer++;
            }
        }

        return lobbyMatrix;
    }

    private void setupUrn(List<Integer> openSlotsForOrderIndex) {
        for (int i = 0; i < maxGames; i++) {
            openSlotsForOrderIndex.add(i);
        }
        Collections.shuffle(openSlotsForOrderIndex);
    }

    public boolean startNextGame() {
        GameLobby currentLobby = lobbyList.get(runningLobbyId);
        if (currentLobby.getGameState() != GameState.FINISHED) return false;
        // TODO: Get winner order and distribute points to players
        runningLobbyId++;
        if (runningLobbyId == lobbyList.size()) return false;

        GameLobby newLobby = lobbyList.get(runningLobbyId);
        newLobby.runGame();
        return true;
    }

    public void addPlayer(int clientId, String name) {
        tournamentPlayerMap.put(clientId, new TournamentPlayer(clientId, name));
    }

    public void removePlayer(int clientId) { // TODO
        for(GameLobby gameLobby : lobbyList) {
            if(gameLobby.getGameState() != GameState.FINISHED) {
                gameLobby.removePlayer(clientId);
            }
        }
    }
}
