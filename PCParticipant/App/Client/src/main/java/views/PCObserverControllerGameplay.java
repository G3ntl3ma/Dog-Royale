package views;

import Dog.Client.Client;
import Dog.Client.Interfaces.IClientObserverGameplay;
import Dtos.*;
import Dtos.CustomClasses.*;
import Enums.Card;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for the observer gameplay screen
 *
 * @author grupp 8, mtwardy
 */
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
    public DrawBoard drawBoard;
    public PieceHandler pieceHandler;
    private PCObserverControllerHouses houseController;

    private final int fieldSize = 120;//200;
    private final int numPlayers = 6;
    private final int numPieces = 4;

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
    private int liveTimer;
    private Thread turnTimerThread;
    private long turnTimer;
    private boolean infoWindowState;
    private boolean housePopupState;
    private Stage infoWindowStage;
    private PCObserverControllerInfoWindow infoWindowController;
    private Client client;
    private int lastPlayedCard = -1;
    private int lastSelectedValue = -1;

    private Label[] lblPlayerFigures;

    //mtwardy
    @FXML
    private ScrollPane cardsScrollPane;
    @FXML
    private HBox handCards;
    @FXML
    private Pane discardPane;

    public int animationTime = 1000;

    /**
     * returns the handCards
     *
     * @return An HBox containing the handCards
     */
    public HBox getHandCards() {
        return handCards;
    }


    /**
     * returns the paneBoard
     *
     * @return A Pane containing the paneBoard
     */
    public Pane getPaneBoardView()
    {
        return paneBoardView;
    }

    public PieceImages currentPiece = new PieceImages(paneBoard);
    private CardHandler cardHandler = new CardHandler(this);


    /**
     * returns the cardHandler
     *
     * @return A CardHandler containing the cardHandler
     */
    public ImageView getDiscardPile() {
        return ivDiscardPile;
    }

    /**
     * returns the discardPane
     *
     * @return A Pane containing the discardPane
     */
    public Pane getDiscardPane()
    {
        return discardPane;
    }

    /**
     * returns the animationTime
     *
     * @return An Integer containing the animationTime
     */
    public int getAnimationTime() {
        return animationTime;
    }


////////////////////////////////////////////////////////////////////////////

    /**
     * Sets the client
     *
     * @param client the client to be set
     */
    public void setClient(Client client){
        this.client = client;
        CardHandler.client = client;
        client.registerObserverGameplay(this);
    }

    /**
     * Sets the lobbyConfig
     *
     * @param url This parameter represents the location of the FXML file that defines the user interface associated with this controller
     * @param resourceBundle This parameter is used to pass a ResourceBundle to the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currentPiece = new PieceImages(paneBoard);
        // init board
        board = new Board(fieldSize, numPlayers, numPieces);
        houseBoard = new HouseBoard(numPlayers, numPieces);
        pieceHandler = new PieceHandler(board, houseBoard);


        houseBoard.calculateHouseCoordinates(pieceHandler); // recalculate, now that we have the pieceHandler
        drawBoard = new DrawBoard(paneBoard, board, pieceHandler);

        // dynamic board size, scales with number of fields
        paneBoard.setPrefSize(board.width, board.height);

        // configure board pane;
        paneBoardView.viewOrderProperty().set(20.0f);

        // hides player boxes if less than six players are participating
        hidePlayerIcons();

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

    /**
     * funtionality for "Haus anzeigen" button
     *
     * @param event the event that triggered the method
     * @throws IOException if an input or output exception occurs
     */
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

    /**
     * functionality for "Info" button
     *
     * @param event the event that triggered the method
     * @throws IOException if an input or output exception occurs
     */
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

    /**
     * functionality for "Spiel verlassen" button
     *
     * @param event the event that triggered the method
     * @throws IOException if an input or output exception occurs
     */
    @FXML
    public void leaveMatch(ActionEvent event)throws IOException {
        String css = this.getClass().getResource("style.css").toExternalForm();
        client.sendMessage(new LeavePlayerDto().toJson());
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


    /**
     * zooming functionalities
     *
     * @param event the event that triggered the method
     */
    @FXML
    void btnResetViewClicked(ActionEvent event) {
        resetView();
    }

    /**
     * functionality for mouse events
     *
     * @param mouseEvent the event that triggered the method
     */
    @FXML
    void onMousePressed(MouseEvent mouseEvent) {
        mousePrevX = mouseEvent.getSceneX();
        mousePrevY = mouseEvent.getSceneY();
        translateXPrev = paneBoard.getTranslateX();
        translateYPrev = paneBoard.getTranslateY();
    }

    /**
     * functionality for mouse events
     *
     * @param mouseEvent the event that triggered the method
     */
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

    @FXML
    /**author: mtwardy
     * Button to skip a turn
     * @param event the event that triggered the method
     */
    public void skipTurn(ActionEvent event) {
        if (CardHandler.turn) {
            client.sendMessage(new MoveDto(true, Card.card2.ordinal(), 0, 0, false, -1).toJson());
        }
    }

    /**
     * functionality for Zooming
     *
     * @param zoomFactor A Double containing the zoomFactor
     */
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

    /**
     * functionality for resetting the view
     */
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

    /**
     * functionality for hiding the player icons
     */
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

    /**
     * responsible for updating the drawing of houses on the graphical interface based on the calculated house coordinates
     */
    private void updateDrawing() {
        houseBoard.calculateHouseCoordinates(pieceHandler);
        try{
            drawBoard.updateHouses(paneBoardView, houseBoard);
        }catch(NullPointerException e){
            // window is not open
        }

    }

    /**
     * functionality for displaying the player names
     *
     * @param lobbyConfig the lobbyConfig used to initialize the players
     */
    public void initPlayerNames(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] playerNames = new Label[]{lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4,lblPlayer5, lblPlayer6};
            for(int i = 0; i < lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                playerNames[i].setText(lobbyConfig.getPlayerOrder().getOrder().get(i).getName());
                if (lobbyConfig.getPlayerOrder().getOrder().get(i).getClientId() == this.client.getClientID())
                {
                    PieceImages.clientPlayerIndex = i;
                }
            }
            });
    }

    /**
     * functionality for displaying the number of cards per player
     *
     * @param lobbyConfig the lobbyConfig used to initialize the number of cards per player
     */
    public void initCardNums(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] numCardsPerPlayer = new Label[]{lblCardsPOne, lblCardsPTwo, lblCardsPThree, lblCardsPFour, lblCardsPFive, lblCardsPSix};
            for(int i = 0; i<lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                numCardsPerPlayer[i].setText(" x "+ lobbyConfig.getInitialCardsPerPlayer());
            }
        });

    }

    /**
     * functionality for initializing the bench
     *
     * @param lobbyConfig the lobbyConfig used to initialize the bench
     */
    public void initBench(ReturnLobbyConfigDto lobbyConfig){
        Platform.runLater(() -> {
            Label[] numFiguresPerPlayer = new Label[]{lblFiguresPOne, lblFiguresPTwo, lblFiguresPThree, lblFiguresPFour, lblFiguresPFive, lblFiguresPSix};
            for(int i = 0; i<lobbyConfig.getPlayerOrder().getOrder().size(); i++){
                numFiguresPerPlayer[i].setText(" x "+ lobbyConfig.getFiguresPerPlayer());
            }
        });

    }

    /**
     * functionality for initializing the board
     *
     * @param lobbyConfig the lobbyConfig used to initialize the board
     */
    public void initBoard(ReturnLobbyConfigDto lobbyConfig){
        board = new Board(lobbyConfig.getFieldsize(), lobbyConfig.getMaxPlayerCount(), lobbyConfig.getFiguresPerPlayer());
        houseBoard = new HouseBoard(lobbyConfig.getMaxPlayerCount(), lobbyConfig.getFiguresPerPlayer());
        pieceHandler = new PieceHandler(board, houseBoard);

        houseBoard.calculateHouseCoordinates(pieceHandler); // recalculate, now that we have the pieceHandler
        drawBoard = new DrawBoard(paneBoard, board, pieceHandler);

        // dynamic board size, scales with number of fields
        paneBoard.setPrefSize(board.width, board.height);

        // hides player boxes if less than six players are participating
        hidePlayerIcons();
    }




    // observerInterface methods

    /**
     * functionality for handling the update of the live timer
     *
     * @param lobbyConfig Represents an object of type ReturnLobbyConfigDto, which contains configuration information related to a lobby
     * @throws IOException if an input or output exception occurs
     */
    @Override @FXML
    public void handleLobbyConfig(ReturnLobbyConfigDto lobbyConfig) throws IOException {
        System.out.println("LobbyConfig: " + lobbyConfig.toJson());

        Platform.runLater(()-> {
            this.lobbyConfig = lobbyConfig;
            this.animationTime = lobbyConfig.getVisualizationTimePerMove();
            paneBoard.getChildren().clear();
            try {
                initBoard(lobbyConfig);
                initPlayerNames(lobbyConfig);
                initCardNums(lobbyConfig);
                initBench(lobbyConfig);
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

    /**
     * functionality for handling move validity
     *
     * @param moveValid Represents an object of type MoveValidDto, which contains information about the validity of a move
     */
    @Override
    public void handleMoveValid(MoveValidDto moveValid) {
        System.out.println("moveValid: " + moveValid.toJson());

        Platform.runLater(() -> {
            if(!moveValid.isValidMove()){
                updatePieceLabels();
                return;
            }
            if(moveValid.isSkip()){
                updatePieceLabels();
                return;
            }
            boolean isSwapMagnetCard = (moveValid.getOpponentPieceId() != -1);
            if(moveValid.isStarter()){
                pieceHandler.startPiece(moveValid.getPieceId());
                updateDrawing();
                client.sendMessage(new ResponseDto().toJson());
                updatePieceLabels();
                return;
            }
            if(isSwapMagnetCard) {// Test for Swap card
                if(cardNumber(moveValid.getCard(), moveValid.getSelectedValue()) == 100) {
                    // Case: Magnet
                    pieceHandler.magnet(moveValid.getPieceId(), moveValid.getOpponentPieceId());
                }
                else if (cardNumber(moveValid.getCard(), moveValid.getSelectedValue()) == 200) {
                    // Case: Swap card
                    pieceHandler.switchPieces(moveValid.getPieceId(), moveValid.getOpponentPieceId());
                }
                else{
                    throw new AssertionError("Somethings wrong with your server logic. Wanting to accuse others. Probably not a valid move to begin with.");
                }
            }
            else{
                // Case: No Swapcard
                pieceHandler.movePiece(cardNumber(moveValid.getCard(), moveValid.getSelectedValue()), moveValid.getPieceId());
                lblPlayerFigures[pieceHandler.pieces[moveValid.getPieceId()].player].setText(" x " + (board.numPieces - pieceHandler.numPiecesOnBench(pieceHandler.pieces[moveValid.getPieceId()].player)));
                if(moveValid.getCard() != 14) { // don't save copycard as last played cards
                    lastPlayedCard = moveValid.getCard();
                    lastSelectedValue = moveValid.getSelectedValue();
                }
            }
            updatePieceLabels();
            // interfacedoc requires (optional) response for updated gui
            updateDrawing();
            client.sendMessage(new ResponseDto().toJson());
        });
    }

    /**
     * updates piece labels
     */
    private void updatePieceLabels() {
        for (int player = 0; player < board.numPlayers; player++) {
            lblPlayerFigures[player].setText("x " + pieceHandler.numPiecesOnBench(player));
        }
    }

    /**
     * returns the corresponding numerical value based on a predefined array (cardValues).
     * It handles special cases for certain cards
     *
     * @param ordinalValue Represents the ordinal value of a card, typically ranging from 0 to 14.
     * @param selectedValue Represents the selected value associated with a card.
     * @return An Integer containing the card number
     */
    public int cardNumber(int ordinalValue, int selectedValue) {
        int[] cardValues = {2, 3, 5, 6, 8, 9, 10, 12, 0, 0, 0, 0, 100, 200, 0}; // 100 = swapcard, 200 = magnetcard
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
            if(ordinalValue == 14){
                return cardNumber(lastPlayedCard, lastSelectedValue);
            }
            return cardValues[ordinalValue];
        } else {
            return -1;
        }
    }

    /**
     * handles the drawing and dropping of cards in the game
     *
     * @param drawCards Represents an object of type DrawCardsDto containing information about drawn and dropped cards in the game.
     */
    @Override
    public void handleDrawCards(DrawCardsDto drawCards) {
        System.out.println("DrawnCards: " + drawCards.toJson());

        Platform.runLater(() -> {

            Card[] droppedCards = new Card[lobbyConfig.getInitialCardsPerPlayer()+lobbyConfig.getDrawCardFields().getCount()];
            int count = 0;
            for (int i : drawCards.getDroppedCards())
            {
                droppedCards[count] = Card.getByOrdinal(i);
                count++;
            }
            cardHandler.removeCards(droppedCards);
            for (int i : drawCards.getDrawnCards())
            {
                cardHandler.addCard(Card.getByOrdinal(i));
            }

            cardHandler.updateCards();
        });

    }

    /**
     * It prints debug information, updates UI elements such as player labels,
     * displays the current player's turn, and updates the visual representation of pieces on the board.
     * Additionally, it manages the display of the discard pile
     *
     * @param boardStateDto Represents an object containing the current state of the game board
     */
    @Override
    public void handleBoardState(BoardStateDto boardStateDto) {

        System.out.println("boardState: " + boardStateDto.toJson());

        Platform.runLater(()->{
            if(boardStateDto.isGameOver()){
                // show end screen
                try{
                    if(boardStateDto.getWinnerOrder().size() <= 2){
                        String css = this.getClass().getResource("style.css").toExternalForm();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("endScreen2Player.fxml"));
                        Parent rootMenu = fxmlLoader.load();
                        PCObserverControllerEndScreen2Player controller = fxmlLoader.getController();
                        controller.initData(boardStateDto.getWinnerOrder());
                        Stage stageMenu = (Stage) lblPlayer1.getScene().getWindow();
                        Scene sceneMenu = new Scene(rootMenu);
                        stageMenu.setScene(sceneMenu);
                        sceneMenu.getStylesheets().add(css);
                        stageMenu.setMinWidth(800);
                        stageMenu.setMinHeight(800);
                        stageMenu.show();
                    }else{
                        String css = this.getClass().getResource("style.css").toExternalForm();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("endScreen6Player.fxml"));
                        Parent rootMenu = fxmlLoader.load();
                        PCObserverControllerEndScreen6Player controller = fxmlLoader.getController();
                        controller.initData(boardStateDto.getWinnerOrder());
                        Stage stageMenu = (Stage) lblPlayer1.getScene().getWindow();
                        Scene sceneMenu = new Scene(rootMenu);
                        stageMenu.setScene(sceneMenu);
                        sceneMenu.getStylesheets().add(css);
                        stageMenu.setMinWidth(800);
                        stageMenu.setMinHeight(800);
                        stageMenu.show();
                    }
                }catch(IOException ignored){
                }
                return;
            }

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
            // check if it´s your turn author: mtwardy
            cardHandler.setTurnWithId(boardStateDto.getNextPlayer());

            // update board
            ArrayList<PlayerPiece> pieces = boardStateDto.getPieces();
            boolean debugPrints = false; // Debugging for Legs
            for (int i = 0; i < pieces.size(); i++) {
                int pieceClientId = pieces.get(i).getClientId();// alternatively: boardStateDto.getPieces().get(i).getClientId()
                for (int playerIndex = 0; playerIndex < board.numPlayers; playerIndex++) {
                    int playerId = lobbyConfig.getPlayerOrder().getOrder().get(playerIndex).getClientId();


                    if (playerId == pieceClientId) {
                        int pieceID = boardStateDto.getPieces().get(i).getPieceId();
                        PieceHandler.pieces[pieceID].setPlayer(playerIndex); //mtwardy: modified
                        PieceHandler.pieces[pieceID].fieldImage.setImage(new Image(drawBoard.playerImagePath(playerIndex)));
                        PieceHandler.pieces[pieceID].houseImage.setImage(new Image(drawBoard.playerImagePath(playerIndex)));
                        PieceHandler.pieces[pieceID].isOnBench = pieces.get(i).isOnBench();
                    }
                }

                if (PieceHandler.pieces[i].player == -1) {
                    throw new AssertionError("The piece with index "+Integer.toString(i)+" is supposed to be player-"+Integer.toString(pieceClientId)+"'s piece however this was not assigned during the loop over all "+Integer.toString(board.numPlayers)+" player(s)");
                }
            }
            for(PlayerPiece piece : pieces){
                pieceHandler.assertPieceState(piece.getPieceId(),piece.isOnBench(), piece.getPosition(), piece.getInHousePosition());
                updateDrawing();
            }


            // update discardPile
            ArrayList<DiscardedCard> discardPile = boardStateDto.getDiscardPile();
            if(discardPile != null && !discardPile.isEmpty()){
                ivDiscardPile.setImage(new Image(String.format("card_%d.png",discardPile.get(0).getCard())));
                if (Card.getByOrdinal(discardPile.get(0).getCard()) != Card.copyCard) //Saves last played Card for copy card
                {
                    cardHandler.setLastPlayedCard(Card.getByOrdinal(discardPile.get(0).getCard()));
                }
            }else{
                ivDiscardPile.setImage(new Image("card_default.png"));
            }
        });
    }

    /**
     * updates the UI labels representing the number of draw cards for each player based on the information provided
     *
     * @param updateDrawCards contains information about the hand cards held by players.
     */
    @Override
    public void handleUpdateDrawCards(UpdateDrawCardsDto updateDrawCards) {
        Platform.runLater(() ->{
            /**
            HandCards[] listNumCards = updateDrawCards.getHandCards();
            lblCardsPOne.setText(" x " + listNumCards[0].getCount());
            lblCardsPTwo.setText(" x " + listNumCards[1].getCount());
            if(listNumCards.length >= 3){
                lblCardsPThree.setText(" x " + listNumCards[2].getCount());
                if(listNumCards.length >= 4){
                    lblCardsPFour.setText(" x " + listNumCards[3].getCount());
                    if(listNumCards.length >= 5){
                        lblCardsPFive.setText(" x " + listNumCards[4].getCount());
                        if(listNumCards.length >= 6){
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
             */
        });

    }

    /**
     * updates the local liveTimer variable and the UI label representing the live timer
     *
     * @param liveTimerDto an object of type LiveTimerDto representing the live timer data
     */
    @Override
    public void handleUpdateLiveTimer(LiveTimerDto liveTimerDto) {
        liveTimer = liveTimerDto.getLiveTime();
        String time = new SimpleDateFormat("mm:ss").format(liveTimer);
        Platform.runLater(() -> lbLiveTimer.setText(time));

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

    /**
     * updates the local turnTimer variable and the UI label representing the turn timer
     *
     * @param turnTimerDto represents a data transfer object (TurnTimerDto) containing information about the turn timer
     */
    @Override
    public void handleUpdateTurnTimer(TurnTimerDto turnTimerDto) {
        turnTimer = turnTimerDto.getTurnTime();
        Platform.runLater(() -> pbTime.setProgress(turnTimer / (float) lobbyConfig.getThinkTimePerMove()));

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

    /**
     * closes the connection
     */
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

    /**
     * the handler, used to rotate the playerIcon
     *
     * @param playerIndex Represents the index of the player whose image needs to be rotated
     */
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


    /**
     * functionality for handling the freeze of a player
     *
     * @param freeze Represents an object of type FreezeDto
     */
    @Override
    public void handleFreeze(FreezeDto freeze) {

    }

    /**
     * functionality for handling the unfreeze of a player
     *
     * @param unfreeze Represents an object of type UnfreezeDto
     */
    @Override
    public void handleUnFreeze(UnfreezeDto unfreeze) {

    }

    /**
     * functionality for handling cancel
     *
     * @param cancel Represents an object of type CancelDto
     */
    @Override
    public void handleCancel(CancelDto cancel) {

    }

    /**
     * functionality for handling kicking a player
     *
     * @param kick Represents an object of type KickDto
     */
    @Override
    public void handleKick(KickDto kick) {

    }

}