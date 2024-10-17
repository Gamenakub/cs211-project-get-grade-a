package ku.cs.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.FXRouter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class StudentRegisterPageController {
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmNewPasswordField;
    @FXML private TextField emailTextField;

    public void initialize() {
        newPasswordField.setSkin(new PasswordFieldSkin(newPasswordField));
        confirmNewPasswordField.setSkin(new PasswordFieldSkin(confirmNewPasswordField));
    }

    @FXML
    public void onLoginButton() {
        try {
            FXRouter.goTo("user-login");
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาตรวจสอบไฟล์โปรแกรม");
            System.exit(1);
        }
    }

    @FXML
    public void onRegisterButton() {
        String nameTitle = nameTitleTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();
        String studentId = studentIdTextField.getText();
        String email = emailTextField.getText();

        String emailPattern = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$";
        String studentIdPattern = "^\\d{10}$";

        if (nameTitle.isEmpty()) {
            AlertService.showError("กรุณากรอกคำนำหน้าชื่อให้ครบถ้วนและถูกต้อง");
        } else if (name.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อให้ครบถ้วนและถูกต้อง");
        } else if (surname.isEmpty()) {
            AlertService.showError("กรุณากรอกนามสกุลให้ครบถ้วนและถูกต้อง");
        } else if (username.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อผู้ใช้งานให้ครบถ้วนและถูกต้อง");
        } else if (DataProvider.getDataProvider().doesUsernameExist(username)) {
            AlertService.showError("ชื่อผู้ใข้นี้ถูกใช้งานแล้ว" + System.lineSeparator() + "กรุณาเปลี่ยนชื่อผู้ใช้งาน");
        } else if (password.isEmpty()) {
            AlertService.showError("กรุณากรอกรหัสผ่านให้ครบถ้วนและถูกต้อง");
        } else if (confirmNewPassword.isEmpty()) {
            AlertService.showError("กรุณากรอกยืนยันรหัสผ่านให้ครบถ้วนและถูกต้อง");
        } else if (studentId.isEmpty() || !studentId.matches(studentIdPattern)) {
            AlertService.showError("กรุณากรอกรหัสนิสิตให้ครบถ้วนและถูกต้อง (ตัวเลข 10 หลัก)");
        } else if (email.isEmpty() || !email.matches(emailPattern)) {
            AlertService.showError("กรุณากรอกอีเมลให้ครบถ้วนและอยู่ในรูปแบบที่ถูกต้อง");
        } else {
            StudentList studentList = DataProvider.getDataProvider().getStudentList();
            Student student;
            try {
                student = studentList.findStudentById(studentId);
                if (student.checkStudentRegister(nameTitle, name, surname, studentId, email)) {
                    if (student.getActivated()) {
                        AlertService.showError("บัญชีนี้ได้มีการลงทะเบียนแล้ว");
                    } else {
                        student.setUsername(username);
                        student.setStudentEmail(email);
                        if (password.equals(confirmNewPassword)) {
                            student.setStudentPassword(password);
                            student.setActivated(true);
                            student.setRecentTime(LocalDateTime.now());
                            DataProvider.getDataProvider().saveStudent();
                            AlertService.showInfo("การลงทะเบียนใช้งานเสร็จสิ้น" + System.lineSeparator() + "นิสิตสามารถลงชื่อเข้าใช้งานได้ทันที");
                            FXRouter.goTo("user-login");
                        }
                    }
                } else {
                    AlertService.showError("ไม่พบข้อมูลนิสิตที่ตรงกัน");
                }
            } catch (IOException e) {
                AlertService.showError("ระบบมีความผิดพลาด กรุณาตรวจสอบไฟล์โปรแกรม");
                System.exit(1);
            } catch (NoSuchElementException e) {
                AlertService.showError("ไม่พบข้อมูลนิสิตที่ตรงกัน");
            }
        }
    }
}
