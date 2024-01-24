package views;

import Dog.Client.Client;
import Dog.Client.Interfaces.IClientObserverGameplay;
import Dtos.*;
import Dtos.CustomClasses.*;
import Enums.Card;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


public class PCObserverControllerGameplay implements Initializable, IClientObserverGameplay {

    @FXML
    private Label lbLiveTimer;
    @FXML
    private Label lblPlayer1;
    @FXML
    private Label lblPlayer2;
    @FXML
    private Label lblCardsPTwo;
    @FXML
    private Label lblFiguresPTwo;
    @FXML
    private Label lblPlayer3;
    @FXML
    private Label lblCardsPThree;
    @FXML
    private Label lblFiguresPThree;
    @FXML
    private Label lblPlayer4;
    @FXML
    private Label lblCardsPFour;
    @FXML
    private Label lblFiguresPFour;
    @FXML
    private Label lblPlayer5;
    @FXML
    private Label lblCardsPFive;
    @FXML
    private Label lblFiguresPFive;
    @FXML
    private Label lblPlayer6;
    @FXML
    private Label lblCardsPSix;
    @FXML
    private Label lblFiguresPSix;
    @FXML
    private ProgressBar pbTime;
    @FXML
    private Label lbAmZug;
    @FXML
    private ImageView ivDiscardPile;

    @FXML
    private Pane paneBoardView;

    @FXML
    private Pane paneBoard;

    @FXML
    private VBox playerOneVBox;
    @FXML
    private VBox playerTwoVBox;
    @FXML
    private VBox playerThreeVBox;
    @FXML
    private VBox playerFourVBox;
    @FXML
    private VBox playerFiveVBox;
    @FXML
    private VBox playerSixVBox;

    @FXML
    private HBox hbPlayer1;
    @FXML
    private HBox hbPlayer2;
    @FXML
    private HBox hbPlayer3;
    @FXML
    private HBox hbPlayer4;
    @FXML
    private HBox hbPlayer5;
    @FXML
    private HBox hbPlayer6;

    @FXML
    private Button btnResetView;
    @FXML
    private CheckBox cbFullscreen;

    @FXML
    private Label lblCardsPOne;
    @FXML
    private Label lblFiguresPOne;
    @FXML
    private Label lblMoveCount;
    @FXML
    private Button btnOpenInfoWindow;

    private HBox[] playerHbArray;

    public Board board;
    public HouseBoard houseBoard;
    public final DrawBoard drawBoard = new DrawBoard();
    public PieceHandler pieceHandler;
    private PCObserverControllerHouses houseController;

    private int fieldSize = 120;//200;
    private int numPlayers = 6;
    private int numPieces = 4;

    private PCObserverControllerMenu controller;

    private Parent rootHouse;
    private Scene sceneHouse;
    private Stage stageHouse;
    private ReturnLobbyConfigDto lobbyConfig;

    // pane movement
    private final double zoomFactor = 0.05f;
    private double zoom = 1.0f;
    private final double minZoom = 0.05;
    private final double maxZoom = 4.00;
    private double mousePrevX = 0.0f;
    private double mousePrevY = 0.0f;
    private double translateXPrev = 0.0f;
    private double translateYPrev = 0.0f;

    @FXML
    private ImageView ivPlayer1;
    @FXML
    private ImageView ivPlayer2;
    @FXML
    private ImageView ivPlayer3;
    @FXML
    private ImageView ivPlayer4;
    @FXML
    private ImageView ivPlayer5;
    @FXML
    private ImageView ivPlayer6;
    private ImageView[] playerImageViews;
    private RotateTransition rotatePlayer;

    // timer
    private Thread liveTimerThread;
    private long liveTimer;
    private Thread turnTimerThread;
    private long turnTimer;
    private boolean infoWindowState;
    private boolean housePopupState;
    private Stage infoWindowStage;
    private PCObserverControllerInfoWindow infoWindowController;
    private Client client;
    private int lastPlayedCard = 0;

    private Label[] lblPlayerFigures;

    //mtwardy
    @FXML
    private ScrollPane cardsScrollPane;
    @FXML
    private HBox handCards;
    @FXML
    private Pane discardPane;

    public HBox getHandCards() {
        return handCards;
    }

    public Pane getPaneBoardView()
    {
        return paneBoardView;
    }

    public Piece currentPiece = new Piece();
    private CardHandler cardHandler = new CardHandler(this);

    public ImageView getDiscardPile() {
        return ivDiscardPile;
    }

    public Pane getDiscardPane()
    {
        return discardPane;
    }


////////////////////////////////////////////////////////////////////////////

    public void setClient(Client client){
        this.client = client;
        client.registerObserverGameplay(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //TODO: Delete testing data

        // init board
        board = new Board(fieldSize, numPlayers, numPieces);
        houseBoard = new HouseBoard(numPlayers, numPieces);
        pieceHandler = new PieceHandler(board, houseBoard);
        pieceHandler.whosePiece[0] = 1; /////////////////TEST DELETE
        pieceHandler.whosePiece[1] = 2; /////////////////TEST DELETE
        pieceHandler.whosePiece[2] = 3; /////////////////TEST DELETE
        pieceHandler.piecePositions[0] = 2; ///////////////////////TEST DELETE
        pieceHandler.piecePositions[1] = 4; ///////////////////////TEST DELETE
        pieceHandler.piecePositions[2] = 6; ///////////////////////TEST DELETE
        pieceHandler.movePiece(0, 0, true); /////////////TEST DELETE
        pieceHandler.movePiece(2, 1, true); /////////////TEST DELETE
        pieceHandler.movePiece(3, 2, true); /////////////TEST DELETE

        houseBoard.calculateHouseCoordinates(pieceHandler); // recalculate, now that we have the pieceHandler

        // dynamic board size, scales with number of fields
        paneBoard.setPrefSize(board.width, board.height);

        // configure board pane;
        paneBoardView.viewOrderProperty().set(20.0f);

        // hides player boxes if less than six players are participating
        hidePlayerIcons();

        // draw field
        drawBoard.drawBoard(board, paneBoard, pieceHandler);

        // center board
        Platform.runLater(this::resetView);

        lblPlayerFigures = new Label[]{lblFiguresPOne, lblFiguresPTwo, lblFiguresPThree, lblFiguresPFour, lblFiguresPFive, lblFiguresPSix};
        playerImageViews = new ImageView[]{ivPlayer1, ivPlayer2, ivPlayer3, ivPlayer4, ivPlayer5, ivPlayer6};
        playerHbArray = new HBox[]{hbPlayer1, hbPlayer2, hbPlayer3, hbPlayer4, hbPlayer5, hbPlayer6};

        handleRotate(0);

        //mtwardy
        //bind width of handCards to basically window width
        handCards.prefWidthProperty().bind(cardsScrollPane.widthProperty());
        //
    }

    // functionality for "Haus anzeigen" button
    @FXML
    private void openHousePopup(ActionEvent event) throws IOException {
        if (housePopupState){  // checks if an info window is already open; true when open
            stageHouse.getScene().getWindow().hide();
            housePopupState = false;
        }
        else {
            String css = this.getClass().getResource("style.css").toExternalForm();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("houses.fxml"));
            rootHouse = fxmlLoader.load();
            stageHouse = (Stage) ((Node) event.getSource()).getScene().getWindow();
            houseController = fxmlLoader.getController();
            houseController.setBoardAttr(houseBoard, pieceHandler);
            stageHouse = new Stage();
            sceneHouse = new Scene(rootHouse);
            sceneHouse.getStylesheets().add(css);
            stageHouse.setScene(sceneHouse);
            stageHouse.setTitle("Häuser");
            stageHouse.getIcons().add(new Image("icon.png"));
            stageHouse.initOwner(((Node) event.getSource()).getScene().getWindow());
            stageHouse.initModality(Modality.NONE);
            stageHouse.setMinWidth(960);
            stageHouse.setMinHeight(720);
            stageHouse.show();
            housePopupState = true;
            stageHouse.setOnCloseRequest(houseCloseEvent -> {
                housePopupState = false;
            });
        }
    }

    //functionality for "Info" button
    @FXML
    private void openInfoWindow(ActionEvent event) throws IOException {
        if (infoWindowState) { //checks if a house window is already open; true when open
            infoWindowStage.getScene().getWindow().hide();
            infoWindowState = false;
        }
        else {
            String css = this.getClass().getResource("style.css").toExternalForm();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("observerInfoWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            infoWindowController = fxmlLoader.getController();
            infoWindowController.setLobbyConfig(lobbyConfig);
            infoWindowStage = new Stage();
            infoWindowStage.setTitle("Info");
            infoWindowStage.getIcons().add(new Image("icon.png"));
            Scene scene = new Scene(root1);
            scene.getStylesheets().add(css);
            infoWindowStage.setScene(scene);
            infoWindowStage.setMinWidth(960);
            infoWindowStage.setMinHeight(720);
            infoWindowStage.show();
            infoWindowState = true;
            infoWindowStage.setOnCloseRequest(infoWindowCloseEvent -> {
                infoWindowState = false;
            });
        }
    }

    // functionality for "Spiel verlassen" button
    @FXML
    public void leaveMatch(ActionEvent event)throws IOException {
        String css = this.getClass().getResource("style.css").toExternalForm();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("observerMenu.fxml"));
        Parent rootMenu = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Stage stageMenu = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene sceneMenu = new Scene(rootMenu);
        stageMenu.setScene(sceneMenu);
        sceneMenu.getStylesheets().add(css);
        stageMenu.setMinWidth(800);
        stageMenu.setMinHeight(800);
        stageMenu.show();

        stop();
    }


    // zooming functionalities
    @FXML
    void btnResetViewClicked(ActionEvent event) {
        resetView();
    }
    @FXML
    void onMousePressed(MouseEvent mouseEvent) {
        mousePrevX = mouseEvent.getSceneX();
        mousePrevY = mouseEvent.getSceneY();
        translateXPrev = paneBoard.getTranslateX();
        translateYPrev = paneBoard.getTranslateY();
    }

    @FXML
    void onMouseDrag(MouseEvent mouseEvent) {
        double mouseXDelta = mouseEvent.getSceneX() - mousePrevX;
        double mouseYDelta = mouseEvent.getSceneY() - mousePrevY;

        paneBoard.setTranslateX(translateXPrev + mouseXDelta);
        paneBoard.setTranslateY(translateYPrev + mouseYDelta);
    }

    @FXML
    void onMouseScroll(ScrollEvent event) {
        zoomView(Math.signum(event.getDeltaY()) * zoomFactor);
    }

    @FXML
    void cbFullscreenClicked(ActionEvent event) {
        Stage stage = (Stage) paneBoard.getScene().getWindow();
        stage.setFullScreen(cbFullscreen.isSelected());
        resetView();
    }

    /** TODO: DELETE  after testing manually////////////////////////////////////////////////////////////////////
     * TestButton to draw a card on the handCards pane
     * @param event the event that triggered the method
     */
    @FXML // if u want to try a different card change the X in Card.cardX to one in look under Common/src/main/java/Enums/Card.java
    public void btnaddCard(ActionEvent event) {
        drawCard(Card.card8);
    }
    ///////////////////////////////////////////////////////////////////////////////////
    /**
     * Draws a card on the handCards pane
     * @param card the card to be drawn
     */
    public void drawCard(Card card)
    {
        cardHandler.addCard(card);
    }

    private void zoomView(double zoomFactor){
        zoom += zoomFactor;

        if(zoom <= minZoom){
            zoom = minZoom;
        }else if(zoom >= maxZoom){
            zoom = maxZoom;
        }

        paneBoard.setScaleX(zoom);
        paneBoard.setScaleY(zoom);
    }

    private void resetView(){
        paneBoard.setTranslateX(paneBoardView.getWidth() / 2 - paneBoard.getWidth() / 2);
        paneBoard.setTranslateY(paneBoardView.getHeight() / 2 - paneBoard.getHeight() / 2);


        paneBoard.setScaleX(1);
        paneBoard.setScaleY(1);
        zoom = 1;

        while (zoom > minZoom && (board.width * zoom > paneBoardView.getWidth() || board.height * zoom > paneBoardView.getHeight())) {
            zoomView(-zoomFactor);
        }

    }
    // zooming functionalities end

    private void hidePlayerIcons() {
        if (board.numPlayers < 6) {
            playerSixVBox.setVisible(false);
            if (board.numPlayers < 5) {
                playerFiveVBox.setVisible(false);
                if (board.numPlayers < 4) {
                    playerFourVBox.setVisible(false);
                    if (board.numPlayers < 3) {
                        playerThreeVBox.setVisible(false);
                    }
                }
            }
        }
    }

    private void updateDrawing() {
        drawBoard.drawBoard(board, paneBoard, pieceHandler);
        houseBoard.calculateHouseCoordinates(pieceHandler);
        try{
            drawBoard.drawHouses(houseBoard, houseController.paneContent, pieceHandler);
        }catch(NullPointerException e){
            // window is not open
        }

    }

    public void initPlayerNames(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] playerNames = new Label[]{lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4,lblPlayer5, lblPlayer6};
            for(int i = 0; i < lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                playerNames[i].setText(lobbyConfig.getPlayerOrder().getOrder().get(i).getName());
            }
            });
    }
    public void initCardNums(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] numCardsPerPlayer = new Label[]{lblCardsPOne, lblCardsPTwo, lblCardsPThree, lblCardsPFour, lblCardsPFive, lblCardsPSix};
            for(int i = 0; i<lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                numCardsPerPlayer[i].setText(" x "+ lobbyConfig.getInitialCardsPerPlayer());
            }
        });

    }
    public void initBank(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] numFiguresPerPlayer = new Label[]{lblFiguresPOne, lblFiguresPTwo, lblFiguresPThree, lblFiguresPFour, lblFiguresPFive, lblFiguresPSix};
            for(int i = 0; i<lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                numFiguresPerPlayer[i].setText(" x "+ lobbyConfig.getFiguresPerPlayer());
            }
        });

    }
    public void initBoard(ReturnLobbyConfigDto lobbyConfig){
        board = new Board(lobbyConfig.getFieldsize(), lobbyConfig.getPlayerCount(), lobbyConfig.getFiguresPerPlayer());
        houseBoard = new HouseBoard(lobbyConfig.getPlayerCount(), lobbyConfig.getFiguresPerPlayer());
        pieceHandler = new PieceHandler(board, houseBoard);

        houseBoard.calculateHouseCoordinates(pieceHandler); // recalculate, now that we have the pieceHandler

        // dynamic board size, scales with number of fields
        paneBoard.setPrefSize(board.width, board.height);

        // hides player boxes if less than six players are participating
        hidePlayerIcons();

        // draw field
        drawBoard.drawBoard(board, paneBoard, pieceHandler);
    }




    // observerInterface methods
    @Override @FXML
    public void handleLobbyConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException {

        Platform.runLater(()-> {
            this.lobbyConfig = lobbyConfig;
            paneBoard.getChildren().clear();
            try {
                initBoard(lobbyConfig);
                initPlayerNames(lobbyConfig);
                initCardNums(lobbyConfig);
                initBank(lobbyConfig);
                updateDrawing();

                resetView();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            // handleUpdateTurnTimer(new TurnTimerDto(lobbyConfig.getThinkTimePerMove()));
            handleUpdateLiveTimer(new LiveTimerDto(lobbyConfig.getMaximumGameDuration()));
        });
    }

    @Override
    public void handleMoveValid(MoveValidDto moveValid) {
        Platform.runLater(() -> {
            if(moveValid.isSkip()){
                return;
            }
            boolean isSwapCard = (moveValid.getOpponentPieceId() ==  -1);
            if(!isSwapCard) {// Test for Swapcard
                // Case: Swapcard
                pieceHandler.switchPieces(moveValid.getPieceId(), moveValid.getOpponentPieceId());
                updateDrawing();
            }
            else{
                // Case: No Swapcard
                pieceHandler.movePiece(cardNumber(moveValid.getCard(), moveValid.getSelectedValue()), moveValid.getPieceId(), moveValid.isStarter());
                lblPlayerFigures[pieceHandler.whosePiece[moveValid.getPieceId()]].setText(" x " + pieceHandler.numPiecesInField[pieceHandler.whosePiece[moveValid.getPieceId()]]);
                System.out.println("Gameplay: " + pieceHandler.whosePiece[moveValid.getPieceId()]);
                lastPlayedCard = moveValid.getCard();
                updateDrawing();

                // interfacedoc requires response for updated gui
                client.sendMessage(new ResponseDto().toJson());
            }});

    }
    public int cardNumber(int ordinalValue, int selectedValue) {
        int[] cardValues = {2, 3, 5, 6, 8, 9, 10, 12, 0, 0, 0, 0, 0, 0, lastPlayedCard};
        if (ordinalValue < cardValues.length) {
            if(ordinalValue == 8){
                if(selectedValue == -1){
                    return 1;
                }
                else{
                    return 13;
                }
            }
            if(ordinalValue == 9){
                if(selectedValue == 11){
                    return 11;
                }
                else {
                    return 1;
                }
            }
            if(ordinalValue == 10){
                return selectedValue;
            }
            if(ordinalValue == 11){
                return selectedValue;
            }
            return cardValues[ordinalValue];
        } else {
            return 0;
        }
    }

    @Override
    public void handleDrawCards(DrawCardsDto drawCards) {

    }

    @Override
    public void handleBoardState(BoardStateDto boardStateDto) {

        Platform.runLater(()->{
            lblMoveCount.setText("Zug: "+boardStateDto.getMoveCount()+"/"+ lobbyConfig.getMaximumTotalMoves() + " Runde: "+ boardStateDto.getRound());

            // highlight current player
            ArrayList<PlayerName> playerOrder = lobbyConfig.getPlayerOrder().getOrder();
            lblPlayer1.setStyle("");
            lblPlayer2.setStyle("");
            lblPlayer3.setStyle("");
            lblPlayer4.setStyle("");
            lblPlayer5.setStyle("");
            lblPlayer6.setStyle("");
            for(int i = 0; i < playerOrder.size(); ++i){
                if(boardStateDto.getNextPlayer() == playerOrder.get(i).getClientId()){
                    switch(i){
                        case 0:
                            lblPlayer1.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                        case 1:
                            lblPlayer2.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                        case 2:
                            lblPlayer3.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                        case 3:
                            lblPlayer4.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                        case 4:
                            lblPlayer5.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                        case 5:
                            lblPlayer6.setStyle("-fx-text-fill: rgb(255, 255, 110); -fx-font-weight: bold;");
                            break;
                    }
                    handleRotate(i);
                    lbAmZug.setText(String.format("%s ist am Zug", playerOrder.get(i).getName()));
                    break;
                }
            }

            // update board
            ArrayList<PlayerPiece> pieces = boardStateDto.getPieces();
            boolean debugPrints = false; // Debugging for Legs
            for (int i = 0; i < pieces.size(); i++) {
                int pieceClientId = pieces.get(i).getClientId();// alternatively: boardStateDto.getPieces().get(i).getClientId()
                pieceHandler.whosePiece[i] = -1;
                if (debugPrints) {System.out.print("piece = "); System.out.println(i); System.out.print("pieceClientId = ");System.out.println(pieceClientId);};
                for (int playerIndex = 0; playerIndex < board.numPlayers; playerIndex++) {
                    int playerId = lobbyConfig.getPlayerOrder().getOrder().get(playerIndex).getClientId();
                    if (debugPrints) {System.out.print(" - player = "); System.out.println(playerIndex); System.out.print(" - ..having Id = "); System.out.println(playerId);};
                    if (playerId == pieceClientId) {
                        if (debugPrints) {System.out.println("  -> it's a match!");};
                        pieceHandler.whosePiece[i] = playerIndex;
                    }
                }
                if (debugPrints) {System.out.print("For the record: pieceHandler.whosePiece = "); System.out.println(Arrays.toString(pieceHandler.whosePiece));};
                if (pieceHandler.whosePiece[i] == -1) {
                    throw new AssertionError("The piece with index "+Integer.toString(i)+" is supposed to be player-"+Integer.toString(pieceClientId)+"'s piece however this was not assigned during the loop over all "+Integer.toString(board.numPlayers)+" player(s)");
                }
            }
            for(PlayerPiece piece : pieces){
                pieceHandler.movePieceIntoHouse(piece.getClientId(), piece.getInHousePosition());
                updateDrawing();
            }

            // update discardPile
            ArrayList<DiscardedCard> discardPile = boardStateDto.getDiscardPile();
            if(discardPile != null && !discardPile.isEmpty()){
                ivDiscardPile.setImage(new Image(String.format("card_%d.png",discardPile.get(0).getCard())));
            }else{
                ivDiscardPile.setImage(new Image("card_default.png"));
            }
        });
    }

    @Override
    public void handleUpdateDrawCards(UpdateDrawCardsDto updateDrawCards) {
        Platform.runLater(() ->{
            HandCards[] listNumCards = updateDrawCards.getHandCards();
            lblCardsPOne.setText(" x " + listNumCards[0].getCount());
            lblCardsPTwo.setText(" x " + listNumCards[1].getCount());
            if(lobbyConfig.getPlayerCount() >= 3){
                lblCardsPThree.setText(" x " + listNumCards[2].getCount());
                if(lobbyConfig.getPlayerCount() >= 4){
                    lblCardsPFour.setText(" x " + listNumCards[3].getCount());
                    if(lobbyConfig.getPlayerCount() >= 5){
                        lblCardsPFive.setText(" x " + listNumCards[4].getCount());
                        if(lobbyConfig.getPlayerCount() >= 6){
                            lblCardsPSix.setText(" x " + listNumCards[5].getCount());

                        }
                    }
                }
            }

            // gray out player
            for (HBox hBox : playerHbArray) {
                hBox.setDisable(false);
            }
            for(int i = 0; i < listNumCards.length; ++i){
                if(listNumCards[i].getCount() == 0) {
                    playerHbArray[i].setDisable(true);
                }
            }
        });

    }

    @Override
    public void handleUpdateLiveTimer(LiveTimerDto liveTimerDto) {
        liveTimer = liveTimerDto.getLiveTime();
        lbLiveTimer.setText(new SimpleDateFormat("mm:ss").format(liveTimer * 1000));

        if(liveTimerThread == null){
            liveTimerThread = new Thread(() -> {
                try {
                    while(liveTimer > 0){
                        Thread.sleep(1000);
                        liveTimer -= 1000;
                        Platform.runLater(() -> lbLiveTimer.setText(new SimpleDateFormat("mm:ss").format(liveTimer)));
                    }
                    liveTimer = 0;
                } catch (InterruptedException ignored) {
                }
            });
            liveTimerThread.start();
        }
    }

    @Override
    public void handleUpdateTurnTimer(TurnTimerDto turnTimerDto) {
        turnTimer = turnTimerDto.getTurnTime();
        pbTime.setProgress(turnTimer / (float) lobbyConfig.getThinkTimePerMove());

        if(turnTimerThread == null){
            turnTimerThread = new Thread(() -> {
                try {
                    while(turnTimer > 0){
                        Thread.sleep(1);
                        turnTimer -= 1;
                        Platform.runLater(() -> pbTime.setProgress(turnTimer / (float) lobbyConfig.getThinkTimePerMove()));
                    }
                    turnTimer = 0;
                } catch (InterruptedException ignored) {
                }
            });
            turnTimerThread.start();
        }
    }

    public void stop(){
        if(liveTimerThread != null) liveTimerThread.interrupt();
        if(turnTimerThread != null) turnTimerThread.interrupt();
        client.closeConnection();
    }



    //Code made to make players Rotate

    //these methods are used as the temporary trigger for the Handler method, in the future it would be wise to
    //let this be handled by the mechanism that indicates which players turn it is.
    /*
    public void player1Rotate(){
        handleRotate(bttnPlayer1);
    }
    public void player2Rotate(){
        handleRotate(bttnPlayer2);
    }
    public void player3Rotate(){
        handleRotate(bttnPlayer3);
    }
    public void player4Rotate(){
        handleRotate(bttnPlayer4);
    }
    public void player5Rotate(){
        handleRotate(bttnPlayer5);
    }
    public void player6Rotate(){
        handleRotate(bttnPlayer6);
    }*/

    //the handler, used to rotate the playerIcon
    //a second parameter could be used to limit the cycle count to the maximum time per turn
    public void handleRotate(int playerIndex) {
        if(rotatePlayer != null){
            rotatePlayer.stop();
            rotatePlayer.getNode().setRotate(0);
        }

        rotatePlayer = new RotateTransition();
        rotatePlayer.setNode(playerImageViews[playerIndex]);
        rotatePlayer.setDuration(Duration.millis(1500));
        rotatePlayer.setCycleCount(TranslateTransition.INDEFINITE);
        rotatePlayer.setInterpolator(Interpolator.LINEAR);
        rotatePlayer.setFromAngle(0);
        rotatePlayer.setByAngle(360);
        rotatePlayer.setAxis(Rotate.Z_AXIS);
        rotatePlayer.play();
    }


    @Override
    public void handleFreeze(FreezeDto freeze) {

    }

    @Override
    public void handleUnFreeze(UnfreezeDto unfreeze) {

    }

    @Override
    public void handleCancel(CancelDto cancel) {

    }

    @Override
    public void handleKick(KickDto kick) {

    }

}