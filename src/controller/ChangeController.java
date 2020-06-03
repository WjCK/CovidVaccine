package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Patient;

public class ChangeController implements Initializable {

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

    private Patient selectedPatient;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
