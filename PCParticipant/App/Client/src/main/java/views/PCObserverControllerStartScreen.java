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

/**
 * observer controller for the start screen
 *
 * @author gruppe 8
 */
public class PCObserverControllerStartScreen implements Initializable {

    @FXML
    private Button bttnExit;

    @FXML
    private Button bttnStart;
    int fastness=10000;

    /**
     * shift the bttnStart button horizontally by -300 units and fade it out
     */
    @FXML
    protected void handleImageTranslate() {
        imageTranslate(bttnStart,-300,0);
        imageFade(bttnStart);
    }
    int movement=2;

    /**
     * Translates the bttnStart and bttnExit buttons vertically by a distance specified by the movement variable
     */
    public void handleUp(){
        imageTranslate(bttnStart, 0,movement);
        imageTranslate(bttnExit, 0,movement);
        movement= (int) (movement*1.5);
    }

    /**
     * Translates the bttnStart and bttnExit buttons vertically by a distance specified by the movement variable
     */
    public void handleDown(){
        imageTranslate(bttnStart, 0,-movement);
        imageTranslate(bttnExit, 0,-movement);
        movement= (int) (movement*1.5);
    }

    /**
     * Translates the bttnStart and bttnExit buttons horizontally by a distance specified by the movement variable
     */
    public void handleLeft(){
        imageTranslate(bttnStart, -movement,0);
        imageTranslate(bttnExit, -movement,0);
        movement= (int) (movement*1.5);
    }

    /**
     * Translates the bttnStart and bttnExit buttons horizontally by a distance specified by the movement variable
     */
    public void handleRight(){
        imageTranslate(bttnStart, movement,0);
        imageTranslate(bttnExit, movement,0);
        movement= (int) (movement*1.5);
    }

    /**
     * gives functionality to the "Start" Button. Is directly ripped and modified from "PCObserverControllerMenu
     *
     * @param event represents the event triggered by a user action
     * @throws IOException if an input or output exception occurs
     * @throws InterruptedException if a thread is interrupted
     */
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

    /**
     * gives functionality to the exit button
     *
     * @param event represents the event triggered by a user action
     */
    @FXML
    public void bttnExit(ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) bttnExit.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /**
     * moves the image by the specified distances along the X and Y axes, creating a visual effect
     *
     * @param imgName Represents a JavaFX Node object that is intended to undergo translation animation
     * @param setByX Represents the distance by which the image is translated along the X-axis.
     * @param setByY Represents the distance by which the image is translated along the Y-axis.
     */
    public void imageTranslate(Node imgName, int setByX, int setByY){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(imgName);
        translate.setDuration(Duration.millis(500));
        translate.setByX(setByX);
        translate.setByY(-setByY);
        translate.setAutoReverse(true);
        translate.play();
    }

    /**
     * rotates the image by 360 degrees along the Z-axis, creating a visual effect
     *
     * @param imgName Represents a JavaFX Node object that is intended to undergo rotation animation
     * @param speed Represents the speed at which the image is rotated
     */
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

    /**
     * fades the image out, creating a visual effect
     *
     * @param imgName Represents a JavaFX Node object that is intended to undergo fade animation
     */
    // fade
    public void imageFade(Node imgName){
        FadeTransition fade = new FadeTransition();
        fade.setNode(imgName);
        fade.setDuration(Duration.millis(500));
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();}

    /**
     * Method to initialize the start screen.
     *
     * @param url Represents the URL of the location from which the FXML file is loaded
     * @param resourceBundle Represents a ResourceBundle object used for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
