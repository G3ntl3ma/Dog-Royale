package views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class PCObserverControllerHouses implements Initializable {
    @FXML
    private ScrollPane scrollPaneHolderPane;
    public Pane paneContent;

    public HouseBoard houseBoard;
    private DrawBoard drawBoard;
    private PieceHandler pieceHandler;

    public void setBoardAttr(HouseBoard houseBoard, PieceHandler pieceHandler){
        this.houseBoard = houseBoard;
        this.pieceHandler = pieceHandler;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneContent = new Pane();
        paneContent.getStyleClass().add("pane");

        Platform.runLater(() ->{
            // configure scrollpane
            scrollPaneHolderPane.setPannable(true);
            scrollPaneHolderPane.setContent(paneContent);

            //houseBoard = new HouseBoard(numPlayers, numHouseFields);
            paneContent.setPrefSize(houseBoard.width, houseBoard.height);
            drawBoard = new DrawBoard(paneContent, houseBoard, pieceHandler);
        });
    }
}
