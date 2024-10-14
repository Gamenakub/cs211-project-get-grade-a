package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Department;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

import java.util.ArrayList;

public class DepartmentOfficerStudentCreatePopupController extends BasePopup<DepartmentOfficer> {
    @FXML private TextField studentIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField studentEmailTextField;
    @FXML private ChoiceBox<String> advisorSelectChoiceBox;
    @FXML private TextField nameTitleTextField;
    @FXML private AnchorPane anchorPane;
    private ArrayList<Advisor> advisors;
    private Advisor selectedAdvisor;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        advisors = new ArrayList<>();
        
    }

    @Override
    public void onPopupOpen() {
        DepartmentOfficer departmentOfficer = getModel();
        for (Advisor advisor : departmentOfficer.getAdvisorList().findAdvisorByDepartment(departmentOfficer.getDepartment()).getAdvisors()) {
            advisors.add(advisor);
            advisorSelectChoiceBox.getItems().add(advisor.getName());
        }

        advisorSelectChoiceBox.getItems().addFirst("ไม่มี");
        advisorSelectChoiceBox.getSelectionModel().select(0);
    }

    public void onCancelButton() {
        close();
    }

    public void onConfirmButton() {
        String emailPattern = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$";
        String studentIdPattern = "^\\d{10}$";

        if (studentEmailTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || studentIdTextField.getText().isEmpty() || nameTitleTextField.getText().isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
            return;
        }

        if (!studentEmailTextField.getText().matches(emailPattern)) {
            AlertService.showError("กรุณากรอกอีเมลให้ถูกต้อง");
            return;
        }

        if (!studentIdTextField.getText().matches(studentIdPattern)) {
            AlertService.showError("กรุณากรอกรหัสนิสิตให้ครบถ้วนและถูกต้อง (คัวเลข 10 หลัก)");
            return;
        }

        if (DataProvider.getDataProvider().doesStudentIdExist(studentIdTextField.getText())) {
            AlertService.showError("รหัสนิสิตนี้มีอยู่ในระบบแล้ว");
            return;
        }

        String studentId = studentIdTextField.getText();
        String nameTitle = nameTitleTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = studentEmailTextField.getText();
        DepartmentOfficer departmentOfficer = getModel();
        Department department = departmentOfficer.getDepartment();

        for (Advisor advisor : advisors) {
            if (advisor.getName().equals(advisorSelectChoiceBox.getValue())) {
                selectedAdvisor = advisor;
                break;
            }
        }

        Student newStudent = new Student(
                null,
                nameTitle,
                name,
                surname,
                studentId,
                email,
                selectedAdvisor,
                department
        );
        issueEvent("save", newStudent);
        close();
    }
}
