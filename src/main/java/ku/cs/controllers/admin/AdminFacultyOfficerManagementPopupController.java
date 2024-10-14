package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.FacultyMenuButtonController;
import ku.cs.controllers.components.PasswordFieldSkin;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdminFacultyOfficerManagementPopupController extends BasePopup<FacultyOfficer> {
    @FXML private Label titleLabel;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField defaultPasswordField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private AnchorPane anchorPane;

    private Admin admin;
    private FacultyOfficer facultyOfficer;

    @Override
    public void onPopupOpen() {
        Session session = Session.getSession();
        session.getThemeProvider().setTheme(anchorPane);
        admin = (Admin) session.getLoggedInUser();

        defaultPasswordField.setSkin(new PasswordFieldSkin(defaultPasswordField));
        FacultyMenuButtonController.addItems(facultyMenuButton, admin.getFacultyList().getFaculties());

        if (this.hasModel()) {
            facultyOfficer = getModel();
            titleLabel.setText("แก้ไขข้อมูลเจ้าหน้าที่คณะ");
            nameTitleTextField.setText(facultyOfficer.getNameTitle());
            nameTextField.setText(facultyOfficer.getName());
            surnameTextField.setText(facultyOfficer.getSurname());
            usernameTextField.setText(facultyOfficer.getUsername());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, facultyOfficer.getFaculty());
            if (facultyOfficer.getActivated()) {
                defaultPasswordField.setDisable(true);
                defaultPasswordField.setPromptText("บัญชีนี้มีการแก้ไขรหัสผ่านแล้ว");
            }
        } else {
            titleLabel.setText("เพิ่มข้อมูลเจ้าหน้าที่คณะ");
        }
    }

    @FXML
    public void onCancelButton() {
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton() {
        String nameTitle = nameTitleTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = defaultPasswordField.getText();
        Faculty faculty = (Faculty) facultyMenuButton.getUserData();

        if (nameTitle.isEmpty()) {
            AlertService.showError("กรุณากรอกคำนำหน้าชื่อให้ครบถ้วนและถูกต้อง");
        } else if (name.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อให้ครบถ้วนและถูกต้อง");
        } else if (surname.isEmpty()) {
            AlertService.showError("กรุณากรอกนามสกุลให้ครบถ้วนและถูกต้อง");
        } else if (username.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อผู้ใช้ให้ครบถ้วนและถูกต้อง");
        } else if (DataProvider.getDataProvider().doesUsernameExist(username) && !(this.hasModel() && facultyOfficer.getUsername().equals(username))) {
            AlertService.showError("ชื่อผู้ใข้นี้ถูกใช้งานแล้ว" + System.lineSeparator() + "กรุณาเปลี่ยนชื่อผู้ใช้");
        } else if (faculty == null) {
            AlertService.showError("กรุณาเลือกคณะ");
        } else {
            if (this.hasModel()) {
                facultyOfficer.setNameTitle(nameTitle);
                facultyOfficer.setName(name);
                facultyOfficer.setSurname(surname);
                facultyOfficer.setUsername(username);
                facultyOfficer.setFaculty(faculty);
                if (!password.isEmpty()) {
                    if (AlertService.showConfirmation("คุณต้องการแก้ไขรหัสผ่านเริ่มต้นของ" + System.lineSeparator() + facultyOfficer.getName() + " " + facultyOfficer.getSurname())) {
                        facultyOfficer.setDefaultPassword(password);
                    }
                }
            } else {
                facultyOfficer = new FacultyOfficer(username, password, nameTitle, name, surname, faculty);
                admin.getFacultyOfficerList().addOfficer(facultyOfficer);
            }
            DataProvider.getDataProvider().saveUser();
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
