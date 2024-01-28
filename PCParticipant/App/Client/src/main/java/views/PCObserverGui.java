package views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is the main class of the PCObserverGui.
 * It starts the GUI and handles the exit of the application.
 *
 * @author mtwardy
 */
public class PCObserverGui extends Application {
    private PCObserverControllerStartScreen pcOCStartScreen;

    /**
     * This method starts the GUI and loads the fxml file.
     *
     * @param stage represents the main window or stage of the JavaFX application where the user interface will be displayed
     * @throws IOException if the fxml file can't be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        String css = this.getClass().getResource("style.css").toExternalForm();

        FXMLLoader fxmlLoader = new FXMLLoader(PCObserverGui.class.getResource("startScreen.fxml"));
        Scene gameOverview = new Scene(fxmlLoader.load());
        gameOverview.getStylesheets().add(css);
        pcOCStartScreen = fxmlLoader.getController();
        stage.setTitle("Dog Digital");
        stage.setScene(gameOverview);
        stage.getIcons().add(new Image("icon.png"));
        stage.setMinWidth(400);
        stage.setMinHeight(480);
        stage.centerOnScreen();
        stage.show();

        stage.setOnCloseRequest(event -> exit(stage));
    }

    /**
     * This method starts the application.
     *
     * @param args command line arguments
     */
    public static void main (String[]args){
            launch();
    }

    /**
     * this method is called when the exit button is pressed.
     *
     * @param stage represents the main window or stage of the JavaFX application where the user interface will be displayed
     */
    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Anwendung schließen?");

        Image icon = new Image("icon.png");
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        dialog.getStyleClass().add("dialog");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(icon);

        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}

