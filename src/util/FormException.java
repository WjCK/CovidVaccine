package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class FormException extends Exception {

    private static final long serialVersionUID = 4394296234369178600L;
    final transient Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);

    public FormException(String message, String cause) {
        alert.setTitle("A fault has been found!");
        alert.setContentText(message);
        alert.setHeaderText("The failure occurred due to: " + cause);
    }

    @Override
    public void printStackTrace() {
        alert.show();
    }

}