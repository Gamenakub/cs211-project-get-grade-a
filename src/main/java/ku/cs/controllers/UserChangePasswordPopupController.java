package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.users.User;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class UserChangePasswordPopupController extends BasePopup<User> {
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private AnchorPane anchorPane;
    private User user;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        user = getModel();
        oldPasswordField.setSkin(new PasswordFieldSkin(oldPasswordField));
        newPasswordField.setSkin(new PasswordFieldSkin(newPasswordField));
        confirmNewPasswordField.setSkin(new PasswordFieldSkin(confirmNewPasswordField));
    }

    @FXML
    public void onConfirmButton() {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();
        if (newPassword.equals(confirmNewPassword)) {
            try {
                user.changePassword(oldPassword, newPassword);
                user.setActivated(true);
                AlertService.showInfo("เปลี่ยนรหัสผ่านเรียบร้อยแล้ว");
                DataProvider.getDataProvider().saveUser();
                this.issueEvent("success");
                this.close();
            } catch (Exception e) {
                AlertService.showError("รหัสผ่านเก่าไม่ถูกต้อง");
            }
        } else {
            AlertService.showError("รหัสผ่านใหม่ไม่ตรงกัน");
        }
        oldPasswordField.clear();
        newPasswordField.clear();
        confirmNewPasswordField.clear();
    }
}
