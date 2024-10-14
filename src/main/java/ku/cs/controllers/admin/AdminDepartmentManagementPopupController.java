package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.controllers.components.FacultyMenuButtonController;
import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdminDepartmentManagementPopupController extends BasePopup<Department> {
    @FXML private Label titleLabel;
    @FXML private TextField departmentNameTextField;
    @FXML private MenuButton facultyMenuButton;
    @FXML private TextField departmentIdTextField;
    @FXML private AnchorPane anchorPane;
    private Admin admin;
    private Department department;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        admin = (Admin) Session.getSession().getLoggedInUser();

        FacultyMenuButtonController.addItems(facultyMenuButton, admin.getFacultyList().getFaculties());

        if (this.hasModel()) {
            department = getModel();
            titleLabel.setText("แก้ไขข้อมูลภาควิชา");
            departmentNameTextField.setText(department.getName());
            departmentIdTextField.setText(department.getId());
            FacultyMenuButtonController.setMenuButton(facultyMenuButton, department.getFaculty());
        } else {
            titleLabel.setText("เพิ่มข้อมูลภาควิชา");
        }
    }

    @FXML
    public void onCancelButton() {
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton() {
        String departmentName = departmentNameTextField.getText();
        String departmentId = departmentIdTextField.getText();
        Faculty faculty = (Faculty) facultyMenuButton.getUserData();

        if (departmentName.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อภาควิชาให้ครบถ้วนและถูกต้อง");
        } else if (departmentId.isEmpty()) {
            AlertService.showError("กรุณากรอกรหัสภาควิชาให้ครบถ้วน");
        } else if (faculty == null) {
            AlertService.showError("กรุณาเลือกคณะ");
        } else {
            if (this.hasModel()) {
                if (!faculty.equals(department.getFaculty())) {
                    department.setName(departmentName);
                    department.setId(departmentId);
                    department.getFaculty().removeDepartment(department);
                    faculty.addDepartment(department);
                } else {
                    department.setName(departmentName);
                    department.setId(departmentId);
                }
            } else {
                department = faculty.addDepartment(departmentName, departmentId);
                admin.getDepartmentList().addDepartment(department);
            }
            DataProvider.getDataProvider().saveUser();
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
