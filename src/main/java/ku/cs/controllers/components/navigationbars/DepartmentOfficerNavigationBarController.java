package ku.cs.controllers.components.navigationbars;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.components.ProfilePictureController;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;

import java.io.IOException;

public class DepartmentOfficerNavigationBarController {
    @FXML private AnchorPane navBarAnchorPane;
    @FXML private Circle profilePictureCircle;
    @FXML private MenuButton fontSizeMenuButton;
    @FXML private MenuButton fontStyleMenuButton;
    @FXML private ImageView logoutIcon;
    @FXML private ImageView themeIcon;

    IconController iconThemeController;

    public void initialize() {
        ProfilePictureController profilePictureController = new ProfilePictureController();
        profilePictureController.setImageToCircle(profilePictureCircle, Session.getSession().getLoggedInUser().getProfilePictureFileName());
        fontSizeMenuButton.setText(Session.getSession().getThemeProvider().getFontSize());
        iconThemeController = new IconController(logoutIcon, themeIcon, fontSizeMenuButton, fontStyleMenuButton);
        iconThemeController.updateNavBar();
    }

    @FXML
    public void onPersonalInformationManagementButton() {
        try {
            FXRouter.goTo("user-personal-information-management");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }


    public void onRequestManagementButton() {
        try {
            FXRouter.goTo("department-officer-request-management");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    public void onRequestManagerSet() {
        try {
            FXRouter.goTo("department-officer-request-approver-management");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }

    }

    public void onStudentSet() {
        try {
            FXRouter.goTo("department-officer-student-management");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onThemeButtonClick() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeTheme(root);
        iconThemeController.updateNavBar();
    }

    @FXML
    public void onLogoutButton() {
        Session.getSession().clearSession();
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onLargeMenuItem() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontSize(root, "Large");
    }

    @FXML
    public void onSmallMenuItem() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontSize(root, "Small");
    }

    @FXML
    public void onMediumMenuItem() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontSize(root, "Medium");
    }

    @FXML
    public void onSarabunMenuItem() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontStyle(root, "Sarabun");
    }

    @FXML
    public void onMaliGradeMenuItemClick() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontStyle(root, "Maligrade");
    }

    @FXML
    public void onAngsanaNewMenuItem() {
        Scene scene = navBarAnchorPane.getScene();
        Parent root = scene.getRoot();
        Session.getSession().getThemeProvider().changeFontStyle(root, "Angsananew");
    }

}
