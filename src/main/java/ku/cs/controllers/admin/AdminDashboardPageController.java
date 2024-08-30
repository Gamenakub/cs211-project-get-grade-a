package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminDashboardPageController {

    @FXML private Pane navBarPane;

    @FXML private AnchorPane anchorPane;


    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        navBarPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());

        navBarPane.getChildren().clear();

        FXMLLoader navBarFxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/admin-navbar.fxml"));
        try {
            AnchorPane advisorNavBar = navBarFxmlLoader.load();
            navBarPane.getChildren().add(advisorNavBar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
