package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DepartmentOfficerApproverManagementPageController {
    @FXML
    private Pane navBarPane;

    public void initialize() {
        navBarPane.getChildren().clear();
        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/department-officer-navbar.fxml"));
        try {
            AnchorPane adminNavbar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(adminNavbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
