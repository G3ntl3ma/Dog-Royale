package views;

import Dtos.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the observer info window
 *
 * @author gruppe 8
 */
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

    /**
     * Method to set the lobby config
     * @param lobbyConfig the lobby config
     */
    public void setLobbyConfig(ReturnLobbyConfigDto lobbyConfig) {
        this.lobbyConfig = lobbyConfig;
    }

    /**
     * update various JavaFX UI components with information from the lobbyConfig
     *
     * @param url represents the URL of the location from which the FXML file is loaded
     * @param resourceBundle a ResourceBundle object used for localization
     */
    @Override
    // sets infoWindow parameters with given lobbyConfig
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{infoWindowSpielfeld.setText(Integer.toString(lobbyConfig.getFieldsize()));
            infoWindowSpieleranzahl.setText(Integer.toString(lobbyConfig.getMaxPlayerCount()));
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
