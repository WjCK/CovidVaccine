package controller;

import java.io.IOException;
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
import javafx.scene.layout.AnchorPane;
import model.Patient;
import service.PatientService;
import service.PatientServiceImpl;
import util.FormException;

public class ChangeController implements Initializable {

    Logger logger;

    @FXML
    private AnchorPane searchAnchorPane;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validateForm();

        /* load previous screen */
        btnCancel.setOnAction((event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/layouts/updatePatient.fxml"));
            try {
                Parent parent = loader.load();
                searchAnchorPane.getChildren().setAll(parent);
            } catch (IOException e) {
                logger.log(Level.ERROR, e);
            }
        }));

        /* update appointment in database */
        btnUpdate.setOnAction((event -> {
            try {
                validateBeforeUpdate();
                patientService.update(loadPatient());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        /* delete appointment in database */
        btnDelete.setOnAction(event -> {
            try {
                patientService.delete(loadPatient().getId());
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        });
    }

    /**
     * Form validation
     * 
     * @throws FormException
     */
    private void validateForm() {
        txtPatientName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) {
                if (!newValue.matches("[A-Z a-zÀ-Ÿà-ÿ]+") || oldValue.length() == 0) {
                    txtPatientName.setText("");
                }
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
                if (!newValue.matches("[0-9.]+") || oldValue.length() == 0)
                    txtWeight.setText("");
            } else if (!newValue.matches("[0-9.]+") || newValue.length() > 4)
                txtWeight.setText(oldValue);
        });

    }

    /**
     * set patient info in text field, combo box and date picker
     * 
     * @param patient
     */
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

    /**
     * Load patient to parse info to database
     * 
     * @return
     */
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

    /**
     * Validate form before update in database
     * 
     * @throws FormException
     */
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
