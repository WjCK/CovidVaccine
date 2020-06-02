package controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;
import service.PatientService;
import service.PatientServiceImpl;
import util.CustomException;

public class UpdateController implements Initializable {

    @FXML
    private TableView<Patient> tableView;

    @FXML
    private TableColumn<Patient, Integer> clID;

    @FXML
    private TableColumn<Patient, String> clPatientName;

    @FXML
    private TableColumn<Patient, String> clGender;

    @FXML
    private TableColumn<Patient, Integer> clAge;

    @FXML
    private TableColumn<Patient, Double> clWeight;

    @FXML
    private TableColumn<Patient, Date> clVaccineDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PatientService patientService = new PatientServiceImpl();
        ObservableList<Patient> lista = FXCollections.observableArrayList();
        try {
            lista.addAll(patientService.list());
            if (!lista.isEmpty()) {
                // System.out.println(lista.get(0).getId());
                tableView.setItems(lista);
                clID.setCellValueFactory(new PropertyValueFactory<>("id"));
                clPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
                clAge.setCellValueFactory(new PropertyValueFactory<>("age"));
                clGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                clWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
                clVaccineDate.setCellValueFactory(new PropertyValueFactory<>("vaccineDate"));
                System.out.println(clAge);
            }
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

}