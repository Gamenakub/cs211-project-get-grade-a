package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdminFacultyManagementPopupController extends BasePopup<Faculty> {
    @FXML private Label titleLabel;
    @FXML private TextField facultyNameTextField;
    @FXML private TextField facultyIdTextField;
    @FXML private AnchorPane anchorPane;

    private Faculty faculty;
    private Admin admin;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        admin = (Admin) Session.getSession().getLoggedInUser();
        if (this.hasModel()) {
            faculty = getModel();
            titleLabel.setText("แก้ไขข้อมูลคณะ");
            facultyNameTextField.setText(faculty.getName());
            facultyIdTextField.setText(faculty.getId());
        } else {
            titleLabel.setText("เพิ่มข้อมูลคณะ");
        }
    }

    @FXML
    public void onCancelButton() {
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton() {
        String facultyName = facultyNameTextField.getText();
        String facultyId = facultyIdTextField.getText();
        if (facultyName.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อคณะให้ครบถ้วนและถูกต้อง");
        } else if (facultyId.isEmpty()) {
            AlertService.showError("กรุณากรอกรหัสคณะให้ครบถ้วนและถูกต้อง");
        } else {
            if (this.hasModel()) {
                faculty.setName(facultyNameTextField.getText());
                faculty.setId(facultyIdTextField.getText());
            } else {
                faculty = new Faculty(facultyName, facultyId);
                admin.getFacultyList().addFaculty(faculty);
            }
            DataProvider.getDataProvider().saveUser();
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
