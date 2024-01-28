package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**@author : leisen
 * This Controller is responsible for all selectValue Windows
 * Only the Method closeStage should be used outside the Buttons
 */
public class selectValueController {

    private Stage stage;

    public void select1Event(ActionEvent e){
        CardHandler.setSelectedValue(1);
        CardHandler.setIsStarter(false);
    }

    public void select2Event(ActionEvent e){
        CardHandler.setSelectedValue(2);
        CardHandler.setIsStarter(false);
    }
    public void select3Event(ActionEvent e){
        CardHandler.setSelectedValue(3);
        CardHandler.setIsStarter(false);
    }
    public void select4Event(ActionEvent e){
        CardHandler.setSelectedValue(4);
        CardHandler.setIsStarter(false);
    }
    public void select5Event(ActionEvent e){
        CardHandler.setSelectedValue(5);
        CardHandler.setIsStarter(false);
    }
    public void select6Event(ActionEvent e){
        CardHandler.setSelectedValue(6);
        CardHandler.setIsStarter(false);
    }
    public void select7Event(ActionEvent e){
        CardHandler.setSelectedValue(7);
        CardHandler.setIsStarter(false);
    }

    public void selectMinus4Event(ActionEvent e){
        CardHandler.setSelectedValue(-4);
        CardHandler.setIsStarter(false);
    }
    public void selectStartEvent(ActionEvent e){
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

    public void select11Event(ActionEvent e){
        CardHandler.setSelectedValue(11);
        CardHandler.setIsStarter(false);
    }

    public void select13Event(ActionEvent e){
        CardHandler.setSelectedValue(13);
        CardHandler.setIsStarter(false);
    }
}
