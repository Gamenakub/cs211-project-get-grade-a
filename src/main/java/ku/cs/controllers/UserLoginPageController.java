package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.FXRouter;
import ku.cs.services.Session;
import ku.cs.services.popup.PopupComponent;

import java.io.IOException;

public class UserLoginPageController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private AnchorPane anchorPane;

    public void initialize() {
        passwordField.setSkin(new PasswordFieldSkin(passwordField));
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML
    public void onRegisterButton() {
        try {
            FXRouter.goTo("student-register");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void pressEnter(KeyEvent e) throws IOException {
        if(e.getCode().toString().equals("ENTER"))
        {
            onLoginButton();
        }
    }


    @FXML
    public void onLoginButton() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if(username.isEmpty() || password.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            Session session = Session.getSession();
            try {
                session.setUser(usernameTextField.getText(),passwordField.getText());
                User user = session.getLoggedInUser();
                if(!user.getStatus()) {
                    AlertService.showError("บัญชีนี้ถูกระงับสิทธิ์การใช้งาน");
                } else if(!user.getActivated()){
                    AlertService.showInfo("การเข้าใช้งานครั้งแรก จะต้องเปลี่ยนรหัสผ่านก่อนเท่านั้น");
                    PopupComponent<User> popup = new PopupComponent<>(Session.getSession().getLoggedInUser(),"/ku/cs/views/user-change-password-popup.fxml","user-change-password-popup", anchorPane.getScene().getWindow());
                    popup.show();
                    popup.getPopupController().addEventListener(
                            "save", new EventCallback() {
                                @Override
                                public void onEvent(Object eventData) {
                                    try {
                                        FXRouter.goTo("user-personal-information-management");
                                    } catch (IOException e) {
                                        AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                                        System.exit(0);
                                    }
                                }
                            }
                    );
                } else {
                    try {
                        FXRouter.goTo("user-personal-information-management");
                    } catch (IOException e) {
                        AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
            throw new RuntimeException(e);
        }
    }
}
