package com.nexusvision.server.model.gamelogic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.ConnectToServer;
import com.nexusvision.server.model.messages.menu.ConnectedToServer;
import com.nexusvision.server.model.messages.menu.ReturnLobbyConfig;
import com.nexusvision.server.model.messages.menu.TypeMenue;
import com.google.gson.Gson;
import com.google.gson.*;
import com.nexusvision.utils.NewLineAppendingSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class EngineServerHandler{
    //know own cards by keeping track of all 3.4 messages
    private ArrayList<Card> handcards = new ArrayList<>();
    private int clientId;
    private HashMap<Integer, Integer> handCardCounts = new HashMap<>(); //clientId key
    private Game game;
    private Ai ai;
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    public EngineServerHandler() {
        
    }

    //TODO discard pile is not in order i think
    
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
            connectToServer.setType(TypeMenue.connectToServer.getOrdinal());
            connectToServer.setName("mikeoxlong");
            connectToServer.setObserver(false);
            String tosend = gson.toJson(connectToServer).toString();
            System.out.println("trying to send " + tosend);
            writer.println(tosend);
            writer.flush();
            //receive 2.4 (receive own clienitid)
            // request = reader.readLine();
            // read own clientid
            // ConnectedToServer connectedToServer = gson.fromJson(request, ConnectedToServer.class);
            // this.clientId = connectedToServer.getClientId();

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
            System.out.println("type " + type);
            if (type == TypeMenue.connectedToServer.getOrdinal()+3) {
                handleReceiveClientId(jsonRequest); //2.4
            } else if (type == TypeMenue.returnLobbyConfig.getOrdinal()) {
                handleLobbyConfig(jsonRequest); //2.12
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                handleSynchronizeCardCounts(jsonRequest); //3.5 
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
        //TODO yeah im converting it back to a string eventhough i could have passed it directly
        System.out.println("handle lobby config");
        ReturnLobbyConfig lobbyConfig = gson.fromJson(jsonObject.toString(), ReturnLobbyConfig.class);
        
        System.out.println(lobbyConfig.getFiguresPerPlayer()+ "-" + lobbyConfig.getInitialCardsPerPlayer()+ "-" + lobbyConfig.getMaximumTotalMoves()+ "-" + lobbyConfig.getConsequencesForInvalidMove());
        this.ai = new Ai(100000, lobbyConfig.getThinkTimePerMove()*1000-500);
        
        List<Integer> startFieldsPositions = lobbyConfig.getStartFields().getPositions();
        List<Integer> drawCardFieldsPositions = lobbyConfig.getDrawCardFields().getPositions();
        
        StringBuilder fieldStringBuild = new StringBuilder();
        fieldStringBuild.append("n".repeat(Math.max(0, lobbyConfig.getFieldsize())));

        for (int i = 0; i < startFieldsPositions.size(); i++) {
            System.out.println("startfield" + i + "=" + startFieldsPositions.get(i));
            fieldStringBuild.setCharAt(startFieldsPositions.get(i), 's');
        }

        for (int i = 0; i < drawCardFieldsPositions.size(); i++) {
            System.out.println("drawfield" + i + "=" + drawCardFieldsPositions.get(i));
            fieldStringBuild.setCharAt(drawCardFieldsPositions.get(i), 'k');
        }
        String boardString = fieldStringBuild.toString();
        System.out.println("boardString " + boardString);

        LobbyConfig convertedLobbyConfig = new LobbyConfig();
        convertedLobbyConfig.importLobbyConfig(lobbyConfig);

        this.game = new Game(convertedLobbyConfig, 0);
        game.initDeck();
        game.distributeCards();
        //this.game.printBoard();
    }
    
    private void handleReceiveClientId(JsonObject jsonObject) { //2.4
        this.clientId = jsonObject.get("clientId").getAsInt();
        System.out.println("my client id " + this.clientId);
    }

    //
    public void handleSynchronizeCardCounts(JsonObject jsonObject) { //3.5
        UpdateDrawCards updateDrawCards = gson.fromJson(jsonObject.toString(), UpdateDrawCards.class);
        List<UpdateDrawCards.HandCard> handCards = updateDrawCards.getHandCards();
        for (UpdateDrawCards.HandCard handcard : handCards) {
            int clientId = handcard.getClientId();
            int count = handcard.getCount();
            handCardCounts.put(clientId, count);
        }
        System.out.println("handCardCounts " + handCardCounts); //check
    }
    
    public void handleManageHandCards(JsonObject jsonObject) { //3.4
        DrawCards drawCards = gson.fromJson(jsonObject.toString(), DrawCards.class);
        List<Integer> droppedInts = drawCards.getDroppedCards();
        List<Integer> drawnInts = drawCards.getDrawnCards();

        // Process dropped cards
        for (int dropped : droppedInts) {
            handcards.remove(Card.values()[dropped]);
        }
        // Process drawn cards
        for (int drawn : drawnInts) {
            handcards.add(Card.values()[drawn]);
        }
        System.out.println("handcards " + handcards);
    }

    public String handleLoadBoardJson(JsonObject jsonObject) { //3.3
        System.out.println("handle load board");
        BoardState boardState = gson.fromJson(jsonObject.toString(), BoardState.class);
        System.out.println("game over bool=" + boardState.isGameOver());
        if(boardState.isGameOver()) { 
            System.out.println("game over");
            System.exit(666);
        }
        ////if its our turn make move
        if (boardState.getNextPlayer() != this.clientId) {
             System.out.println("dont load the state because its not our turn");
             return null;
        }
        
        System.out.println("my turn");
        game.setPlayerToMoveId(boardState.getNextPlayer());
        
        //parse field
        //set piece stuff
        for (int i = 0; i < game.getBoard().length; i++) {
            Field field = game.getBoard()[i];
            field.setFigure(null);
        }
        
        for (BoardState.Piece piece : boardState.getPieces()) {
            //set piece in game to this
            int clientId = piece.getClientId();
            int pieceId = piece.getPieceId(); 
            Player player = game.getPlayer(clientId); //get player of clientId

            Figure figure = player.getFigureList().get(pieceId);
            //variables to set
            boolean isOnBench = piece.isOnBench();
            figure.setOnBench(isOnBench);
            game.setMovesMade(boardState.getMoveCount());
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
            if(player.getClientId() != this.clientId) {
                player.setCardList(new ArrayList<>());
            }
        }

        System.out.println("put all unknown vars into an arraylist");
        //put all unknown vars into an arraylist
        //distribute stuffs
        //handcardCounts
        game.setLastCardOnPile(Card.values()[boardState.getLastPlayedCard()]);
        ArrayList<Card> unknownCardPool = new ArrayList<>();
        for (int i = 0; i < game.getPlayerList().size(); i++) {
            if(i != clientId) {
                unknownCardPool.addAll(game.getPlayerList().get(i).getCardList());
            }
        }
        unknownCardPool.addAll(game.getDeck());
        unknownCardPool.addAll(game.getPile());
        
        Collections.shuffle(unknownCardPool);
        game.setDeck(new ArrayList<Card>());
        game.setPile(new ArrayList<Card>());
        int currentId = 0;

        //pile cards are known discardPile
        int pilesize = boardState.getDiscardPile().size();
        for (int i = 0; i < pilesize; i++) {
            int cardix = boardState.getDiscardPile().get(pilesize - i - 1).getCard();
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

        System.out.println("choose move");
        long startTime = System.currentTimeMillis();
        //choose random move
        // Move move = game.getRandomMove();
        //choose ai move
        Move move = this.ai.getMove(game, startTime);
        //TODO put this into a function ?
        MoveValid moveValid = new MoveValid();
        moveValid.setType(TypeGame.moveValid.getOrdinal());
        if(move != null) {
            move.printMove();
            //convert to json
            moveValid.setSkip(false);
            moveValid.setCard(move.getCardUsed().ordinal());
            moveValid.setSelectedValue(move.getSelectedValue());
            Figure playerFigure = move.getPlayerFigure();
            Integer pieceId = null;
            if(playerFigure != null) {
                pieceId = playerFigure.getFigureId();
            }
            moveValid.setPieceId(pieceId);
            moveValid.setStarter(move.isStartMove());
            Integer opponentPieceId = null;
            Figure opponentFigure = game.getBoard()[move.getTo().getFieldId()].getFigure();
            if (opponentFigure != null) {
                opponentPieceId = opponentFigure.getFigureId();
            }
            moveValid.setOpponentPieceId(opponentPieceId);
        }
        else {
            moveValid.setSkip(true);
            moveValid.setCard(-1);
            moveValid.setSelectedValue(-1);
            moveValid.setPieceId(-1);
            moveValid.setStarter(false);
            moveValid.setOpponentPieceId(-1);
        }
        return gson.toJson(moveValid).toString(); //return the move 3.7
    }
}
