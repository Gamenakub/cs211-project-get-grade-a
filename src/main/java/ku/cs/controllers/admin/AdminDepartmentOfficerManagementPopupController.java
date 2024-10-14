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
import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdminDepartmentOfficerManagementPopupController extends BasePopup<DepartmentOfficer> {
    @FXML private Label titleLabel;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField defaultPasswordField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private MenuButton departmentMenuButton;
    @FXML private AnchorPane anchorPane;
    private Admin admin;
    private DepartmentOfficer departmentOfficer;

    @Override
    public void onPopupOpen() {
        Session session = Session.getSession();
        session.getThemeProvider().setTheme(anchorPane);
        admin = (Admin) session.getLoggedInUser();

        defaultPasswordField.setSkin(new PasswordFieldSkin(defaultPasswordField));
        FacultyMenuButtonController.addItems(facultyMenuButton, departmentMenuButton, admin.getFacultyList().getFaculties());

        if (this.hasModel()) {
            departmentOfficer = getModel();
            titleLabel.setText("แก้ไขข้อมูลเจ้าหน้าที่ภาควิชา");
            nameTitleTextField.setText(departmentOfficer.getNameTitle());
            nameTextField.setText(departmentOfficer.getName());
            surnameTextField.setText(departmentOfficer.getSurname());
            usernameTextField.setText(departmentOfficer.getUsername());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, departmentMenuButton, departmentOfficer.getFaculty(), departmentOfficer.getDepartment());
            if (departmentOfficer.getActivated()) {
                defaultPasswordField.setDisable(true);
                defaultPasswordField.setPromptText("บัญชีนี้มีการแก้ไขรหัสผ่านแล้ว");
            }
        } else {
            titleLabel.setText("เพิ่มข้อมูลเจ้าหน้าที่ภาควิชา");
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
        Department department = (Department) departmentMenuButton.getUserData();

        if (nameTitle.isEmpty()) {
            AlertService.showError("กรุณากรอกคำนำหน้าชื่อให้ครบถ้วนและถูกต้อง");
        } else if (name.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อให้ครบถ้วนและถูกต้อง");
        } else if (surname.isEmpty()) {
            AlertService.showError("กรุณากรอกนามสกุลให้ครบถ้วนและถูกต้อง");
        } else if (username.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อผู้ใช้ให้ครบถ้วนและถูกต้อง");
        } else if (DataProvider.getDataProvider().doesUsernameExist(username) && !(this.hasModel() && departmentOfficer.getUsername().equals(username))) {
            AlertService.showError("ชื่อผู้ใข้นี้ถูกใช้งานแล้ว" + System.lineSeparator() + "กรุณาเปลี่ยนชื่อผู้ใช้");
        } else if (faculty == null) {
            AlertService.showError("กรุณาเลือกคณะ");
        } else if (department == null) {
            AlertService.showError("กรุณาเลือกภาควิชา");
        } else {
            if (this.hasModel()) {
                departmentOfficer.setNameTitle(nameTitle);
                departmentOfficer.setName(name);
                departmentOfficer.setSurname(surname);
                departmentOfficer.setUsername(username);
                departmentOfficer.setDepartment(department);
                if (!password.isEmpty()) {
                    if (AlertService.showConfirmation("คุณต้องการแก้ไขรหัสผ่านเริ่มต้นของ" + System.lineSeparator() + departmentOfficer.getName() + " " + departmentOfficer.getSurname())) {
                        departmentOfficer.setDefaultPassword(password);
                    }
                }
            } else {
                departmentOfficer = new DepartmentOfficer(username, password, nameTitle, name, surname, department);
                admin.getDepartmentOfficerList().addOfficer(departmentOfficer);
            }
            DataProvider.getDataProvider().saveUser();
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
