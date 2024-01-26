package com.nexusvision.server.model.gamelogic;

import com.nexusvision.server.model.enums.Colors;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.utils.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nexusvision.server.model.gamelogic.EngineServerHandler.gson;

/**
 * @author felixwr
 */
@Log4j2
@Getter
public class LobbyConfig {

    private final HashMap<Colors, Integer> colorMap;
    private final PlayerOrder playerOrder;
    private final List<WinnerOrderElement> winnerOrder;
    private final List<ObserverElement> observerList;
    private String gameName;
    private int maxPlayerCount;
    private int fieldSize;
    private int figuresPerPlayer;
    private DrawCardFields drawCardFields;
    private StartFields startFields;
    private int initialCardsPerPlayer;
    private int thinkTimePerMove;
    private int visualizationTimePerMove;
    private Penalty consequencesForInvalidMove;
    private int maximumGameDuration;
    private int maximumTotalMoves;

    public LobbyConfig() {
        colorMap = new HashMap<>();
        playerOrder = new PlayerOrder();
        playerOrder.setType(null);
        playerOrder.setOrder(new ArrayList<>());
        winnerOrder = new ArrayList<>();
        observerList = new ArrayList<>();
        maxPlayerCount = -1; // this means it isn't set yet
    }

    private static String readJsonFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }

        return contentBuilder.toString();
    }

    public void importLobbyConfig(String gameName,
                                  int maxPlayerCount,
                                  int fieldSize,
                                  int figuresPerPlayer,
                                  List<ColorMapping> colorMap,
                                  DrawCardFields drawCardFields,
                                  StartFields startFields,
                                  int initialCardsPerPlayer,
                                  int thinkTimePerMove,
                                  int visualizationTimePerMove,
                                  int consequencesForInvalidMove,
                                  int maximumGameDuration,
                                  int maximumTotalMoves) {
        this.gameName = gameName;
        this.maxPlayerCount = maxPlayerCount;
        this.fieldSize = fieldSize;
        this.figuresPerPlayer = figuresPerPlayer;

        ArrayList<Integer> playerList = new ArrayList<>(this.colorMap.values());
        playerList.removeIf(clientId -> clientId == -1);
        this.colorMap.clear();
        for (ColorMapping colorMapping : colorMap) {
            Colors color = Colors.values()[colorMapping.getColor()];
            this.colorMap.put(color, playerList.isEmpty() ? -1 : playerList.remove(0));
        }
        for (int clientId : playerList) {
            for (Colors color : Colors.values()) {
                if (!this.colorMap.containsKey(color)) {
                    this.colorMap.put(color, clientId);
                    break;
                }
            }
        }

        this.drawCardFields = drawCardFields;
        this.startFields = startFields;
        this.initialCardsPerPlayer = initialCardsPerPlayer;
        this.thinkTimePerMove = thinkTimePerMove;
        this.visualizationTimePerMove = visualizationTimePerMove;
        this.consequencesForInvalidMove = Penalty.values()[consequencesForInvalidMove];
        this.maximumGameDuration = maximumGameDuration;
        this.maximumTotalMoves = maximumTotalMoves;
    }

    /**
     * Imports the lobby config by making use of the provided object
     *
     * @param returnLobbyConfig The lobby config object being used to import the lobby config
     */
    public void importLobbyConfig(ReturnLobbyConfig returnLobbyConfig) {
        importLobbyConfig(
                returnLobbyConfig.getGameName(),
                returnLobbyConfig.getMaxPlayerCount(),
                returnLobbyConfig.getFieldsize(),
                returnLobbyConfig.getFiguresPerPlayer(),
                returnLobbyConfig.getColors(),
                returnLobbyConfig.getDrawCardFields(),
                returnLobbyConfig.getStartFields(),
                returnLobbyConfig.getInitialCardsPerPlayer(),
                returnLobbyConfig.getThinkTimePerMove(),
                returnLobbyConfig.getVisualizationTimePerMove(),
                returnLobbyConfig.getConsequencesForInvalidMove(),
                returnLobbyConfig.getMaximumGameDuration(),
                returnLobbyConfig.getMaximumTotalMoves()
        );
    }

    /**
     * Imports the lobby config via the file from path
     *
     * @param filePath The path to the json file being used
     */
    public void importLobbyConfig(String filePath) {
        ReturnLobbyConfig returnLobbyConfig;

        try (Reader reader = new FileReader(filePath)) {
            returnLobbyConfig = gson.fromJson(reader, ReturnLobbyConfig.class);
        } catch (Exception e) {
            log.error("Couldn't import lobby config: " + e.getMessage());
            return;
        }

        importLobbyConfig(returnLobbyConfig);
    }

    /**
     * TODO: Export to file
     */
    public void exportLobbyConfig() {

    }

    /**
     * Add the player if possible
     *
     * @param name     The name of the player
     * @param clientId The clientId of the player
     * @return <code>true</code> if added successfully and <code>false</code> if maxPlayerCount is reached
     * or player is added already
     */
    public synchronized boolean addPlayer(String name, int clientId) {
        List<PlayerElement> orderList = playerOrder.getOrder();
        if (playerOrder.find(clientId) != -1 || orderList.size() == maxPlayerCount || orderList.size() == 6) {
            return false;
        }
        PlayerElement player = new PlayerElement();
        player.setName(name);
        player.setClientId(clientId);
        orderList.add(player);
        return true;
    }

    /**
     * Removes the player with <code>clientId</code>
     *
     * @param clientId The clientId that will be removed
     */
    public synchronized void removePlayerByClientId(int clientId) {
        playerOrder.removeClientId(clientId);
    }

    /**
     * Removes the player with the given order index
     *
     * @param orderIndex The index in the order list used to remove the entry
     */
    public synchronized void removePlayerByOrderIndex(int orderIndex) {
        playerOrder.getOrder().remove(orderIndex);
    }

    /**
     * Adds a player with exactly the given color
     *
     * @param color    The color which the player will be added with
     * @param clientId The clientId to add the color with
     */
    public synchronized void addColor(Colors color, int clientId) {
        if (!colorMap.containsKey(color)) {
            colorMap.put(color, clientId);
            return;
        }
        int oldClientId = colorMap.get(color);
        colorMap.put(color, clientId);
        Colors switchColor = findUnusedColor();
        colorMap.put(switchColor, oldClientId);
    }

    public synchronized void removeColor(int clientId) {
        for (Map.Entry<Colors, Integer> entry : colorMap.entrySet()) {
            if (entry.getValue() == clientId) {
                colorMap.remove(entry.getKey());
                return;
            }
        }
    }

    // TODO: Provide a way to change the playerOrder for the Ausrichter

    /**
     * Returns a suited color that is not used yet
     *
     * @return The color
     */
    public synchronized Colors findUnusedColor() {
        for (Map.Entry<Colors, Integer> entry : colorMap.entrySet()) {
            if (entry.getValue() == -1) {
                return entry.getKey();
            }
        }
        for (Colors tempColor : Colors.values()) {
            if (!colorMap.containsKey(tempColor)) {
                return tempColor;
            }
        }
        return null; // Should never happen
    }
}
