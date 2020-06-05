package controller;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
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
                validateBeforeUpdate();
                patientService.update(loadPatient());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        btnDelete.setOnAction(event -> {
            try {
                patientService.delete(loadPatient().getId());
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        });
    }

    public void initData(Patient patient) {
        Patient selectedPatient;
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
            logger.log(Level.ERROR, e);
        }

        return patient;
    }

    private void validateBeforeUpdate() throws FormException {
        if (txtPatientName.getText().isEmpty()) {
            throw new FormException("Inform the patient name!", "Patient Name is empty");
        }

        if (!txtPatientName.getText().matches("[A-Z a-zÀ-Ÿà-ÿ]+")) {
            throw new FormException("Patient name must be 50 characters and",
                    "Patient Name must have more than 50 characters or containst numbers");
        }

        if (cmbGender.equals("")) {
            throw new FormException("Select a gender!", "Combo box is empty");
        }

        if (txtAge.getText().isEmpty()) {
            throw new FormException("Input patient age!", "Age field is empty");
        }

        if (!txtAge.getText().matches("\\d+")) {
            throw new FormException("Patient age cant have letters!", "Age field has letters");
        }

        if (txtWeight.getText().isEmpty()) {
            throw new FormException("Enter a Weight", "Weight field is empty");
        }

        if (txtWeight.getText().matches("[0-9,]+")) {
            throw new FormException("Weight has letters!", "Weight field has letters");
        }

        if (txtVaccineDate.getEditor().getText().isEmpty()) {
            throw new FormException("Input vaccine date", "Vaccine Date is empty");
        }

        if (!txtVaccineDate.getEditor().getText().matches("[0-3][0-9]/[0-1][0-9]/[0-9]+")) {
            throw new FormException("try: 00/00/0000", "The date format is invalid");
        }
    }

}
