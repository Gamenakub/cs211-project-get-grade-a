package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static ku.cs.services.AlertService.showError;

public class UserLoginPageController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        passwordField.setSkin(new PasswordFieldSkin(passwordField));
    }

    @FXML
    public void onRegisterButton() {
        try {
            FXRouter.goTo("student-register");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void pressEnter(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            onLoginButton();
        }
    }

    @FXML
    public void onLoginButton() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            Session session = Session.getSession();
            try {
                session.setUser(usernameTextField.getText(), passwordField.getText());
                User user = session.getLoggedInUser();
                if (!user.getStatus()) {
                    showError("บัญชีนี้ถูกระงับสิทธิ์การใช้งาน");
                } else if (!user.getActivated()) {
                    AlertService.showInfo("การเข้าใช้งานครั้งแรก จะต้องเปลี่ยนรหัสผ่านก่อนเท่านั้น");
                    PopupComponent<User> popup = null;
                    try {
                        popup = new PopupComponent<>(Session.getSession().getLoggedInUser(), "/ku/cs/views/user-change-password-popup.fxml", anchorPane.getScene().getWindow());
                    } catch (IOException e) {
                        AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
                        System.exit(1);
                    }
                    popup.show();
                    popup.getPopupController().addEventListener(
                            "save", eventData -> {
                                try {
                                    user.setRecentTime(LocalDateTime.now());
                                    FXRouter.goTo("user-personal-information-management");
                                } catch (IOException e) {
                                    AlertService.showError("โปรแกรมนี้มีความผิดพลาด กรุณาติดต่อผู้พัฒนา");
                                    System.exit(1);
                                }
                            }
                    );
                } else {
                    try {
                        user.setRecentTime(LocalDateTime.now());
                        FXRouter.goTo("user-personal-information-management");
                    } catch (IOException e) {
                        AlertService.showError("โปรแกรมนี้มีความผิดพลาด กรุณาติดต่อผู้พัฒนา");
                        System.exit(1);
                    }
                }
            } catch (NoSuchElementException e) {
                AlertService.showError("ไม่พบข้อมูลผู้ใช้ในระบบ" + System.lineSeparator() + "โปรดตรวจสอบชื่อผู้ใช้และรหัสผ่านอีกครั้ง");
            } finally {
                usernameTextField.clear();
                passwordField.clear();
            }
        }
    }

    @FXML
    public void onDeveloperInformationButton() {
        try {
            FXRouter.goTo("developer-information");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onGuidePdfButton() {
        PopupComponent<User> popup = null;
        try {
            popup = new PopupComponent<>(Session.getSession().getLoggedInUser(), "/ku/cs/views/user-guide.fxml", anchorPane.getScene().getWindow());
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
        popup.show();
    }
}
