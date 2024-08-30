package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.services.PopupComponent;

import java.io.IOException;

public class UserPersonalInformationManagementPageController {
    @FXML private Pane navBarPane;
    @FXML private AnchorPane anchorPane;
    @FXML private Label userNameLabel;
    @FXML private Label roleLabel;
    @FXML private Label facultyLabel;
    @FXML private Label departmentLabel;
    @FXML private Label IdLabel;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/admin-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onChangeProfilePictureButton() {
        PopupComponent<Object> popup = new PopupComponent<>(new Object(),"/ku/cs/views/user-change-profile-picture-popup.fxml","user-change-profile-picture-popup",navBarPane.getScene().getWindow());
        popup.show();
    }

    public void onChangePasswordButton() {
        PopupComponent<Object> popup = new PopupComponent<>(new Object(),"/ku/cs/views/user-change-password-popup.fxml","user-change-password-popup",navBarPane.getScene().getWindow());
        popup.show();
    }
}
