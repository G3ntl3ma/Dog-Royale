package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This Controller is responsible for all selectValue Windows
 * Only the Method closeStage should be used outside the Buttons
 *
 * @author : leisen
 */
public class selectValueController {

    private Stage stage;

    @FXML
    private Button startCard1Startbtn;
    @FXML
    private Button startCard1Thirteenbtn;
    @FXML
    private Button startCard2Startbtn;
    @FXML
    private Button startCard2Onebtn;
    @FXML
    private Button startCard2Elevenbtn;
    @FXML
    private Button plusMinus4Plusbtn;
    @FXML
    private Button plusMinus4Minusbtn;
    @FXML
    private Button oneToSeven1btn;
    @FXML
    private Button oneToSeven2btn;
    @FXML
    private Button oneToSeven3btn;
    @FXML
    private Button oneToSeven4btn;
    @FXML
    private Button oneToSeven5btn;
    @FXML
    private Button oneToSeven6btn;
    @FXML
    private Button oneToSeven7btn;

    /**
     * sets the selected value in a CardHandler to 1 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select1Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("startCard2Onebtn")){
            startCard2Onebtn.setDisable(true);
            startCard2Startbtn.setDisable(false);
            startCard2Elevenbtn.setDisable(false);
        } else if (pressed.getId().equals("oneToSeven1btn")) {
            oneToSeven1btn.setDisable(true);
            oneToSeven2btn.setDisable(false);
            oneToSeven3btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }


        CardHandler.setSelectedValue(1);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 2 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select2Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if (pressed.getId().equals("oneToSeven2btn")) {
            oneToSeven2btn.setDisable(true);
            oneToSeven1btn.setDisable(false);
            oneToSeven3btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }

        CardHandler.setSelectedValue(2);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 3 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select3Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if (pressed.getId().equals("oneToSeven3btn")) {
            oneToSeven3btn.setDisable(true);
            oneToSeven1btn.setDisable(false);
            oneToSeven2btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }
        CardHandler.setSelectedValue(3);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 4 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select4Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("plusMinus4Plusbtn")){
            plusMinus4Plusbtn.setDisable(true);
            plusMinus4Minusbtn.setDisable(false);
        }else if (pressed.getId().equals("oneToSeven4btn")){
            oneToSeven4btn.setDisable(true);
            oneToSeven1btn.setDisable(false);
            oneToSeven2btn.setDisable(false);
            oneToSeven3btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }
        CardHandler.setSelectedValue(4);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 5 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select5Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if (pressed.getId().equals("oneToSeven5btn")) {
            oneToSeven5btn.setDisable(true);
            oneToSeven1btn.setDisable(false);
            oneToSeven2btn.setDisable(false);
            oneToSeven3btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }
        CardHandler.setSelectedValue(5);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 6 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select6Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if (pressed.getId().equals("oneToSeven6btn")) {
            oneToSeven6btn.setDisable(true);
            oneToSeven3btn.setDisable(false);
            oneToSeven1btn.setDisable(false);
            oneToSeven2btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven7btn.setDisable(false);
        }
        CardHandler.setSelectedValue(6);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 7 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select7Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if (pressed.getId().equals("oneToSeven7btn")) {
            oneToSeven7btn.setDisable(true);
            oneToSeven1btn.setDisable(false);
            oneToSeven2btn.setDisable(false);
            oneToSeven3btn.setDisable(false);
            oneToSeven4btn.setDisable(false);
            oneToSeven5btn.setDisable(false);
            oneToSeven6btn.setDisable(false);
        }
        CardHandler.setSelectedValue(7);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to -4 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void selectMinus4Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("plusMinus4Minusbtn")){
            plusMinus4Minusbtn.setDisable(true);
            plusMinus4Plusbtn.setDisable(false);
        }
        CardHandler.setSelectedValue(-4);
        CardHandler.setIsStarter(false);
    }

    /**
     * handler for selecting the start event in a game
     *
     * @param e represents an ActionEvent
     */
    public void selectStartEvent(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("startCard1Startbtn")){
            startCard1Startbtn.setDisable(true);
            startCard1Thirteenbtn.setDisable(false);
        }
        else if(pressed.getId().equals("startCard2Startbtn")){
            startCard2Startbtn.setDisable(true);
            startCard2Onebtn.setDisable(false);
            startCard2Elevenbtn.setDisable(false);
        }


        if (PieceImages.currentPiece != null) {
            PieceImages.currentPiece.deselect();
        }
        CardHandler.setSelectedValue(0);
        CardHandler.setIsStarter(true);
        System.out.println("clientPlayerIndex: " + PieceImages.clientPlayerIndex);
        System.out.println("PieceHandler.pieces.length: " + PieceHandler.pieces.length);
        for (int i = 0; i < PieceHandler.pieces.length; i++)
        {

            System.out.println("Players Piece: " + PieceHandler.pieces[i].player + " isOnBench: " + PieceHandler.pieces[i].isOnBench + "my PlayerIndex: " + PieceImages.clientPlayerIndex);
            if (PieceHandler.pieces[i].player == PieceImages.clientPlayerIndex && PieceHandler.pieces[i].isOnBench)
            {
                System.out.println("current Piece is now" + PieceHandler.pieces[i].fieldImage.getPieceIndex());
                PieceImages.currentPiece = PieceHandler.pieces[i].fieldImage;
                break;
            }
        }
    }

    /**
     * sets the selected value in a CardHandler to 11 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select11Event(ActionEvent e){

        //Give the Player Feedback that a Value has been selected
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("startCard2Elevenbtn")){
            startCard2Elevenbtn.setDisable(true);
            startCard2Onebtn.setDisable(false);
            startCard2Startbtn.setDisable(false);
        }
        CardHandler.setSelectedValue(11);
        CardHandler.setIsStarter(false);
    }

    /**
     * sets the selected value in a CardHandler to 13 and updates a boolean flag in the CardHandler
     * to indicate that the selected value is not a starter value
     *
     * @param e represents an ActionEvent
     */
    public void select13Event(ActionEvent e){
        Button pressed = (Button)e.getSource();
        if(pressed.getId().equals("startCard1Thirteenbtn")){
            startCard1Startbtn.setDisable(false);
            startCard1Thirteenbtn.setDisable(true);
        }
        CardHandler.setSelectedValue(13);
        CardHandler.setIsStarter(false);
    }
}
