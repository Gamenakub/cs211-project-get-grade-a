package ku.cs.controllers;

import javafx.fxml.FXML;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class UserLoginPageController {
    @FXML
    public void onRegisterButton() {
        try {
            FXRouter.goTo("student-register");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onLoginButton() {
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onDeveloperInformationButton() {
        try {
            FXRouter.goTo("developer-information");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
