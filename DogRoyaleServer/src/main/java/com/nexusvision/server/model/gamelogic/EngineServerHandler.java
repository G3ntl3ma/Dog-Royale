package com.nexusvision.server.model.gamelogic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.messages.game.BoardState;
import com.nexusvision.server.model.messages.game.MoveValid;
import com.nexusvision.server.model.messages.game.TypeGame;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.TypeMenue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collections;

public class EngineServerHandler{
    //know own cards by keeping track of all 3.4 messages
    private ArrayList<Card> handcards = new ArrayList<>();
    private int clientId;
    // private int playerId; //TODO set
    private HashMap<Integer, Integer> handCardCounts = new HashMap<>(); //clientId key //TODO fix clientid vs playerid
    private Game game;
    private HashMap<Integer,Integer> clientIdToPlayerId = new HashMap<>();


    public EngineServerHandler() {
        
    }
    
    public void run(String SERVER_ADDRESS, int PORT) {
        try {        
            Socket clientSocket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                clientSocket.getOutputStream(), false);
            String request, response;
            //send 2.3 (join game with name and isobs bool = false)
            ConnectToServer connectToServer = new ConnectToServer();
            connectToServer.setType();
            connectToServer.setName("mike oxlong");
            writer.println(connectToServer.toString());
            //receive 2.4 (receive own clienitid)
            request = reader.readLine();
            //read own clientid
            ConnectedToServer connectedToServer = gson.fromJson(request, ConnectedToServer.class);
            this.clientId = connectedToServer.getClientId();
            while (true) {
                if ((request = reader.readLine()) != null
                    && (response = handle(request)) != null) {
                    writer.println(response);
                    writer.flush();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String handle(String request) {
        try {
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            int type = jsonRequest.get("type").getAsInt();
            if (type == TypeMenue.connectedToServer.getOrdinal()) {
                handleReceiveClientId(jsonRequest); //2.4
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                handleSynchronizeCardCounts(jsonRequest); //3.5
            } else if (type == TypeMenue.returnLobbyConfig.getOrdinal()) {
                handleLobbyConfig(jsonRequest); //2.12
            } else if (type == TypeGame.drawCards.getOrdinal()) {
                handleManageHandCards(jsonRequest); //3.4
            } else if (type == TypeGame.boardState.getOrdinal()) {
                return handleLoadBoardJson(jsonRequest); //3.3
            }
        } catch (JsonSyntaxException e) {
            return null;
        }
        return null;
    }
    
    //use lobby config to configure the game //dont respond
    private void handleLobbyConfig(JsonObject jsonObject) { //2.12
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
    }
    
    private void handleReceiveClientId(JsonObject jsonObject) { //2.4
        this.clientId = jsonObject.get("clientId").getAsInt();
    }

    //
    public void handleSynchronizeCardCounts(JsonObject jsonObject) { //3.5
        JsonArray _handCards = jsonObject.getAsJsonArray("handCards");
        for (int i = 0; i < _handCards.size(); i++) {
            JsonObject handcard = _handCards.get(i).getAsJsonObject();
            int clientId = handcard.get("clientId").getAsInt();
            int count = jsonObject.get("count").getAsString();
            handCardCounts.put(clientId, count);
        }
    }
    
    public void handleManageHandCards(JsonObject jsonObject) { //3.4
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
    }

    public String handleLoadBoardJson(JsonObject jsonObject) { //3.3
        //TODO this should be done with the class
        String request = jsonObject.toString();
        BoardState boardState = gson.fromJson(request, BoardState.class);
        
        if(boardState.getGameOver() || boardState.getWasCanceled()) {
            System.out.println("game over");
            System.exit(666);
        }
        // int myClientId = jsonObject.get("clientId").getAsInt();
        ////if its our turn make move
        if (boardState.getNextPlayer() != this.clientId) return "";
        game.setPlayerToMoveId(boardState.getNextPlayer);
        
        //TODO parse field
        //set piece stuff
        for (int i = 0; i < game.getBoard().length; i++) {
            Field field = game.getBoard()[i];
            field.setFigure(null);
        }
        
        JsonArray _pieces = jsonObject.getAsJsonArray("pieces");
        // List<Piece> pieces;
        for (BoardState.Piece piece : boardState.getPieces().size()) {
            //set piece in game to this
            int clientId = piece.getClientId();
            // int playerId = clientIdToPlayerId.get(clientId); //TODO ???
            int pieceId = piece.getPieceId(); 
            Player player = game.getPlayerList().get(playerId);
            Figure figure = player.getFigureList().get(pieceId);
            //variables to set
            boolean isOnBench = piece.isOnBench();
            figure.setOnBench(isOnBench);
            game.setMoveCount(boardState.getMoveCount());
            if(isOnBench) {
                figure.setField(null);
                figure.setInHouse(false);
                continue;
            }
            
            int houseInx = piece.getInHousePosition();
            int houseStart = player.getHouseFirstIndex();
            if(houseInx != -1) {
                int fieldInx = houseStart + houseInx;
                Field field = game.getBoard()[fieldInx];
                field.setFigure(figure);
                figure.setField(field);
                figure.setInHouse(true);
            }
            else {
                int fieldInx = piece.getPosition();
                Field field = game.getBoard()[fieldInx];
                field.setFigure(figure);
                figure.setField(field);
                figure.setInHouse(false);
            }
        }
        
        for(Player player : game.getPlayerList()) {
            int figuresInHouse = 0;
            int figuresOnBench = 0;
            for(Figure figure : player.getFigureList()) {
                if(figure.isInHouse()) figuresInHouse++;
                if(figure.isOnBench()) figuresOnBench++;
            }
            player.setFiguresInHouse(figuresInHouse);
            player.setFiguresInBank(figuresOnBench);
            if(player.getPlayerId() != this.playerId) {
                player.setCardList(new ArrayList<Card>());
            }
        }
        
        //TODO set last played card
        //put all unknown vars into an arraylist
        //distribute stuffs
        //handcardCounts
        Integer lastPlayedCardInx = gameState.getLastPlayedCard();
        Card lastPlayedCard = Card.values()[lastPlayedCardInx]; 
        ArrayList<Card> unknownCardPool = new ArrayList<>();
        for (int i = 0; i < game.getPlayerList().size(); i++) {
            if(i != playerId) {
                unknownCardPool.addAll(game.getPlayerList().get(i).getCardList());
            }
        }
        unknownCardPool.addAll(game.getDeck());
        unknownCardPool.addAll(game.getPile());
        //remove last card from pool
        // unknownCardPool.remove(lastPlayedCard);
        
        Collections.shuffle(unknownCardPool);
        game.setDeck(new ArrayList<Card>());
        game.setPile(new ArrayList<Card>());
        int currentId = 0;

        //pile cards are known discardPile
        JsonArray _discardPile = jsonObject.get("discardPile").getAsJsonArray();
        int pilesize = gameState.getDiscardPile().size();
        for (int i = 0; i < pilesize; i++) {
            int cardix = gameState.getDiscardPile()..get(pilesize - i).getCard();
            Card card = Card.values()[cardix];
            unknownCardPool.remove(card);
            game.getPile().add(card);
        }
        
        for(Card card : unknownCardPool) {
            if(currentId >= game.getPlayerList().size()) {
                game.getDeck().add(card);
                continue;
            }
            Player currentPlayer = game.getPlayerList().get(currentId);
            if(currentPlayer.getCardList().size() < handCardCounts.get(currentId)) {
                currentPlayer.getCardList().add(card);
            }
            else {
                currentId++;
            }
        }
        // game.getPile().add(lastPlayedCard);
        
        //choose random move
        Move move = game.getRandomMove();
        
        //convert to json
        MoveValid moveValid = new MoveValid();
        moveValid.setType(TypeGame.moveValid.getOrdinal());
        moveValid.setSkip(false);
        moveValid.setCard(move.getCardUsed().ordinal());
        moveValid.setSelectedValue(move.getSelectedValue());
        int pieceId = game.getBoard()[move.getFrom().getFieldId()].getFigure().getFigureId();
        moveValid.setPieceId(pieceId);
        moveValid.setStarter(move.isStartMove());
        int opponentPieceId = game.getBoard()[move.getTo().getFieldId()].getFigure().getFigureId();
        moveValid.setOpponentPieceId(opponentPieceId);
        

        return moveValid.toString(); //return the move 3.7
    }
}
