package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class DeveloperInformationPageController {
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onBackToLoginButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
