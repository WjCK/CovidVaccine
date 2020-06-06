package controller;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MainController implements Initializable {

    Logger logger;

    @FXML
    private JFXButton btnRegisterPatient;

    @FXML
    private JFXButton btnUpdatePatient;

    @FXML
    private JFXButton btnAbout;

    @FXML
    private JFXButton btnLearnMore;

    @FXML
    private JFXButton btnHome;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonFocus();

        btnHome.setOnAction(event -> {
            Parent screen;
            try {
                screen = FXMLLoader.load(getClass().getResource("/view/layouts/main.fxml"));
                Scene scene = btnHome.getScene();
                scene.setRoot(screen);
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        });

        btnRegisterPatient.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/layouts/createPatient.fxml"));
                Scene scene = btnRegisterPatient.getScene();

                root.translateXProperty().set(0 - scene.getWidth());

                Pane parentPane = (Pane) scene.getRoot();
                mainPane.getChildren().addAll(root);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1 -> {
                    parentPane.getChildren().remove(mainPane);
                });
                timeline.play();
            } catch (IOException e) {
                logger.log(Level.ERROR, e);
            }
        });

        btnUpdatePatient.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/layouts/updatePatient.fxml"));
                Scene scene = btnRegisterPatient.getScene();

                root.translateXProperty().set(0 - scene.getWidth());

                Pane parentPane = (Pane) scene.getRoot();
                mainPane.getChildren().addAll(root);

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.8), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(event1 -> {
                    parentPane.getChildren().remove(mainPane);
                });
                timeline.play();
            } catch (IOException e) {
                logger.log(Level.ERROR, e);
            }
        });
    }

    /**
     * set button focus in view
     */
    private void setButtonFocus() {
        btnRegisterPatient.setFocusTraversable(false);
        btnUpdatePatient.setFocusTraversable(false);
        btnAbout.setFocusTraversable(false);
        btnLearnMore.setFocusTraversable(false);
        btnHome.setFocusTraversable(false);
    }
}