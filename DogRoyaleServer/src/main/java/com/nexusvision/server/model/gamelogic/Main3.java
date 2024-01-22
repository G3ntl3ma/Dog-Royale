package com.nexusvision.server.model.gamelogic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//TODO
//for ai
//keep track of if move was valid of current player to build a player is out list
//keep track of players that are being skipped

//for ai need to know the
//missing: how many cards on hand per player
//solution: cards on hand per player is sent by server 3.5
//missing: how many cards in deck
//solution: 110 - #pile - #sumhandcards

//get board state and construct savestate from it 
//convert move to a json and send
//listen to which card we get
public class Main3 {

    //know own cards by keeping track of all 3.4 messages
    private ArrayList<Card> handcards = new ArrayList<>();
    private int clientId;
    private int playerId; //TODO set
    private HashMap<Integer, Integer> handCardCounts = new HashMap<>(); //playerId key
    private Game game;
    private HashMap<Integer, Integer> clientIdToPlayerId = new HashMap<>();

    public static void main(String[] args) {
        String SERVER_ADDRESS = "127.0.0.1";
        int PORT = 8088;
        Socket clientSocket = new Socket(SERVER_ADDRESS, PORT);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(
                clientSocket.getOutputStream(), false);
        String request, response;
        while (true) {
            if ((request = reader.readLine()) != null
                    && (response = handle(request)) != null) {
                writer.println(response);
                writer.flush();
            }
        }
    }

    //TODO move into another class
    private String handle(String request) {
        try {
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            int type = jsonRequest.get("type").getAsInt();
            if (type == TypeMenue.connectedToServer.getOrdinal()) {
                return handleReceiveClientId(jsonRequest); //2.4
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                return handleSynchronizeCardCounts(jsonRequest); //3.5
            } else if (type == TypeMenue.returnLobbyConfig.getOrdinal()) {
                return handleLobbyConfig(jsonRequest); //2.12
            } else if (type == TypeGame.drawCards.getOrdinal()) {
                return handleManageHandCards(jsonRequest); //3.4
            } else if (type == TypeGame.boardState.getOrdinal()) {
                return handleLoadBoardJson(jsonRequest); //3.3
            }
        } catch (JsonSyntaxException e) {
            return "uh oh";
        }
    }

    //use lobby config to configure the game
    private String handleLobbyConfig(JsonObject jsonObject) { //2.12
        //TODO get playerorder
        // ReturnLobbyConfig.PlayerOrder

        //jsonObject.getAsJsonObject("playerOrder").get("type");

        JsonArray playerOrder = jsonObject.getAsJsonObject("playerOrder").getAsJsonArray("order");
        //...
        for (int i = 0; i < playerOrder.size(); i++) {
            int playerId = playerOrder.get(i).getAsJsonObject().get("clientId").getAsInt();
            clientIdToPlayerId.put(i, playerId);
        }

        int figuresPerPlayer = jsonObject.get("figuresPerPlayer").getAsInt();
        int initialCardsPerPlayer = jsonObject.get("initialCardsPerPlayer").getAsInt();

        int maximumTotalMoves = jsonObject.get("maximumTotalMoves").getAsInt();
        int consequencesForInvalidMove = jsonObject.get("consequencesForInvalidMove").getAsInt();

        JsonArray startFields = jsonObject.getAsJsonObject("startFields").getAsJsonArray("positions");
        JsonArray drawCardFields = jsonObject.getAsJsonObject("drawCardFields").getAsJsonArray("positions");

        StringBuilder fieldStringBuild = new StringBuilder();
        fieldStringBuild.append("n".repeat(Math.max(0, startFields.size())));

        for (int i = 0; i < startFields.size(); i++) {
            fieldStringBuild.setCharAt(i, 's');
        }

        for (int i = 0; i < drawCardFields.size(); i++) {
            fieldStringBuild.setCharAt(i, 'k');
        }

        this.game = new Game(fieldStringBuild.toString(), figuresPerPlayer, initialCardsPerPlayer, maximumTotalMoves, consequencesForInvalidMove);
        return "";
    }

    private String handleReceiveClientId(JsonObject jsonObject) { //2.4
        this.clientId = jsonObject.get("clientId").getAsInt();
        return "";
    }

    //
    public String handleSynchronizeCardCounts(JsonObject jsonObject) { //3.5
        JsonArray _handCards = jsonObject.getAsJsonArray("handCards");
        for (int i = 0; i < _handCards.size(); i++) {
            JsonObject jsonObject = _handCards.get(i);
            int clientId = jsonObject.get("clientId").getAsInt();
            //String name = jsonObject.get("count").getAsString();
            //TODO
        }
        return "";
    }

    public String handleManageHandCards(JsonObject jsonObject) { //3.4
        JsonArray droppedInts = jsonObject.getAsJsonArray("droppedCards");
        JsonArray drawnInts = jsonObject.getAsJsonArray("drawnCards");

        // Process dropped cards
        for (int i = 0; i < droppedInts.size(); i++) {
            int dropped = droppedInts.get(i).getAsInt();
            handcards.add(Card.values()[dropped]);
        }
        // Process drawn cards
        for (int i = 0; i < drawnInts.size(); i++) {
            int drawn = drawnInts.get(i).getAsInt();
            handcards.remove(Card.values()[drawn]);
        }

        return "";
    }

    public String handleLoadBoardJson(JsonObject jsonObject) { //3.3
        // int myClientId = jsonObject.get("clientId").getAsInt();
        ////if its our turn make move
        Integer nextPlayer = jsonObject.get("nextPlayer").getAsInt();
        if (nextPlayer != this.clientId) return "";
        game.setPlayerToMoveId(nextPlayer);

        //TODO parse field
        //set piece stuff
        for (int i = 0; i < game.getBoard().length; i++) {
            Field field = game.getBoard()[i];
            field.setFigure(null);
        }

        JsonArray _pieces = jsonObject.getAsJsonArray("pieces");
        for (int i = 0; i < _pieces.size(); i++) {
            // Get the JsonObject at index i
            JsonObject pieceObject = _pieces.get(i).getAsJsonObject();
            //set piece in game to this
            int clientId = pieceObject.get("clientId").getAsInt();
            int playerId = clientIdToPlayerId.get(clientId);
            int pieceId = pieceObject.get("pieceId").getAsInt();
            Player player = game.getPlayerList().get(playerId);
            Figure figure = player.getFigureList().get(pieceId);
            //variables to set
            boolean isOnBench = pieceObject.get("isOnBench").getAsBoolean();
            figure.setOnBench(isOnBench);

            if (isOnBench) {
                figure.setField(null);
                figure.setInHouse(false);
                continue;
            }

            int houseInx = pieceObject.get("inHousePosition").getAsInt();
            int houseStart = player.getHouseFirstIndex();
            if (houseInx != -1) {
                int fieldInx = houseStart + houseInx;
                Field field = game.getBoard()[fieldInx];
                field.setFigure(figure);
                figure.setField(field);
                figure.setInHouse(true);
            } else {
                int fieldInx = pieceObject.get("position").getAsInt();
                Field field = game.getBoard()[fieldInx];
                field.setFigure(figure);
                figure.setField(field);
                figure.setInHouse(false);
            }
        }

        for (Player player : game.getPlayerList()) {
            int figuresInHouse = 0;
            int figuresOnBench = 0;
            for (Figure figure : player.getFigureList()) {
                if (figure.isInHouse()) figuresInHouse++;
                if (figure.isOnBench()) figuresOnBench++;
            }
            player.setFiguresInHouse(figuresInHouse);
            player.setFiguresInBank(figuresOnBench);
            if (player.getPlayerId() != this.playerId) {
                player.setCardList(new ArrayList<Card>());
            }
        }

        //TODO set last played card
        //put all unknown vars into an arraylist
        //distribute stuffs
        //handcardCounts
        Integer lastPlayedCardInt = jsonObject.get("lastPlayedCard").getAsInt();
        Card lastPlayedCard = Card.values()[lastPlayedCardInt];
        ArrayList<Card> unknownCardPool = new ArrayList<>();
        for (int i = 0; i < game.getPlayerList().size(); i++) {
            if (i != playerId) {
                unknownCardPool.addAll(game.getPlayerList().get(i).getCardList());
            }
        }
        unknownCardPool.addAll(game.getDeck());
        unknownCardPool.addAll(game.getPile());
        //remove last card from pool
        unknownCardPool.remove(lastPlayedCard);

        Collections.shuffle(unknownCardPool);
        game.setDeck(new ArrayList<Card>());
        game.setPile(new ArrayList<Card>());
        int currentId = 0;
        for (Card card : unknownCardPool) {
            if (currentId >= game.getPlayerList().size()) {
                //TODO pile cards are known
                game.getDeck().add(card);
                continue;
            }
            Player currentPlayer = game.getPlayerList().get(currentId);
            if (currentPlayer.getCardList().size() < handCardCounts.get(currentId)) {
                currentPlayer.getCardList().add(card);
            } else {
                currentId++;
            }
        }
        game.getPile().add(lastPlayedCard);

        //TODO choose random move
        //TODO convert to json
        //TODO send to server


        //TODO
        ////not super important
        //game.setMoveCount(jsonObject.get("moveCount").getAsInt());

        ////if and of these true just exit
        //Boolean gameOver = jsonObject.get("gameOver").getAsBoolean());
        //Boolean wasCanceled = jsonObject.get("wasCanceled").getAsBoolean();
    }

}
