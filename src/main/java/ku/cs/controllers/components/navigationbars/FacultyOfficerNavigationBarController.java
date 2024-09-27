package ku.cs.controllers.components.navigationbars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;

import java.io.IOException;

public class FacultyOfficerNavigationBarController {
    @FXML
    AnchorPane navBarAnchorPane;

    @FXML
    Circle profilePictureCircle;
    @FXML
    MenuButton fontSizeMenuButton;
    @FXML
    MenuItem largeItem;
    @FXML
    MenuItem mediumItem;
    @FXML
    MenuItem smallItem;

    private EventCallback onChangePage = (eventName) -> {};

    public void initialize() {
        ProfilePictureController.setImageToCircle(profilePictureCircle, Session.getSession().getLoggedInUser().getProfilePictureFileName());
    }

    @FXML
    public void onPersonalInformationManagementButton(){
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onRequestManagementButton(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("faculty-officer-request-management");
    }

    public void onRequestManagerSet(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("faculty-officer-request-approver-management");
    }

    @FXML
    public void onThemeButtonClick(){
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeTheme(root);
    }

    @FXML
    public void onLogoutButton() {
        Session.getSession().clearSession();
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onLargeItemClick(){
        fontSizeMenuButton.setText(largeItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    @FXML
    public void onSmallItemClick() {
        fontSizeMenuButton.setText(smallItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    @FXML
    public void onMediumItemClick() {
        fontSizeMenuButton.setText(mediumItem.getText());
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getTheme().changeFontSize(root,fontSizeMenuButton.getText());
    }

    public void onChangePage(EventCallback callback){
        this.onChangePage = callback;
    }
}
