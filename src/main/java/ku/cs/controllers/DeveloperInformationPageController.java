package ku.cs.controllers;

import javafx.fxml.FXML;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import java.io.IOException;

public class DeveloperInformationPageController {
    @FXML
    public void onBackToLoginButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            AlertService.showError("โปรแกรมนี้มีความผิดพลาด กรุณาติดต่อผู้พัฒนา");
            System.exit(1);
        }
    }
}
