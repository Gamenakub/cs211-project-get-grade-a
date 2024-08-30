package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DepartmentOfficerApproverManagementPageController {
    @FXML private AnchorPane anchorPane;
    @FXML
    public MenuButton selectFirstApproverName;
    @FXML
    public MenuButton selectFirstApproverRole;
    @FXML
    public Button deleteFirstApprover;
    @FXML
    public MenuButton selectSecondApproverName;
    @FXML
    public MenuButton selectSecondApproverRole;
    @FXML
    public Button deleteSecondApprover;
    @FXML
    public MenuButton selectThirdApproverName;
    @FXML
    public MenuButton selectThirdApproverRole;
    @FXML
    public Button deleteThirdApprover;
    @FXML
    private Pane navBarPane;

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddApproverButtonClick(ActionEvent actionEvent) {
    }

    public void onSaveDataButtonClick(ActionEvent actionEvent) {
    }
}
