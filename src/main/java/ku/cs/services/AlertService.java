package ku.cs.services;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Optional;

public class AlertService {

    public static void showError(String message) {
        showAlert(Alert.AlertType.ERROR, message);
    }

    public static boolean showConfirmation(String message) {
        return showAlert(Alert.AlertType.CONFIRMATION, message);
    }

    public static void showWarning(String message) {
        showAlert(Alert.AlertType.WARNING, message);
    }

    public static boolean showInfo(String message) {
        return showAlert(Alert.AlertType.INFORMATION, message);
    }

    private static boolean showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        if (alertType == Alert.AlertType.CONFIRMATION) {
            alert = new Alert(alertType, message, ButtonType.CANCEL, ButtonType.OK);
        }
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(AlertService.class.getResourceAsStream("/images/logo.png")));
        alertStage.setResizable(false);
        alert.setTitle("FormXpress");
        alert.setHeaderText(null);

        alert.getDialogPane().getStylesheets().add(AlertService.class.getResource("/ku/cs/views/styles/main-style.css").toString());
        alert.getDialogPane().getStyleClass().add("alert-text-field-label");
        Session.getSession().getThemeProvider().setTheme(alert.getDialogPane());

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.setText("ตกลง");
            okButton.getStyleClass().add("green-button");
        }

        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        if (cancelButton != null) {
            assert okButton != null;
            cancelButton.setText("ยกเลิก");
            cancelButton.getStyleClass().add("deny-button");
        }
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                return true;
            } else if (result.get() == ButtonType.CANCEL) {
                return false;
            }
        }

        return false;
    }
}