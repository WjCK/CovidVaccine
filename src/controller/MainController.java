package controller;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {

    Logger logger;

    @FXML
    private JFXButton btnRegisterPatient;

    @FXML
    private JFXButton btnUpdatePatient;

    @FXML
    private JFXButton btnDeletePatient;

    @FXML
    private JFXButton btnCheckPatient;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void loadCreatePatient(ActionEvent event) {
        Pane pane;
        try {
            pane = FXMLLoader.load(getClass().getResource("/view/layouts/createPatient.fxml"));
            mainPane.getChildren().setAll(pane);
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

    @FXML
    void loadDeletePatient(ActionEvent event) {

    }

    @FXML
    void loadUpdatePatient(ActionEvent event) {

    }

    @FXML
    void loadCheckPatient(ActionEvent event) {

    }

}