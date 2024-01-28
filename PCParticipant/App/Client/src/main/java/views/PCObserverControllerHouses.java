package views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the observer houses
 *
 * @author gruppe 8
 */
public class PCObserverControllerHouses implements Initializable {
    @FXML
    private ScrollPane scrollPaneHolderPane;
    public Pane paneContent;

    public HouseBoard houseBoard;
    private DrawBoard drawBoard;
    private PieceHandler pieceHandler;

    PieceImages currentPiece = new PieceImages(paneContent);

    /**
     * Method to set the board attributes
     * @param houseBoard the board
     * @param pieceHandler the piece handler
     */
    public void setBoardAttr(HouseBoard houseBoard, PieceHandler pieceHandler){
        this.houseBoard = houseBoard;
        this.pieceHandler = pieceHandler;
    }

    /**
     * initializes the UI elements when the associated FXML file is loaded
     *
     * @param url represents the URL of the location from which the FXML file is loaded
     * @param resourceBundle a ResourceBundle object used for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneContent = new Pane();
        paneContent.getStyleClass().add("pane");

        Platform.runLater(() ->{
            currentPiece = new PieceImages(paneContent);
            // configure scrollpane
            scrollPaneHolderPane.setPannable(true);
            scrollPaneHolderPane.setContent(paneContent);

            //houseBoard = new HouseBoard(numPlayers, numHouseFields);
            paneContent.setPrefSize(houseBoard.width, houseBoard.height);
            drawBoard = new DrawBoard(paneContent, houseBoard, pieceHandler);
        });
    }
}
