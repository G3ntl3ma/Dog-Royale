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

public class PCObserverControllerEndScreen2Player implements Initializable {
    @FXML
    private Button bttnExit;

    @FXML
    private Label lblPlayer1;

    @FXML
    private Label lblPlayer2;

    private ArrayList<PlayerPoints> winnerOrder;

    private Label[] playerLables;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerLables = new Label[]{ lblPlayer1, lblPlayer2 };
    }

    @FXML
    public void bttnExit(ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) bttnExit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void initData(ArrayList<PlayerPoints> winnerOrder){
        this.winnerOrder = winnerOrder;
        for(int i = 0 ; i < winnerOrder.size(); ++i){
            playerLables[i].setText(winnerOrder.get(i).getName());
        }
    }


}
