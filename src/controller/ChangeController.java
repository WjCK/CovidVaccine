package controller;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.sql.Date;
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
import model.Patient;
import service.PatientService;
import service.PatientServiceImpl;
import util.FormException;

public class ChangeController implements Initializable {

    Logger logger;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private JFXTextField txtWeight;

    @FXML
    private JFXDatePicker txtVaccineDate;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    private Patient selectedPatient;

    PatientService patientService = new PatientServiceImpl();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnCancel.setOnAction((event -> {
            Parent screen;
            try {
                screen = FXMLLoader.load(getClass().getResource("/view/layouts/main.fxml"));
                Scene scene = btnCancel.getScene();
                scene.setRoot(screen);
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }));

        btnUpdate.setOnAction((event -> {
            try {
                patientService.update(loadPatient());
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        }));

        btnDelete.setOnAction(event -> {
            try {
                patientService.delete(loadPatient().getId());
            } catch (Exception e) {
            }
        });
    }

    public void initData(Patient patient) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        selectedPatient = patient;
        txtID.setText(Integer.toString(selectedPatient.getId()));
        txtPatientName.setText(selectedPatient.getPatientName());
        cmbGender.getItems().addAll("Male", "Female");
        if (patient.getGender().equals("Male")) {
            cmbGender.setValue("Male");
        } else {
            cmbGender.setValue("Female");
        }
        txtAge.setText(Integer.toString(selectedPatient.getAge()));
        txtWeight.setText(Double.toString(selectedPatient.getWeight()));

        txtVaccineDate.getEditor().setText(sdf.format(selectedPatient.getVaccineDate()));
    }

    private Patient loadPatient() {
        Patient patient = new Patient();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        patient.setId(Integer.parseInt(txtID.getText()));
        patient.setPatientName(txtPatientName.getText());
        patient.setAge(Integer.parseInt(txtAge.getText()));
        patient.setGender(cmbGender.getValue());
        patient.setWeight(Double.parseDouble(txtWeight.getText()));
        try {
            java.util.Date date = sdf.parse(txtVaccineDate.getEditor().getText());
            patient.setVaccineDate(new java.sql.Date(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return patient;
    }

}
