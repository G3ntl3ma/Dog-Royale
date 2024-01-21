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

public class PCObserverGui extends Application {
    private PCObserverControllerStartScreen pcOCStartScreen;

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
        stage.show();

        stage.setOnCloseRequest(event -> exit(stage));
    }

    public static void main (String[]args){
            launch();
        }

     //Method gets called when pressing the X in the top right Corner
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

