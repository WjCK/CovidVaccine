package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableColumn;

public class UpdateController implements Initializable {

    @FXML
    private JFXTreeTableView<?> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configColumn();
    }

    /**
     * config column in table view
     */
    public void configColumn() {
        TreeTableColumn idColumn = new TreeTableColumn<>("id");
        TreeTableColumn patientNameColumn = new TreeTableColumn<>("Patient Name");
        TreeTableColumn genderColumn = new TreeTableColumn<>("Gender");
        TreeTableColumn ageColumn = new TreeTableColumn<>("Age");
        TreeTableColumn weightColumn = new TreeTableColumn<>("Weight");
        TreeTableColumn vaccineColumn = new TreeTableColumn<>("Vaccine Date");

        tableView.getColumns().addAll(idColumn, patientNameColumn, genderColumn, ageColumn, weightColumn,
                vaccineColumn);

        // tableView.setStyle("-fx-background-color: #3A3A3D");

        idColumn.setPrefWidth(50);
        patientNameColumn.setPrefWidth(178);
        genderColumn.setPrefWidth(100);
        ageColumn.setPrefWidth(100);
        weightColumn.setPrefWidth(100);
        vaccineColumn.setPrefWidth(160);

        idColumn.setResizable(false);
        patientNameColumn.setResizable(false);
        genderColumn.setResizable(false);
        ageColumn.setResizable(false);
        weightColumn.setResizable(false);
        vaccineColumn.setResizable(false);

        // idColumn.setStyle("-fx-background-color: #3A3A3D");
    }

}