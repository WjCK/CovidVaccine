package controller;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Patient;
import service.PatientService;
import service.PatientServiceImpl;
import util.FormException;

public class CreateController implements Initializable {

    @FXML
    private AnchorPane anchorPane2;

    @FXML
    private Pane createPane;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXDatePicker dateAppointment;

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    Logger logger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGender.getItems().add("Male");
        cmbGender.getItems().add("Female");
        cmbGender.setValue("value");

        btnSave.setOnAction((event -> {
            try {
                validateForm();
                try {
                    PatientService patientService = new PatientServiceImpl();
                    patientService.create(loadPatient());
                } catch (Exception e) {
                    logger.log(Level.ERROR, e);
                }
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }));

        btnCancel.setOnAction(event -> {
            Parent screen;
            try {
                screen = FXMLLoader.load(getClass().getResource("/view/layouts/main.fxml"));
                Scene scene = btnCancel.getScene();
                scene.setRoot(screen);
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        });
    }

    /**
     * Form validation
     */
    private void validateForm() {
        txtPatientName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                if (!newValue.matches("[A-Z a-zÀ-Ÿà-ÿ]+") || oldValue.length() != 0)
                    txtPatientName.setText("");
            } else if (!newValue.matches("[A-Z a-zÀ-Ÿà-ÿ]+") || (newValue.length() > 50)) {
                txtPatientName.setText(oldValue);
            }
        });

        txtAge.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                if (!newValue.matches("\\d+") || oldValue.length() == 0)
                    txtAge.setText("");
            } else if (!newValue.matches("\\d+") || newValue.length() < 0)
                txtAge.setText(oldValue);
        });

        txtWeight.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                if (!newValue.matches("[0-9]{1,2}[,][0-9]{1}") || oldValue.length() == 0)
                    txtWeight.setText("");
            } else if (!newValue.matches("[0-9]{1,2}[,][0-9]{1}") || newValue.length() == 0)
                txtWeight.setText(oldValue);
        });

        if (dateAppointment.getValue() == null) {
            try {
                throw new FormException("Error", "Try inserting a date, please");
            } catch (FormException e) {
                e.printStackTrace();
            }
        }

        if (cmbGender.getValue().isEmpty()) {
            try {
                throw new FormException("Error", "Select a gender please");
            } catch (FormException e) {
                e.printStackTrace();
            }
        }
    }

    private Patient loadPatient() {
        Patient patient = new Patient();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        patient.setPatientName(txtPatientName.getText());
        patient.setAge(Integer.parseInt(txtAge.getText()));
        patient.setGender(cmbGender.getValue());
        patient.setWeight(Double.parseDouble(txtWeight.getText()));
        try {
            Date date = sdf.parse(dateAppointment.getEditor().getText());
            patient.setVaccineDate(new java.sql.Date(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return patient;
    }

}