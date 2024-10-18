package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdminUserManagementPopupController extends BasePopup<User> {
    @FXML private Circle profilePictureCircle;
    @FXML private Label usernameLabel;
    @FXML private Label nameSurnameLabel;
    @FXML private Label statusLabel;
    @FXML private Pane statusPane;
    @FXML private AnchorPane anchorPane;
    @FXML private User user;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        if (this.hasModel()){
            user = getModel();
            ProfilePictureController profilePictureController = new ProfilePictureController();
            profilePictureController.setImageToCircle(profilePictureCircle, user.getProfilePictureFileName());
            usernameLabel.setText(user.getUsername());
            nameSurnameLabel.setText(user.getName() + " " + user.getSurname());
            if (user.getStatus()) {
                statusLabel.setText("ปกติ");
                statusPane.setStyle("-fx-background-color: #009A21;");
            } else {
                statusLabel.setText("ถูกระงับสิทธิ์");
                statusPane.setStyle("-fx-background-color: #DA3131;");
            }
        }
    }

    @FXML
    public void onBanButton() {
        if (!user.getStatus()) {
            AlertService.showInfo("ผู้ใช้ถูกระงับสิทธิ์แล้ว");
        } else {
            if (AlertService.showConfirmation("คุณต้องการระงับสิทธิ์" + System.lineSeparator() + user.getUsername() + System.lineSeparator() + user.getNameTitle() + user.getName() + " " + user.getSurname())) {
                user.setStatus(false);
                DataProvider.getDataProvider().saveUser();
                this.issueEvent("success");
                this.close();
            }
        }
    }

    @FXML
    public void onUnbanButton() {
        if (user.getStatus()) {
            AlertService.showInfo("ผู้ใช้ไม่ได้ถูกระงับสิทธิ์");
        } else {
            if (AlertService.showConfirmation("คุณต้องการคืนสิทธิ์ให้" + System.lineSeparator() + user.getUsername() + System.lineSeparator() + user.getNameTitle() + user.getName() + " " + user.getSurname())) {
                user.setStatus(true);
                DataProvider.getDataProvider().saveUser();
                this.issueEvent("success");
                this.close();
            }
        }
    }
}
