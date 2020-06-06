package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InfoAlert extends Exception {

    private static final long serialVersionUID = -3000966844107841638L;
    final transient Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.CLOSE);

    public InfoAlert(String message, String cause) {
        alert.setTitle("Success!");
        alert.setContentText(message);
        alert.setHeaderText("Success in operation: " + cause);
    }

    @Override
    public void printStackTrace() {
        alert.show();
    }
}