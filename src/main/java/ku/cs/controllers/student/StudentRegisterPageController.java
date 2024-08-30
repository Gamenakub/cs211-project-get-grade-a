package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class StudentRegisterPageController {

    @FXML TextField nameTextField;
    @FXML TextField surnameTextField;
    @FXML TextField usernameTextField;
    @FXML TextField passwordTextField;
    @FXML TextField confirmPasswordTextField;
    @FXML TextField emailTextField;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onLoginButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onRegisterButton() {
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
