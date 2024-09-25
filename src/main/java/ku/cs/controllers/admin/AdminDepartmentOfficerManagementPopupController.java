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
import ku.cs.models.users.Admin;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
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

    public void onPopupOpen() {
        Session.getSession().getTheme().setTheme(anchorPane);
        defaultPasswordField.setSkin(new PasswordFieldSkin(defaultPasswordField));

        admin = (Admin) Session.getSession().getLoggedInUser();
        departmentOfficer = getModel();

        FacultyMenuButtonController.addItems(facultyMenuButton, departmentMenuButton, admin.getFacultyList().getFaculties());

        if(departmentOfficer != null) {
            titleLabel.setText("แก้ไขข้อมูลเจ้าหน้าที่ภาควิชา");
            nameTitleTextField.setText(departmentOfficer.getNameTitle());
            nameTextField.setText(departmentOfficer.getName());
            surnameTextField.setText(departmentOfficer.getSurname());
            usernameTextField.setText(departmentOfficer.getUsername());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, departmentMenuButton, departmentOfficer.getFaculty(), departmentOfficer.getDepartment());
        } else {
            titleLabel.setText("เพิ่มข้อมูลเจ้าหน้าที่ภาควิชา");
        }
    }


    @FXML
    public void onCancelButton(){
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton(){
        String nameTitle = nameTitleTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String username = usernameTextField.getText();
        String password = defaultPasswordField.getText();
        Department department = (Department) departmentMenuButton.getUserData();

        if(nameTitle.isEmpty() || name.isEmpty() || surname.isEmpty() || username.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            if(departmentOfficer == null){
                departmentOfficer = new DepartmentOfficer(username, password, nameTitle, name, surname, department);
                admin.getDepartmentOfficerList().addOfficer(departmentOfficer);
            } else {
                departmentOfficer.setNameTitle(nameTitle);
                departmentOfficer.setName(name);
                departmentOfficer.setSurname(surname);
                departmentOfficer.setUsername(username);
                departmentOfficer.setDepartment(department);

                if(!password.isEmpty()){
                    if(AlertService.showConfirmation("คุณต้องการแก้ไขรหัสผ่านเริ่มต้นของ" + System.lineSeparator() + departmentOfficer.getName() + " " + departmentOfficer.getSurname())) {
                        departmentOfficer.setPassword(password);
                        AlertService.showInfo("แก้ไขรหัสผ่านเรียบร้อยแล้ว");
                    } else {
                        AlertService.showWarning("รหัสผ่านไม่ถูกแก้ไข");
                    }
                }
            }
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
