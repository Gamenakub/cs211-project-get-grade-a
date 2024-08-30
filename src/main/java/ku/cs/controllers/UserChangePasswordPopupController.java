package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class UserChangePasswordPopupController extends BasePopup<Object> {
    @FXML private TextField oldPassword;
    @FXML private TextField newPassword;
    @FXML private TextField confirmPassword;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onConfirmButton() {
        this.close();
    }
}
