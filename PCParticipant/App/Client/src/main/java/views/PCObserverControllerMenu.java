package views;

import Dog.Client.Client;
import Dog.Client.Interfaces.IClientObserverMenu;
import Dtos.*;
import Dtos.CustomClasses.FinishedGames;
import Dtos.CustomClasses.RunningGame;
import javafx.application.Platform;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class PCObserverControllerMenu implements Initializable, IClientObserverMenu {
    @FXML
    private TextField tfIP;
    @FXML
    private TextField tfPort;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextArea tAClientLog;
    @FXML
    private Button bttnConnect;
    @FXML
    private Button bttnObserve;
    @FXML
    private Button bttnReloadTournamentList;
    @FXML
    private CheckBox cBIsObserver;
    @FXML
    private TableView<RunningGame> tVPlannedGames;
    @FXML
    private  TableView<RunningGame> tVRunningGames;
    @FXML
    private TableView<FinishedGames> tVFinishedGames;
    @FXML
    private Button bttnReloadGameList;
    @FXML
    private TableColumn<RunningGame, Integer> columnName;
    @FXML
    private TableColumn<RunningGame, Integer> columnMaxPlayer;
    @FXML
    private TableColumn<RunningGame, Integer> columnAlreadyJoined;
    @FXML
    private TableColumn<RunningGame, Integer> clmnNameRunning;
    @FXML
    private TableColumn<RunningGame, Integer> clmnMaxPlayersRunning;
    @FXML
    private TableColumn<RunningGame, Integer> clmnAlreadyJoinedRunning;
    @FXML
    private TableColumn<FinishedGames, Integer> clmnNameFinished;
    @FXML
    private TableColumn<FinishedGames, Array> clmnResultsFinished;
    @FXML
    private TableColumn<FinishedGames, Boolean> clmnWasCanceled;
    @FXML
    private Client client;
    private ReturnGameListDto gameList;
    private PCObserverControllerGameplay controller;


    @FXML
    public void switchToGameplay(ActionEvent event) throws IOException {
        if(client == null) {
            return;
        }

        int gameID = gameList.getStartingGame().get(tVPlannedGames.getSelectionModel().getSelectedIndex()).getGameId();
        client.sendMessage(new JoinGameAsObserverDto(gameID, client.getClientID()).toJson());

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
        stageGameplay.setMinHeight(720);
        stageGameplay.show();

        // call stop method when stage is closed
        stageGameplay.setOnCloseRequest(windowEvent -> {
            controller.stop();
        });
    }

    /** //////////////////////////////////////////////////////////////////////////////////////TODO DELETE THIS METHOD
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
        stageGameplay.setMinHeight(960);
        stageGameplay.show();

        // call stop method when stage is closed
        stageGameplay.setOnCloseRequest(windowEvent -> {
            controller.stop();
        });
    }
    //////////////////////////////////////////////////////////////////////////////////////////TODO DELETE fakeGameplay METHOD

    public void requestGameList(){
        client.sendMessage(new RequestGameListDto(client.getClientID(), 100,100,100).toJson());
    }

    @FXML
    public void reloadGameList(){
        client.sendMessage(new RequestGameListDto(client.getClientID(), 100,100,100).toJson());
    }
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

            // configure table columns for single games
            columnName.setCellValueFactory(new PropertyValueFactory<>("gameId"));
            columnMaxPlayer.setCellValueFactory(new PropertyValueFactory<>("maxPlayerCount"));
            columnAlreadyJoined.setCellValueFactory(new PropertyValueFactory<>("currentPlayerCount"));
            clmnNameRunning.setCellValueFactory(new PropertyValueFactory<>("gameId"));
            clmnMaxPlayersRunning.setCellValueFactory(new PropertyValueFactory<>("maxPlayerCount"));
            clmnAlreadyJoinedRunning.setCellValueFactory(new PropertyValueFactory<>("currentPlayerCount"));
            clmnNameFinished.setCellValueFactory(new PropertyValueFactory<>("gameId"));
            clmnResultsFinished.setCellValueFactory(new PropertyValueFactory<>("winnerOrder"));
            clmnWasCanceled.setCellValueFactory(new PropertyValueFactory<>("wasCanceled"));
        });
        tVPlannedGames.getSelectionModel().selectedItemProperty().addListener((observableValue, oldSelection, newSelection) -> {
            bttnObserve.setDisable(newSelection == null);
        });

        tfIP.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        tfUsername.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });

        tfPort.textProperty().addListener((observableValue, oldValue, newValue) -> {
            bttnConnect.setDisable(tfIP.getText().isEmpty() || tfUsername.getText().isEmpty() || tfPort.getText().isEmpty());
        });
    }

    public void stop() {
        if (client == null) {
            return; // Prevents nasty error messages on program closure
        }
        client.closeConnection();
        client = null;
        bttnConnect.setText("Verbinden");
        bttnConnect.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    connectingToServer(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handleGameListUpdate(ReturnGameListDto gameList) {
        this.gameList=gameList;
        tVPlannedGames.getItems().clear();
        for(RunningGame startingGames : gameList.getStartingGame()) {
            tVPlannedGames.getItems().add(startingGames);
        }
        tVRunningGames.getItems().clear();
        for(RunningGame runningGame : gameList.getRunningGames()){
            tVRunningGames.getItems().add(runningGame);
        }
        tVFinishedGames.getItems().clear();
        for(FinishedGames finishedGames : gameList.getFinishedGames()){
            tVFinishedGames.getItems().add(finishedGames);
        }

    }
}
