package views;

import Dtos.CustomClasses.PlayerPoints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * this class represents the end screen of the game for 6 player.
 * It contains the winner of the game.
 *
 * @author gruppe 8
 */
public class PCObserverControllerEndScreen6Player implements Initializable {
    @FXML
    private Button bttnExit;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lblPlayer1;

    @FXML
    private Label lblPlayer2;

    @FXML
    private Label lblPlayer3;

    @FXML
    private Label lblPlayer4;

    @FXML
    private Label lblPlayer5;

    @FXML
    private Label lblPlayer6;

    @FXML
    private VBox vbPlayer4;

    @FXML
    private VBox vbPlayer5;

    @FXML
    private VBox vbPlayer6;

    private Label[] playerLables;
    private VBox[] playerVBoxes;

    private ArrayList<PlayerPoints> winnerOrder;

    /**
     * Method to initialize the end screen.
     * @param url A URL giving the base location of the .fxml file.
     * @param resourceBundle A ResourceBundle instance.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerLables = new Label[]{ lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4, lblPlayer5, lblPlayer6 };
        playerVBoxes = new VBox[]{ vbPlayer4, vbPlayer5, vbPlayer6 };
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
            if(i >= 3) {
                playerVBoxes[i - 3].setVisible(true);
            }
        }
    }

}
