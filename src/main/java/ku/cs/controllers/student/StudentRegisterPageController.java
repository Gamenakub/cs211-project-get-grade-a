package ku.cs.controllers.student;

import javafx.fxml.FXML;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class StudentRegisterPageController {
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
