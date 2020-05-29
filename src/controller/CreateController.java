package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class CreateController implements Initializable {

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private JFXButton btnRegister2;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXDatePicker dateAppointment;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validateForm();
    }

    private void validateForm() {
        txtPatientName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && !newValue.matches("[A-Z a-zÀ-Ÿà-ÿ]+")) {
                txtPatientName.setText("");
            }
            if (!newValue.matches("[A-Z a-zÀ-Ÿà-ÿ]+") || (newValue.length() > 50)) {
                txtPatientName.setText(oldValue);
            }
        });

        txtAge.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && !newValue.matches("\\d+")) {
                txtAge.setText("");
            }
            if (newValue.length() < 0 && !newValue.matches("\\d+")) {
                txtAge.setText(oldValue);
            }
        });

        txtWeight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() && !newValue.matches("[0-9]{1,2}[,][0-9]{1}")) {
                txtWeight.setText(oldValue);
            }
            if (newValue.isEmpty() || !newValue.matches("[0-9]{1,2}[,][0-9]{1}")) {
                txtWeight.setText(oldValue);
            }
        });

    }

}