package com.nexusvision.server.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nexusvision.server.model.enums.GameState;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.messages.menu.ReturnTournamentInfo;
import com.nexusvision.server.model.utils.PlayerElement;
import com.nexusvision.server.model.utils.TournamentPlayer;
import com.nexusvision.server.model.utils.WinnerOrderElement;
import com.nexusvision.utils.NewLineAppendingSerializer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.FileReader;
import java.io.Reader;
import java.math.BigInteger;
import java.util.*;

/**
 * @author felixwr
 */
@Log4j2
public class Tournament {
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    private static final int[] SCORE_LOOKUP_TABLE = {10,7,4,1};
    private final ServerController serverController;
    private final HashMap<Integer, TournamentPlayer> tournamentPlayerMap;
    private final ArrayList<GameLobby> lobbyList;
    @Getter
    private final int tournamentId;
    @Getter
    private int maxPlayer;
    @Getter
    private int maxGames;

    private int runningLobbyIndex;

    @Getter
    private GameState tournamentState;

    private ReturnLobbyConfig lobbyConfig; // TODO: check that lobbyConfig has random PlayerOrder.OrderType
    // TODO: check that maxPlayer in lobbyConfig is 6
    // TODO: Implement a way to set lobbyConfig

    public Tournament(int tournamentId, int maxPlayer) {
        serverController = ServerController.getInstance();
        tournamentPlayerMap = new HashMap<>();
        lobbyList = new ArrayList<>();
        this.tournamentId = tournamentId;
        this.maxPlayer = maxPlayer;
        tournamentState = GameState.UPCOMING;
    }

    public void startTournament() {
        initLobbies();
        GameLobby newLobby = lobbyList.get(runningLobbyIndex);
        newLobby.runGame();
        tournamentState = GameState.RUNNING;
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
        int currentPlayerCount = tournamentPlayerMap.size();
        int lcm = 6 * currentPlayerCount / BigInteger.valueOf(6).gcd(BigInteger.valueOf(currentPlayerCount)).intValue(); // least common multiple
        maxGames = lcm/6;
        int gamesPerPlayer = lcm/currentPlayerCount;
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
        openSlotsForOrderIndex.clear();

        for (int lobbyOrderIndex = 1; lobbyOrderIndex <= 5; lobbyOrderIndex++) {
            setupUrn(openSlotsForOrderIndex);
            while (!openSlotsForOrderIndex.isEmpty()) {
                if (gameCountForPlayer == gamesPerPlayer) {
                    player = players.remove();
                    gameCountForPlayer = 0;
                }
                int slot = openSlotsForOrderIndex.remove(0);
                if (lobbyMatrix[slot][lobbyOrderIndex-1] == player) {
                    openSlotsForOrderIndex.add(slot);
                    continue;
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

    /**
     * Starts the next game and cleans up the current game
     *
     * @return <code>false</code> if old game is still running or next game doesn't exist, else <code>true</code>
     */
    public boolean startNextGameAndFinishUpCurrent() {
        GameLobby currentLobby = lobbyList.get(runningLobbyIndex);
        if (currentLobby.getGameState() != GameState.FINISHED) return false;
        // TODO: Get winner order and distribute points to players
        List<WinnerOrderElement> winnerOrderElementList = currentLobby.getLobbyConfig().getWinnerOrder();
        for (int i = 0; i < Math.min(4, winnerOrderElementList.size()); i++) {
            WinnerOrderElement winnerOrderElement = winnerOrderElementList.get(i);
            int playerId = winnerOrderElement.getClientId();
            TournamentPlayer player = tournamentPlayerMap.get(playerId);
            player.setPoints(player.getPoints() + SCORE_LOOKUP_TABLE[i]);
        }
        runningLobbyIndex++;
        if (runningLobbyIndex >= maxGames) {
            tournamentState = GameState.FINISHED;
            return false;
        }

        GameLobby newLobby = lobbyList.get(runningLobbyIndex);
        newLobby.runGame();
        return true;
    }

    /**
     * Adds a player to the tournament
     *
     * @param clientId The client id of the player to add
     * @param name The name of the player to add
     * @return True if player was added
     */
    public boolean addPlayer(int clientId, String name) {
        if (tournamentState != GameState.UPCOMING || maxPlayer == getCurrentPlayerCount()) return false;
        tournamentPlayerMap.put(clientId, new TournamentPlayer(clientId, name));
        return true;
    }

    /**
     * Remove the player with the provided client id
     *
     * @param clientId The client id of the player to remove
     */
    public void removePlayer(int clientId) {
        for (GameLobby gameLobby : lobbyList) {
            if(gameLobby.getGameState() != GameState.FINISHED) {
                gameLobby.removePlayer(clientId);
            }
        }
        tournamentPlayerMap.remove(clientId);
    }

    /**
     * Returns the current winner order of the tournament sorted in descending order based on the points
     *
     * @return The current winner order of the tournament sorted in descending order based on the points
     */
    public List<WinnerOrderElement> getWinnerOrder() {
        List<WinnerOrderElement> winnerOrder = new ArrayList<>();
        for (TournamentPlayer player : tournamentPlayerMap.values()) {
            WinnerOrderElement winnerOrderElement = new WinnerOrderElement();
            winnerOrderElement.setClientId(player.getClientId());
            winnerOrderElement.setName(player.getName());
            winnerOrderElement.setPoints(player.getPoints());
            winnerOrder.add(winnerOrderElement);
        }
        winnerOrder.sort(Comparator.comparingInt(WinnerOrderElement::getPoints).reversed());
        return winnerOrder;
    }

    public List<PlayerElement> getPlayerElements() {
        List<PlayerElement> players = new ArrayList<>();
        for (TournamentPlayer player : tournamentPlayerMap.values()) {
            PlayerElement playerElement = new PlayerElement();
            playerElement.setClientId(player.getClientId());
            playerElement.setName(player.getName());
            players.add(playerElement);
        }
        return players;
    }

    public ReturnTournamentInfo.TournamentInfo.RunningGame getRunningGame() {
        ReturnTournamentInfo.TournamentInfo.RunningGame runningGame = new ReturnTournamentInfo.TournamentInfo.RunningGame();
        runningGame.setGameId(getGameRunning());
        runningGame.setPlayerCount(getCurrentPlayerCount());
        runningGame.setMaxPlayerCount(maxPlayer);
        runningGame.setCurrentRound(getCurrentRound());
        List<TournamentPlayer> players = getTournamentPlayers(lobbyList.get(runningLobbyIndex));
        runningGame.setPlayers(players);
        return runningGame;
    }

    public List<ReturnTournamentInfo.TournamentInfo.UpcomingGames> getUpcomingGamesList() {
        List<ReturnTournamentInfo.TournamentInfo.UpcomingGames> upcomingGamesList = new ArrayList<>();
        for (int i = runningLobbyIndex + 1; i < maxGames; i++) {
            GameLobby lobby = lobbyList.get(i);
            ReturnTournamentInfo.TournamentInfo.UpcomingGames upcomingGame = new ReturnTournamentInfo.TournamentInfo.UpcomingGames();
            upcomingGame.setGameId(lobby.getId());
            upcomingGame.setPlayers(getTournamentPlayers(lobby));
            upcomingGamesList.add(upcomingGame);
        }
        return upcomingGamesList;
    }

    public List<ReturnTournamentInfo.TournamentInfo.FinishedGames> getFinishedGamesList() {
        List<ReturnTournamentInfo.TournamentInfo.FinishedGames> finishedGamesList = new ArrayList<>();
        for (int i = 0; i < Math.min(runningLobbyIndex, maxGames); i++) {
            GameLobby lobby = lobbyList.get(i);
            ReturnTournamentInfo.TournamentInfo.FinishedGames finishedGame = new ReturnTournamentInfo.TournamentInfo.FinishedGames();
            finishedGame.setGameId(lobby.getId());
            finishedGame.setWasCanceled(lobby.isCanceled());
            finishedGame.setWinnerOrder(getTournamentPlayers(lobby));
            finishedGamesList.add(finishedGame);
        }
        return finishedGamesList;
    }

    private List<TournamentPlayer> getTournamentPlayers(GameLobby lobby) {
        List<TournamentPlayer> players = new ArrayList<>();
        for (PlayerElement playerElement : lobby.getLobbyConfig().getPlayerOrder().getOrder()) {
            players.add(tournamentPlayerMap.get(playerElement.getClientId()));
        }
        return players;
    }

    public List<TournamentPlayer> getCurrentRankings() {
        List<TournamentPlayer> currentRankings = new ArrayList<>(tournamentPlayerMap.values());
        currentRankings.sort(Comparator.comparingInt(TournamentPlayer::getPoints).reversed());
        return currentRankings;
    }

    /**
     * Returns the current player count
     *
     * @return The current player count
     */
    public int getCurrentPlayerCount() {
        return tournamentPlayerMap.size();
    }

    /**
     * Returns the id of the currently running game
     *
     * @return The id of the currently running game
     */
    public int getGameRunning() {
        return lobbyList.get(runningLobbyIndex).getId();
    }

    /**
     * Returns the number of the current round (playing through one lobby is one round)
     *
     * @return The number of the current round
     */
    public int getCurrentRound() {
        return runningLobbyIndex+1;
    }

    public void setConfiguration(ReturnLobbyConfig returnLobbyConfig) {
        if (tournamentState != GameState.UPCOMING) return;
        lobbyConfig = returnLobbyConfig;
    }

    public void setConfiguration(String filePath) {
        ReturnLobbyConfig returnLobbyConfig;

        try (Reader reader = new FileReader(filePath)) {
            returnLobbyConfig = gson.fromJson(reader, ReturnLobbyConfig.class);
        } catch (Exception e) {
            log.error("Couldn't import lobby config: " + e.getMessage());
            return;
        }

        setConfiguration(returnLobbyConfig);
    }
}
