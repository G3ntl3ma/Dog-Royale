package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    }

    public static void main (String[]args){
            launch();
        }
}