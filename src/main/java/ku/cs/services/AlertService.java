package ku.cs.services;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertService {

    public static boolean showError(String message) {
        return showAlert(Alert.AlertType.ERROR, message);
    }

    public static boolean showConfirmation(String message) {
        return showAlert(Alert.AlertType.CONFIRMATION, message);
    }

    public static boolean showWarning(String message) {
        return showAlert(Alert.AlertType.WARNING, message);
    }

    public static boolean showInfo(String message) {
        return showAlert(Alert.AlertType.INFORMATION, message);
    }

    private static boolean showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        if(alertType == Alert.AlertType.CONFIRMATION) {
            alert = new Alert(alertType, message, ButtonType.CANCEL, ButtonType.OK);
        }
        alert.setTitle(alertType.toString());
        alert.setHeaderText(null);

        // Set a style class
        alert.getDialogPane().getStylesheets().add(AlertService.class.getResource("/ku/cs/views/styles/main-style.css").toString());
        alert.getDialogPane().getStyleClass().add("alert-text-field-label");
        Session.getSession().getTheme().setTheme(alert.getDialogPane());
        // Retrieve the OK button
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.getStyleClass().add("green-button"); // Add your custom class for OK button
        }

        // Retrieve the CANCEL button if it exists
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (cancelButton != null) {
            cancelButton.getStyleClass().add("deny-button"); // Add your custom class for CANCEL button
        }
        // Show the alert and wait for the response
        Optional<ButtonType> result = alert.showAndWait();

        // Check which button was clicked
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                return true;  // OK button clicked
            } else if (result.get() == ButtonType.CANCEL) {
                return false; // Cancel button clicked
            }
        }

        return false; // Default return value (if no button was clicked)
    }
}