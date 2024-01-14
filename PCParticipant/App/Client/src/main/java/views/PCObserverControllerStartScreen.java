package views;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class PCObserverControllerStartScreen implements Initializable {

    @FXML
    private Button bttnExit;

    @FXML
    private Button bttnStart;
    int fastness=10000;
    @FXML
    protected void handleImageTranslate() {
        imageTranslate(bttnStart,-300,0);
        imageFade(bttnStart);
    }
    int movement=2;
    public void handleUp(){
        imageTranslate(bttnStart, 0,movement);
        imageTranslate(bttnExit, 0,movement);
        movement= (int) (movement*1.5);
    }
    public void handleDown(){
        imageTranslate(bttnStart, 0,-movement);
        imageTranslate(bttnExit, 0,-movement);
        movement= (int) (movement*1.5);
    }
    public void handleLeft(){
        imageTranslate(bttnStart, -movement,0);
        imageTranslate(bttnExit, -movement,0);
        movement= (int) (movement*1.5);
    }
    public void handleRight(){
        imageTranslate(bttnStart, movement,0);
        imageTranslate(bttnExit, movement,0);
        movement= (int) (movement*1.5);
    }

    // gives functionality to the "Start" Button. Is directly ripped and modified from "PCObserverControllerMenu
    @FXML
    public void switchToObserverMenu(ActionEvent event) throws IOException, InterruptedException {

        String css = this.getClass().getResource("style.css").toExternalForm();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("observerMenu.fxml"));
        Parent rootMenu = fxmlLoader.load();
        Stage stageMenu = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sceneMenu = new Scene(rootMenu);
        sceneMenu.getStylesheets().add(css);
        stageMenu.setScene(sceneMenu);
        stageMenu.setTitle("Dog Digital");
        stageMenu.setMinWidth(960);
        stageMenu.setMinHeight(800);
        stageMenu.show();

        PCObserverControllerMenu controller = fxmlLoader.getController();
        stageMenu.setOnCloseRequest(windowEvent -> controller.stop());
    }

// gives functionality to the exit button
    @FXML
    public void bttnExit(ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) bttnExit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void imageTranslate(Node imgName, int setByX, int setByY){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imgName);
        translate.setDuration(Duration.millis(500));
        translate.setByX(setByX);
        translate.setByY(-setByY);
        translate.setAutoReverse(true);
        translate.play();}

    public void imageRotate(Node imgName,int speed) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(imgName);
        rotate.setDuration(Duration.millis(speed));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.play();

}
    // fade
    public void imageFade(Node imgName){
        FadeTransition fade = new FadeTransition();
        fade.setNode(imgName);
        fade.setDuration(Duration.millis(500));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
