package com.nexusvision.server.model.gamelogic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nexusvision.server.model.enums.Card;
import com.nexusvision.server.model.enums.Penalty;
import com.nexusvision.server.model.messages.game.*;
import com.nexusvision.server.model.messages.menu.*;
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
    private ArrayList<Card> handcards = new ArrayList<>(); //first time parsing 3.3
    private int clientId;
    private HashMap<Integer, Integer> handCardCounts = new HashMap<>(); //clientId key
    private Game game;
    private Ai ai;
    private BoardState boardState;
    private int initialCardsPerPlayer=0;
    private State state;
    private boolean sentNull = false;
    private Scanner userInput = new Scanner(System.in);
    private boolean myTurn = false;
    
    protected static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Object.class, new NewLineAppendingSerializer<>())
            .create();

    public EngineServerHandler() {
        this.state = State.WAIT_SYNC_MOVE;
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
            connectToServer.setType(TypeMenue.connectToServer.getOrdinal());
            connectToServer.setName("mike oxlong");
            connectToServer.setObserver(false);
            String tosend = gson.toJson(connectToServer).toString();
            System.out.println("trying to send " + tosend);
            writer.println(tosend);
            writer.flush();

            //extra stuffs TODO delete
            request = reader.readLine();
            JsonObject jsonRequest = JsonParser.parseString(request).getAsJsonObject();
            int type = jsonRequest.get("type").getAsInt();
            handleReceiveClientId(jsonRequest); //2.4
            
            // System.out.println("enter game id");
            String gameId = "0"; //this.userInput.nextLine();
            JoinGameAsPlayer joinGameAsPlayer = new JoinGameAsPlayer();
            joinGameAsPlayer.setClientId(this.clientId);
            joinGameAsPlayer.setPlayerName("mike oxlong");
            joinGameAsPlayer.setType(TypeMenue.joinGameAsPlayer.getOrdinal());
            tosend = gson.toJson(joinGameAsPlayer).toString();
            writer.println(tosend);
            writer.flush();

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
            // System.out.println("type " + type);
            System.out.println("received request " + request);
            if (type == TypeMenue.connectedToServer.getOrdinal()) {
                handleReceiveClientId(jsonRequest); //2.4
            } else if (type == TypeMenue.returnLobbyConfig.getOrdinal()) {
                handleLobbyConfig(jsonRequest); //2.12
            } else if (type == TypeGame.updateDrawCards.getOrdinal()) {
                handleSynchronizeCardCounts(jsonRequest); //3.5
                if (this.state == State.WAIT_SYNC_MOVE) {
                    System.out.println("send the move");
                    this.state = State.SEND_MOVE;
                }
                else {
                    System.out.println("we were not waiting for this");
                    this.state = State.NO_MOVE;
                }
            } else if (type == TypeGame.drawCards.getOrdinal()) {
                handleManageHandCards(jsonRequest); //3.4 
            } else if (type == TypeGame.boardState.getOrdinal()) {
                handleLoadBoardJson(jsonRequest); //3.3
                loadBoardState();
                if (this.handcards.size() == 0) {
                    System.out.println("wait sync move");
                    this.state = State.WAIT_SYNC_MOVE;
                }
                else {
                    System.out.println("send move immediately");
                    this.state = State.SEND_MOVE;
                }
            } else if (type == TypeGame.moveValid.getOrdinal()) {
                handleMoveValid(jsonRequest); //3.8
            } else if (type == TypeGame.kick.getOrdinal()) {
                handleKick(jsonRequest); //3.14
            }
            if (state == State.SEND_MOVE) {
                if(this.clientId == this.game.getCurrentPlayer().getClientId()) {
                    // System.out.println("myTurn " + this.myTurn + "my client id " + this.clientId + " to move client id " + this.game.getCurrentPlayer().getClientId());
                    state = State.NO_MOVE;
                    // return sendManualMove();
                    return sendMove();
                }
            }
        } catch (JsonSyntaxException e) {
            return null;
        }
        return null;
    }
    
    private void handleMoveValid(JsonObject jsonObject) { //3.8
        MoveValid moveValid = gson.fromJson(jsonObject.toString(), MoveValid.class);
        boolean validMove = moveValid.isValidMove();
        // if(!validMove && !this.sentNull) {//this check has false positives when opponent makes null move
        //     System.out.println("++++++++++++++++++++++++++++");
        //     System.out.println("invalid move was passed to the server (not null)");
        //     System.out.println("selected value " + moveValid.getSelectedValue() + " pieceId " + moveValid.getPieceId() + " isStarter " + moveValid.isStarter() + " opponentPieceId " + moveValid.getOpponentPieceId());
        //     System.out.println("++++++++++++++++++++++++++++");
        //     System.out.println("waiting for input to resume");
        //     this.userInput.nextLine();
        // }
    }

    private void handleKick(JsonObject jsonObject) { //3.14
        Kick kick = gson.fromJson(jsonObject.toString(), Kick.class);
        int clientId = kick.getClientId();
        String reason = kick.getReason();
        this.game.excludeFromGame(clientId);
        System.out.println("player " + clientId + " was kicked. reason: " + reason);
    }
    
    //use lobby config to configure the game //dont respond
    private void handleLobbyConfig(JsonObject jsonObject) { //2.12
        // TODO yeah im converting it back to a string eventhough i could have passed it directly
        
        System.out.println("handle lobby config");
        ReturnLobbyConfig lobbyConfig = gson.fromJson(jsonObject.toString(), ReturnLobbyConfig.class);

        this.ai = new Ai(100000, lobbyConfig.getThinkTimePerMove()*1000-500); //???
        //dont handle if its not the final thing
        //if startfields < maxplayers dont do anything
        if (lobbyConfig.getStartFields().getCount() < lobbyConfig.getMaxPlayerCount()) {
            System.out.println("dont parse the lobbyconfig yet");
            return;
        }
        this.initialCardsPerPlayer = lobbyConfig.getInitialCardsPerPlayer();
        System.out.println(lobbyConfig.getFiguresPerPlayer()+ "-" + lobbyConfig.getInitialCardsPerPlayer()+ "-" + lobbyConfig.getMaximumTotalMoves()+ "-" + lobbyConfig.getConsequencesForInvalidMove());
        
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
        convertedLobbyConfig.importLobbyConfig2(lobbyConfig);

        this.game = new Game(convertedLobbyConfig, 0);
        game.initDeck(); //TODO might also be unnecessary
        // game.distributeCards(); //dont
        this.myTurn = true;
        ////if its our turn make move
        int clientToMove = this.game.getCurrentPlayer().getClientId();
        if (clientToMove != this.clientId) { 
            System.out.println("dont load the state because its not our turn our clientId" + this.clientId + " to move " + clientToMove);
            this.myTurn = false;
            System.out.println("myturn= " + this.myTurn);
            return;
        }
    }
    
    private void handleReceiveClientId(JsonObject jsonObject) { //2.4
        this.clientId = jsonObject.get("clientId").getAsInt();
        System.out.println("my client id " + this.clientId);
    }

    //
    public void handleSynchronizeCardCounts(JsonObject jsonObject) { //3.5
        System.out.println("handlesynchronizecardcounts");
        UpdateDrawCards updateDrawCards = gson.fromJson(jsonObject.toString(), UpdateDrawCards.class);
        List<UpdateDrawCards.HandCard> handCards = updateDrawCards.getHandCards();
        handCardCounts.clear();
        for (UpdateDrawCards.HandCard handcard : handCards) {
            int clientId = handcard.getClientId();
            int count = handcard.getCount();
            handCardCounts.put(clientId, count);
        }

    }
    
    public void handleManageHandCards(JsonObject jsonObject) { //3.4
        System.out.println("handlemanagehandcards");
        DrawCards drawCards = gson.fromJson(jsonObject.toString(), DrawCards.class);
        List<Integer> droppedInts = drawCards.getDroppedCards();
        List<Integer> drawnInts = drawCards.getDrawnCards();
        // Process dropped cards
        for (int dropped : droppedInts) {
            handcards.remove(Card.getType(dropped));
        }
        // Process drawn cards
        for (int drawn : drawnInts) {
            handcards.add(Card.getType(drawn));
        }
        System.out.println("handcards " + handcards);
    }
    
    public void handleLoadBoardJson(JsonObject jsonObject) { //3.3
        System.out.println("handle load board");
        this.boardState = null;
        try {
            this.boardState = gson.fromJson(jsonObject.toString(), BoardState.class);
        } catch (JsonSyntaxException e) {
            System.out.println("json syntax error in handle load board json");
            e.printStackTrace();
        } catch (JsonParseException e) {
            System.out.println("json parsing error in handle load board json");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBoardState() {
        System.out.println("++++++++++++load board state+++++++");
        // System.out.println("game over bool=" + this.boardState.isGameOver());
        if(this.boardState.isGameOver()) {
            this.game.printBoard();
            System.out.println("game over");
            System.exit(666);
        }
        
        ////if its our turn make move
        // int clientToMove = this.game.getCurrentPlayer().getClientId();
        // if (clientToMove != this.clientId) {
        //     System.out.println("dont load the state because its not our turn our clientId" + this.clientId + " to move " + clientToMove);
        //     return;
        // }
        // System.out.println("my turn");
        // this.myTurn = true;
        
        game._setPlayerToMoveId(this.boardState.getNextPlayer());
        
        //parse field
        //set piece stuff
        for (int i = 0; i < game.getBoard().length; i++) {
            Field field = game.getBoard()[i];
            field.setFigure(null);
        }
        System.out.println("players in game: " + game.getPlayerList().size());
        for (BoardState.Piece piece : this.boardState.getPieces()) {
            //set piece in game to this
            int clientId = piece.getClientId();
            //if this is the first time parsing
            //if clientId is not in keys of handcardcounts
            if(!handCardCounts.containsKey(clientId)) {
                handCardCounts.put(clientId, this.initialCardsPerPlayer);
            }
            
            int pieceId = piece.getPieceId(); 
            Player player = game.getPlayer(clientId); //get player of clientId

            Figure figure = player.getFigure(pieceId);
            //variables to set
            boolean isOnBench = piece.isOnBench();
            figure.setOnBench(isOnBench);
            game.setMovesMade(this.boardState.getMoveCount());
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
        int lastPlayedCardInt = boardState.getLastPlayedCard(); //TODO make sure last card is in pile
        game.setLastCardOnPile(lastPlayedCardInt == -1 ? null : Card.getType(lastPlayedCardInt));
        ArrayList<Card> unknownCardPool = new ArrayList<>();
        game.fillWithAllCards(unknownCardPool);
        game.setDeck(new ArrayList<Card>()); //ZERO
        game.setPile(new ArrayList<Card>()); //add the lastplayedcard
        for(Player player : game.getPlayerList()) {
            player.setCardList(new ArrayList<Card>());
        }
            
        // subtract the known handcards
        for (Card handCard : handcards) {
            unknownCardPool.remove(handCard);
            game.getCurrentPlayer().getCardList().add(handCard);
        }
        
        // subtract the known pile cards
        int pilesize = boardState.getDiscardPile().size();
        for (int i = 0; i < pilesize; i++) {
            int cardix = boardState.getDiscardPile().get(pilesize - i - 1).getCard();
            Card card = Card.getType(cardix);
            unknownCardPool.remove(card);
            game.getPile().add(card); 
        }
        
        //then shuffle
        Collections.shuffle(unknownCardPool);

        System.out.println("handcardcounts " + handCardCounts);
        System.out.println("unknowncardpool before distributing to players and deck " + unknownCardPool.size());
        
        int playerListInx = 0;
        while(unknownCardPool.size() > 0) {
            Card card = unknownCardPool.get(0);
            if(playerListInx >= game.getPlayerList().size()) {
                game.getDeck().add(card);
                unknownCardPool.remove(card);
                continue;
            }
            Player currentPlayer = game.getPlayerList().get(playerListInx);
            int clientId = currentPlayer.getClientId();
            int handCardCount = currentPlayer.getCardList().size();
            if(clientId == this.clientId) {
                playerListInx++;
                continue;
            }
            if(handCardCount < handCardCounts.get(clientId)) {
                currentPlayer.getCardList().add(card);
                unknownCardPool.remove(card);
            }
            else {
                playerListInx++;
            }
        }
        System.out.println("+++end load board state++++");
        this.game.printBoard();
    }

    private String sendManualMove() {
        System.out.println("choose manual move");
        System.out.println("handcards " + handcards);
        com.nexusvision.server.model.messages.game.Move _move = new com.nexusvision.server.model.messages.game.Move();
        _move.setType(TypeGame.move.getOrdinal());
        System.out.println("isStartMove: ");
        boolean isStartMove = this.userInput.nextLine() == "y";
        System.out.println("cardInx: ");
        String cardInx = this.userInput.nextLine();
        System.out.println("selectedValue: ");
        String _selectedValue = this.userInput.nextLine();
        System.out.println("pieceId: ");
        String pieceId = this.userInput.nextLine();
        System.out.println("opponentPieceId: ");
        String opponentPieceId = this.userInput.nextLine();
        
        _move.setStarter(isStartMove);
        _move.setCard(Integer.parseInt(cardInx));
        _move.setSelectedValue(Integer.parseInt(_selectedValue));
        _move.setPieceId(Integer.parseInt(pieceId));
        _move.setOpponentPieceId(Integer.parseInt(opponentPieceId));
        System.out.println("returning _movejson " + _move);
        return gson.toJson(_move).toString(); //return the _move 3.7
    }

    private String sendMove() {
        //choose random move
        System.out.println("choose move");
        System.out.println("handcards " + handcards);
        long startTime = System.currentTimeMillis();
        // Move move = game.getRandomMove();
        //choose ai move
        Move move = this.ai.getMove(game, startTime);
        if (move != null) {
            System.out.println("move not null");
            move.printMove();
        }
        else {
            System.out.println("null move becaues there is no valid move");
        }
        com.nexusvision.server.model.messages.game.Move _move = new com.nexusvision.server.model.messages.game.Move();
        _move.setType(TypeGame.move.getOrdinal());
        this.sentNull = move == null;
        if(move != null) {
            //convert to json
            _move.setSkip(false);
            _move.setCard(move.getCardUsed().getOrdinal()); //test
            int _selectedValue = move.getSelectedValue();
            System.out.println("selected value " + _selectedValue);
            _move.setSelectedValue(_selectedValue);
            Figure playerFigure = move.getPlayerFigure();
            Integer pieceId = null;
            if(playerFigure != null) {
                pieceId = playerFigure.getFigureId();
            }
            _move.setPieceId(pieceId);
            _move.setStarter(move.isStartMove());
            int opponentPieceId = -1;
            Figure opponentFigure = game.getBoard()[move.getTo().getFieldId()].getFigure();
            if (opponentFigure != null) {
                opponentPieceId = opponentFigure.getFigureId();
            }
            _move.setOpponentPieceId(opponentPieceId);
        }
        else {
            _move.setSkip(true);
            _move.setCard(-1);
            _move.setSelectedValue(-1);
            _move.setPieceId(-1);
            _move.setStarter(false);
            _move.setOpponentPieceId(-1);
        }
        System.out.println("++++++++++move+++++++++++");
        System.out.println("returning _movejson " + _move);
        System.out.println("+++++++++++++++++++++");
        return gson.toJson(_move).toString(); //return the _move 3.7
    }

    private enum State {
        SEND_MOVE,
        WAIT_SYNC_MOVE,
        NO_MOVE
    }
    
}

