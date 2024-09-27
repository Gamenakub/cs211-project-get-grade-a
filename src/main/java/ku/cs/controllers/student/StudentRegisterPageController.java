package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class StudentRegisterPageController {

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private TextField emailTextField;
    @FXML private AnchorPane anchorPane;
    private StudentList studentList;

    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        newPasswordField.setSkin(new PasswordFieldSkin(newPasswordField));
        confirmNewPasswordField.setSkin(new PasswordFieldSkin(confirmNewPasswordField));
    }

    @FXML
    public void onLoginButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onRegisterButton() {
        String nameTitle = nameTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();
        String id = studentIdTextField.getText();
        String email = emailTextField.getText();

        if(nameTitle.isEmpty() || name.isEmpty() || surname.isEmpty() || id.isEmpty() || email.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            studentList = DataProvider.getDataProvider().getStudentList();
            // TODO studentList is null, tell Tee to manage this
            Student student = studentList.findStudentById(id);
            if(student.checkStudentRegister(name, surname, id, email)) {
                student.setUsername(username);
                student.setStudentEmail(email);
                if(password.equals(confirmNewPassword)) {
                    student.setPassword(password);
                    student.setActivated(true);
                }
            } else {
                throw new RuntimeException("ไม่พบข้อมูลนิสิต");
            }
            try {
                FXRouter.goTo("user-login");
            } catch (IOException e) {
                AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                System.exit(0);
            }
        }
    }
}
