package ku.cs.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Faculty;
import ku.cs.models.users.Admin;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class AdminFacultyManagementPopupController extends BasePopup<Faculty> {
    @FXML private Label titleLabel;
    @FXML private TextField facultyNameTextField;
    @FXML private TextField facultyIdTextField;
    @FXML private AnchorPane anchorPane;
    private Faculty faculty;
    private Admin admin;


    public void onPopupOpen() {
        Session.getSession().getTheme().setTheme(anchorPane);
        admin = (Admin) Session.getSession().getLoggedInUser();
        faculty = getModel();

        if(faculty != null) {
            titleLabel.setText("แก้ไขข้อมูลคณะ");
            facultyNameTextField.setText(faculty.getName());
            facultyIdTextField.setText(faculty.getId());
        } else {
            titleLabel.setText("เพิ่มข้อมูลคณะ");
        }
    }

    public void onCancelButton() {
        AlertService.showWarning("ระบบไม่ได้บันทึกข้อมูล");
        this.close();
    }

    @FXML
    public void onConfirmButton() {
        String facultyName = facultyNameTextField.getText();
        String facultyId = facultyIdTextField.getText();

        if(facultyName.isEmpty()|| facultyId.isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            if(faculty == null) {
                faculty = new Faculty(facultyName, facultyId);
                admin.getFacultyList().addFaculty(faculty);
            } else {
                faculty.setName(facultyNameTextField.getText());
                faculty.setId(facultyIdTextField.getText());
            }
            AlertService.showInfo("บันทึกข้อมูลเรียบร้อยแล้ว");
            this.issueEvent("success");
            this.close();
        }
    }
}
