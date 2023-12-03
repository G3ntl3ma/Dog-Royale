package com.nexusvision.server.controller;

import com.nexusvision.server.handler.ClientHandler;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * The Server-Controller that can start up the server
 *
 * @author felixwr
 */
public class ServerController {
    @Getter
    private static final ServerController instance = new ServerController();

    private final Logger logger = LogManager.getLogger(ServerController.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final HashMap<Integer, String> clientIDMapName = new HashMap<>();
    private final HashMap<Integer, Boolean> clientIDMapObserver = new HashMap<>();

    private final ArrayList<GameLobby> lobbyList = new ArrayList<>();

    //TODO starting games (list of gameid + currentplayercount + maxpalyercount)
    //TODO running games (list of gameid + currentplayercount + maxplayercount)
    //TODO completed games (list of gameid + winnerplayerid)


    
    private ServerController() {}

    /**
     * Setup <code>ServerSocket</code>, create threads for incoming connections
     * and add those to the thread pool
     *
     * @param port The port being used to start the <code>ServerSocket</code>
     */
    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("ServerSocket started successfully on port " + port);
            logger.info("Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New connection request from " + clientSocket.getInetAddress());

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    public ArrayList<GameLobby> getStartingGames(int gameCount) {
	int foundCount = 0;
	ArrayList<GameLobby> gameLobbys = new ArrayList<>();
	for (int i = 0; i < lobbyList.size(); i++) {
	    GameLobby g = lobbyList.get(i);
	    if(g.gameRunning == false) {
		gameLobbys.add(g);
		foundCount++;
	    }
	    if(foundCount == gameCount) break;
	}
	return gameLobbys;
    }

    public ArrayList<GameLobby> getRunningGames(int gameCount) {
	int foundCount = 0;
	ArrayList<GameLobby> gameLobbys = new ArrayList<>();
	for (int i = 0; i < lobbyList.size(); i++) {
	    GameLobby g = lobbyList.get(i);
	    if(g.gameRunning == true) {
		gameLobbys.add(g);
		foundCount++;
	    }
	    if(foundCount == gameCount) break;
	}
	return gameLobbys;
    }
    
    public boolean addPlayer(int gameId, int clientId) {
	for (int i = 0; i < lobbyList.size(); i++) {
	    if(lobbyList.get(i).getGameID() == gameId) {
		lobbyList.get(i).addPlayer(clientId);
		return true;
	    }
	}
	return false;
    }

    public boolean addObserver(int gameId, int clientId) {
	for (int i = 0; i < lobbyList.size(); i++) {
	    if(lobbyList.get(i).getGameID() == gameId) {
		lobbyList.get(i).addObserver(clientId);
		return true;
	    }
	}
	return false;
    }

    public ArrayList<GameLobby> getFinishedGames(int gameCount) {
	int foundCount = 0;
	ArrayList<GameLobby> gameLobbys = new ArrayList<>();
	for (int i = 0; i < lobbyList.size(); i++) {
	    GameLobby g = lobbyList.get(i);
	    if(g.gameCompleted == true) {
		gameLobbys.add(g);
		foundCount++;
	    }
	    if(foundCount == gameCount) break;
	}
	return gameLobbys;
    }

    public int getGameId(GameLobby g) {
	return g.getGameID();
    }

    public int getCurrentPlayerCount(GameLobby g) {
	return g.getCurrentPlayerCount();
    }
    
    public int getMaxPlayerCount(GameLobby g) {
	return g.playerCount;
    }

    public int generateGameID() {
        // TODO: Sollte besser positiv sein
        //@author Farah-ey wenn Du "ran.nextInt(Zahl);" verwendest werden die Zahlen von 0 bis Zahl ausgewählt und somit positiv)
        Random ran = new Random();
        int newGameID = 0;

        boolean found = true;
        while (found) {
            found = false;
            newGameID = ran.nextInt(Integer.MAX_VALUE);
            for (int i = 0; i < lobbyList.size(); i++) {
                int key = lobbyList.get(i).getGameID();
                if (key == newGameID) {
                    found = true;
                    break;
                }
            }
        }
        return newGameID;
    }

    public int generateClientID() {
        Random ran = new Random();
        int newClientID = 0;

        boolean found = true;
        while (found) {
            found = false;
            newClientID = ran.nextInt(Integer.MAX_VALUE);
            for (Integer key : clientIDMapName.keySet()) {
                if (key == newClientID) {
                    found = true;
                    break;
                }
            }
        }
        clientIDMapName.put(newClientID, null);
        return newClientID;
    }

    public int createNewLobby(ArrayList<Integer> playerOrderList, ArrayList<Integer> observerIDs, ArrayList<Integer> playerColorList) {
        int gameID = generateGameID();
        lobbyList.add( new GameLobby(gameID, playerOrderList, observerIDs, playerColorList));
        return gameID;
    }

    public void setConfiguration( int gameID, int playerCount, int fieldSize, int figuresPerPlayer, List<Integer> drawFieldpositions,
                                  List<Integer>  startFields, int initialCardsPerPlayer, int thinkingTimePerMove,
                                  int consequencesForInvalidMove, int maxGameDuration, int maxTotalMoves) {
        for(int i = 0; i < lobbyList.size(); i++) {
            if(lobbyList.get(i).getGameID() == gameID) {
                lobbyList.get(i).setConfiguration( playerCount,  fieldSize,  figuresPerPlayer,  drawFieldpositions,
                         startFields,  initialCardsPerPlayer,  thinkingTimePerMove,
                 consequencesForInvalidMove,  maxGameDuration,  maxTotalMoves);
            }
        }

    }

    public void setUsername(int clientID, String userName) {
        clientIDMapName.put(clientID, userName);
    }

    public void setObserver(int clientID, boolean isObserver) {
        clientIDMapObserver.put(clientID, isObserver);
    }

    public String getUsername(int clientID) {
        return clientIDMapName.get(clientID);
    }

    public int getGameCount() {
	return lobbyList.size();
    }

    public boolean getObserver(int clientID) {
	    return clientIDMapObserver.get(clientID);
    }

    public boolean clientIdRegistered(int clientId) {
	for (Integer key : clientIDMapName.keySet()) {
	    if (key == clientId) {
		return true;
	    }
	}
	return false;
    }


    /*
    public SpiellogikInstanz getLobby(int lobbyID) {
        // Gebe Spiellogik zurück
    }
     */
}
