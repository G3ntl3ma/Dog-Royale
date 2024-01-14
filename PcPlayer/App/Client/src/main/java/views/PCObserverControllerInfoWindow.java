package views;

import Dog.Client.Interfaces.IClientObserverGameplay;
import Dtos.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PCObserverControllerInfoWindow implements Initializable {

    @FXML
    private TextField infoWindowName;
    @FXML
    private TextField infoWindowTurnier;
    @FXML
    private TextField infoWindowSpielfeld;
    @FXML
    private TextField infoWindowSpieleranzahl;
    @FXML
    private TextField infoWindowMaxZuege;
    @FXML
    private TextField infoWindowSpielzeit;
    @FXML
    private TextField infoWindowVisualisierungsDauer;
    @FXML
    private TextField infoWindowBedenkzeit;
    @FXML
    private TextField infoWindowAnzahlSpielfiguren;
    @FXML
    private TextField infoWindowAnzahlKarten;
    @FXML
    private TextField infoWindowBestrafungZug;

    private ReturnLobbyConfigDto lobbyConfig;

    public void setLobbyConfig(ReturnLobbyConfigDto lobbyConfig) {
        this.lobbyConfig = lobbyConfig;
    }

    @Override
    // sets infoWindow parameters with given lobbyConfig
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{infoWindowSpielfeld.setText(Integer.toString(lobbyConfig.getFieldsize()));
            infoWindowSpieleranzahl.setText(Integer.toString(lobbyConfig.getPlayerCount()));
            infoWindowMaxZuege.setText(Integer.toString(lobbyConfig.getMaximumTotalMoves()));
            infoWindowSpielzeit.setText(Integer.toString(lobbyConfig.getMaximumGameDuration()));
            infoWindowVisualisierungsDauer.setText(Integer.toString(lobbyConfig.getVisualizationTimePerMove()));
            infoWindowBedenkzeit.setText(Integer.toString(lobbyConfig.getThinkTimePerMove()));
            infoWindowAnzahlSpielfiguren.setText(Integer.toString(lobbyConfig.getFiguresPerPlayer()));
            infoWindowAnzahlKarten.setText(Integer.toString(lobbyConfig.getInitialCardsPerPlayer()));
            if (lobbyConfig.getConsequencesForInvalidMove() == 0){
                infoWindowBestrafungZug.setText("Von Runde ausschließen");
            }
            else {
                infoWindowBestrafungZug.setText("Aus dem Spiel kicken");
            }
            infoWindowTurnier.setText("-kein-");});

    }
}
