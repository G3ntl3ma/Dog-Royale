package views;

import Dog.Client.Client;
import Dog.Client.Interfaces.IClientObserverMenu;
import Dtos.*;
import Dtos.CustomClasses.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PCObserverControllerMenu implements Initializable, IClientObserverMenu {
    // attr related to connecting
    @FXML
    private TextField tfIP;
    @FXML
    private TextField tfPort;
    @FXML
    private TextField tfUsername;
    @FXML
    private Button bttnConnect;

    // attr related to gameList tabpane
    @FXML
    private TabPane tPGameList;
    @FXML
    private TableView<GamesProgressing> tVPlannedGames;
    @FXML
    private TableColumn<GamesProgressing, String> columnName;
    @FXML
    private TableColumn<GamesProgressing, Integer> columnMaxPlayer;
    @FXML
    private TableColumn<GamesProgressing, String> columnAlreadyJoined;
    @FXML
    private  TableView<GamesProgressing> tVRunningGames;
    @FXML
    private TableColumn<GamesProgressing, String> clmnNameRunning;
    @FXML
    private TableColumn<GamesProgressing, Integer> clmnMaxPlayersRunning;
    @FXML
    private TableColumn<GamesProgressing, String> clmnAlreadyJoinedRunning;
    @FXML
    private TableView<GamesFinished> tVFinishedGames;
    @FXML
    private TableColumn<GamesFinished, String> clmnNameFinished;
    @FXML
    private TableColumn<GamesFinished, Array> clmnResultsFinished;
    @FXML
    private TableColumn<GamesFinished, Boolean> clmnWasCanceled;

    // attr related to tournament tabpane
    @FXML
    private TabPane tPTournaments;
    @FXML
    private TableView<TournamentsUpcoming> tVPlannedTournaments;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCPTtournamentId;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCPTMaxPlayers;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCPTMaxGames;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCPTCurrentPlayerCount;
    @FXML
    private TableView<TournamentsUpcoming> tVRunningTournaments;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCRTtournamentId;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCRTMaxPlayers;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCRTMaxGames;
    @FXML
    private TableColumn<TournamentsUpcoming, Integer> tCRTCurrentPlayerCount;
    @FXML
    private TableView<TournamentsFinished> tVFinishedTournaments;
    @FXML
    private TableColumn<TournamentsFinished, Integer> tCFTtournamentId;
    @FXML
    private TableColumn<TournamentsFinished, Array> tCFTwinnerOrder;

    @FXML
    private TextArea tAClientLog;

    @FXML
    private Button bttnObserve;
    @FXML
    private Button bttnReloadGameList;
    @FXML
    private Button bttnTournamentInformation;
    @FXML
    private CheckBox cBIsObserver;

    @FXML
    private Client client;
    private ReturnGameListDto gameList;
    private ReturnTournamentListDto findTournament;
    private PCObserverControllerGameplay controller;
    private ReturnTournamentInfoDto tournamentInfo;

    @FXML
    public void switchToGameplay(ActionEvent event) throws IOException {
        if(client == null) {
            return;
        }
        // check for which tab is selected to send proper join-msg
        Tab selectedTab = tPGameList.getSelectionModel().getSelectedItem();
        if(selectedTab != null)
            joinGameAsObserver();
        else {
            joinTournamentGame();
        }

        String css = this.getClass().getResource("style.css").toExternalForm();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameplayresponsiv.fxml"));
        Parent rootGameplay = fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setClient(client);

        Stage stageGameplay = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sceneGameplay = new Scene(rootGameplay);
        sceneGameplay.getStylesheets().add(css);
        stageGameplay.setScene(sceneGameplay);
        stageGameplay.setTitle("Dog Digital");
        stageGameplay.setMinWidth(960);
        stageGameplay.setMinHeight(960);
        stageGameplay.show();


        // call stop method when stage is closed
        stageGameplay.setOnCloseRequest(windowEvent -> controller.stop());
    }

    /** //////////////////////////////////////////////////////////////////////////////////////TODO DELETE THIS METHOD after testing without server
     * This method is used to fake a gameplay for testing purposes
     * @param event
     * @throws IOException
     */
    public void fakeGameplay(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameplayresponsiv.fxml"));
        Parent rootGameplay = fxmlLoader.load();

        String css = this.getClass().getResource("style.css").toExternalForm();

        Stage stageGameplay = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sceneGameplay = new Scene(rootGameplay);
        sceneGameplay.getStylesheets().add(css);
        stageGameplay.setScene(sceneGameplay);
        stageGameplay.setTitle("Dog Digital");
        stageGameplay.setMinWidth(960);
        stageGameplay.setMinHeight(1080);
        stageGameplay.show();

        // call stop method when stage is closed
        stageGameplay.setOnCloseRequest(windowEvent -> {
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////TODO DELETE fakeGameplay Method after testing manually till here
    public void checkBoxSwitch(){
        cBIsObserver.setOnAction(event-> {
            if(cBIsObserver.isSelected()) {
                bttnObserve.setText("Beobachten");
            }
            else {
                bttnObserve.setText("Teilnehmen");
            }
        });

    }
    public void joinGameAsObserver(){
        Tab selectedTab = tPGameList.getSelectionModel().getSelectedItem();
        TableView selectedTableview = (TableView) selectedTab.getContent();
        int gameId;
        if(selectedTableview.equals(tVPlannedGames)){
            gameId = gameList.getStartingGame().get(selectedTableview.getSelectionModel().getSelectedIndex()).getGameId();
        }
        else{
           gameId = gameList.getRunningGames().get(selectedTableview.getSelectionModel().getSelectedIndex()).getGameId();
        }
        if (cBIsObserver.isSelected()) {
            client.sendMessage(new JoinGameAsObserverDto(gameId, client.getClientID()).toJson());
        }
        else {
            client.sendMessage(new JoinGameAsPlayerDto(gameId, client.getClientID(), tfUsername.getText()).toJson());
        }

    }


    // see also @handleReturnTournamentInfo()
    public void joinTournamentGame(){
        Tab selectedTab = tPTournaments.getSelectionModel().getSelectedItem();
        TableView selectedTableview = (TableView) selectedTab.getContent();
        int tournamentId;

        int gameId;
        if(selectedTableview.equals(tVPlannedTournaments)){
            tournamentId = findTournament.getTournamentsUpcoming().get(selectedTableview.getSelectionModel().getSelectedIndex()).getTournamentId();
        }
        else{
            tournamentId = findTournament.getTournamentsRunning().get(selectedTableview.getSelectionModel().getSelectedIndex()).getTournamentId();
        }
        requestTournamentInfo(tournamentId);
    }
    public void requestGameList(){
        client.sendMessage(new RequestGameListDto(client.getClientID(), 100,100,100).toJson());
    }
    public void findTournament(){
        client.sendMessage(new RequestTournamentListDto(client.getClientID(), 100, 100, 100).toJson());
    }

    @FXML
    public void reloadGamesAndTournaments(){
        requestGameList();
        findTournament();
    }
    public void requestTournamentInfo(int tournamentId){
         client.sendMessage(new RequestTournamentInfoDto(client.getClientID(), tournamentId).toJson());
    }


    // registers class as observer on client after connection to server is established
    // immediately sends requestGameList and findTournament
    @FXML
    public void connectingToServer(ActionEvent event) throws IOException {
        if (client != null) {
            tAClientLog.setText("Bereits mit einem Server verbunden");
            return;
        }
        int port = 0;
        String ip = "localhost";
        try {
            port = Integer.parseInt(tfPort.getText());
            ip = tfIP.getText();
            if (port < 1 || port > 65535) {
                throw new NumberFormatException();
            }
            String username = (String) tfUsername.getText();
            Socket socket = new Socket(ip, port);
            client = new Client(socket, username);
            client.listenForMessage();

            client.sendMessage((new ConnectToServerDto(username, cBIsObserver.isSelected())).toJson());
            tAClientLog.setText("Mit dem Server auf Port " + port + " verbunden");
            bttnConnect.setText("Trennen");
            bttnConnect.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stop();
                }
            });

            requestGameList();
            findTournament();
            client.registerObserverMenu(this);

            bttnReloadGameList.setDisable(false);
            bttnReloadGameList.setDisable(false);
        } catch (ConnectException connectException) {
            tAClientLog.setText("Es konnte keine Verbindung hergestellt werden!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{
            // callback for representing player order cell correctly
            Callback<TableColumn.CellDataFeatures<GamesProgressing, String>, ObservableValue<String>> playerOrderFactory = gamesProgressingStringCellDataFeatures -> {
                StringBuilder names = new StringBuilder();
                ArrayList<PlayerName> playerNameList = gamesProgressingStringCellDataFeatures.getValue().getPlayerOrder();

                for(int i = 0; i < playerNameList.size(); ++i){
                    names.append(playerNameList.get(i).getName());
                    if(i < playerNameList.size() - 1){
                        names.append(", ");
                    }
                }
                return new SimpleStringProperty( names.toString());
            };

            // configure table columns for single games
            columnName.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            columnMaxPlayer.setCellValueFactory(new PropertyValueFactory<>("maxPlayerCount"));
            columnAlreadyJoined.setCellValueFactory(playerOrderFactory);

            clmnNameRunning.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            clmnMaxPlayersRunning.setCellValueFactory(new PropertyValueFactory<>("maxPlayerCount"));
            clmnAlreadyJoinedRunning.setCellValueFactory(playerOrderFactory);
            clmnNameFinished.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            clmnResultsFinished.setCellValueFactory(new PropertyValueFactory<>("winnerOrder"));
            clmnWasCanceled.setCellValueFactory(new PropertyValueFactory<>("wasCanceled"));

            // configure table columns for tournaments
            tCPTtournamentId.setCellValueFactory(new PropertyValueFactory<>("tournamentId"));
            tCPTMaxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayer"));
            tCPTMaxGames.setCellValueFactory(new PropertyValueFactory<>("maxGames"));
            tCPTCurrentPlayerCount.setCellValueFactory(new PropertyValueFactory<>("currentPlayerCount"));
            tCRTtournamentId.setCellValueFactory(new PropertyValueFactory<>("tournamentId"));
            tCRTMaxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayer"));
            tCRTMaxGames.setCellValueFactory(new PropertyValueFactory<>("maxGames"));
            tCRTCurrentPlayerCount.setCellValueFactory(new PropertyValueFactory<>("currentPlayerCount"));
            tCFTtournamentId.setCellValueFactory(new PropertyValueFactory<>("tournamentId"));
            tCFTwinnerOrder.setCellValueFactory(new PropertyValueFactory<>("winnerOrder"));
        });

        // Enable "observe"-button after game is selected
        tVPlannedGames.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            bttnObserve.setDisable(newSelection == null);
        });

        tVRunningGames.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            bttnObserve.setDisable(newSelection == null);
        }));

        // Enable "observe"-button after tournament is selected
        tVPlannedTournaments.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            bttnObserve.setDisable(newSelection == null);
        });

        tVRunningTournaments.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            bttnObserve.setDisable(newSelection == null);
        }));

        // Enable "tournamentinfo"-button after tournament is selected
        tVPlannedTournaments.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            bttnTournamentInformation.setDisable(newSelection == null);
        }));

        tVRunningTournaments.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            bttnTournamentInformation.setDisable(newSelection == null);
        }));

        // Enable "connect"-button after all information is entered
        tfIP.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        tfUsername.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        tfPort.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        checkBoxSwitch();
    }

    public void stop() {
        if (client == null) {
            return; // Prevents nasty error messages on program closure
        }
        client.closeConnection();
        client = null;
        bttnConnect.setText("Verbinden");
        bttnConnect.setOnAction(event -> {
            try {
                connectingToServer(event);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Override
    public void handleGameListUpdate(ReturnGameListDto gameList) {
        this.gameList=gameList;
        tVPlannedGames.getItems().clear();
        for(GamesProgressing startingGames : gameList.getStartingGame()) {
            tVPlannedGames.getItems().add(startingGames);
        }
        tVRunningGames.getItems().clear();
        for(GamesProgressing GamesProgressing : gameList.getRunningGames()){
            tVRunningGames.getItems().add(GamesProgressing);
        }
        tVFinishedGames.getItems().clear();
        for(GamesFinished gamesFinished : gameList.getFinishedGames()){
            tVFinishedGames.getItems().add(gamesFinished);
        }

    }

    @Override
    public void handleReturnFindTournament(ReturnTournamentListDto findTournament) {
        this.findTournament = findTournament;
        tVPlannedTournaments.getItems().clear();
        for(TournamentsUpcoming tournament : findTournament.getTournamentsUpcoming()){
            tVPlannedTournaments.getItems().add(tournament);
        }
        tVRunningTournaments.getItems().clear();
        for(TournamentsUpcoming tournament : findTournament.getTournamentsRunning()){
            tVPlannedTournaments.getItems().add(tournament);
        }
        tVFinishedTournaments.getItems().clear();
        for (TournamentsFinished tournament : findTournament.getTournamentsFinished()){
            tVFinishedTournaments.getItems().add(tournament);
        }
    }

    //
    // to join tournamentGame
    @Override
    public void handleReturnTournamentInfo(ReturnTournamentInfoDto tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
        int gameId = tournamentInfo.getTournamentInfo().getGameRunning().getGameId();
        client.sendMessage(new JoinGameAsObserverDto(client.getClientID(), gameId).toJson());
    }
}
