package controller;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Patient;
import service.PatientService;
import service.PatientServiceImpl;
import util.CustomException;

public class UpdateController implements Initializable {

    Logger logger;

    @FXML
    private AnchorPane updateAnchorPane;

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

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnNext;

    PatientService patientService = new PatientServiceImpl();
    ObservableList<Patient> lista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnBack.setFocusTraversable(false);
        btnSearch.setFocusTraversable(false);
        btnNext.setFocusTraversable(false);
        loadTable();

        btnBack.setOnAction(event -> {
            Parent screen;
            try {
                screen = FXMLLoader.load(getClass().getResource("/view/layouts/main.fxml"));
                Scene scene = btnBack.getScene();
                scene.setRoot(screen);
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
        });

        btnNext.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/layouts/searchPatient.fxml"));
            try {
                Parent parent = loader.load();
                ChangeController changeController = loader.getController();
                changeController.initData(tableView.getSelectionModel().getSelectedItem());
                updateAnchorPane.getChildren().setAll(parent);
            } catch (IOException e) {
                logger.log(Level.ERROR, e);
            }
        });

        btnSearch.setOnAction(event -> {
            try {
                if (loadPatient().getId() != null && loadPatient().getId() <= patientService.list().size()
                        && loadPatient().getId() != 0) {
                    lista.clear();
                    lista.add(patientService.retrieve(loadPatient().getId()));
                    if (lista.isEmpty()) {
                        tableView.setItems(lista);
                        clID.setCellValueFactory(new PropertyValueFactory<>("id"));
                        clPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
                        clAge.setCellValueFactory(new PropertyValueFactory<>("age"));
                        clGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                        clWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
                        clVaccineDate.setCellValueFactory(new PropertyValueFactory<>("vaccineDate"));
                    }
                } else
                    loadTable();
            } catch (CustomException e) {
                logger.log(Level.ERROR, "error: " + e);
            }
        });

    }

    private void loadTable() {
        lista.clear();
        try {
            lista.addAll(patientService.list());
            if (!lista.isEmpty()) {
                tableView.setItems(lista);
                clID.setCellValueFactory(new PropertyValueFactory<>("id"));
                clPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
                clAge.setCellValueFactory(new PropertyValueFactory<>("age"));
                clGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                clWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
                clVaccineDate.setCellValueFactory(new PropertyValueFactory<>("vaccineDate"));
            }
        } catch (CustomException e) {
            logger.log(Level.ERROR, e);
        }
    }

    private Patient loadPatient() {
        Patient patient = new Patient();
        if (!txtSearch.getText().isEmpty()) {
            patient.setId(Integer.parseInt(txtSearch.getText()));
        }
        return patient;
    }

}