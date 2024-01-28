package views;

import Dtos.CustomClasses.PlayerPoints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class represents the end screen of the game for 2 player.
 * It contains the winner of the game.
 *
 * @author gruppe 8
 */
public class PCObserverControllerEndScreen2Player implements Initializable {
    @FXML
    private Button bttnExit;

    @FXML
    private Label lblPlayer1;

    @FXML
    private Label lblPlayer2;

    private ArrayList<PlayerPoints> winnerOrder;

    private Label[] playerLables;

    /**
     * Method to initialize the end screen.
     * @param url A URL giving the base location of the .fxml file.
     * @param resourceBundle A ResourceBundle instance.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerLables = new Label[]{ lblPlayer1, lblPlayer2 };
    }

    /**
     * Method to close the end screen.
     * @param event The event that was fired.
     */
    @FXML
    public void bttnExit(ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) bttnExit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * Method to set the winner of the game.
     * @param winnerOrder The winner of the game.
     */
    public void initData(ArrayList<PlayerPoints> winnerOrder){
        this.winnerOrder = winnerOrder;
        for(int i = 0 ; i < winnerOrder.size(); ++i){
            playerLables[i].setText(winnerOrder.get(i).getName());
        }
    }


}
