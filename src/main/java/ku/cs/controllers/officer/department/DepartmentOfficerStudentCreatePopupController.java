package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
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

import java.util.ArrayList;

public class DepartmentOfficerStudentCreatePopupController extends BasePopup<DepartmentOfficer> {

    @FXML public TextField studentIdTextField;
    @FXML public TextField nameTextField;
    @FXML public TextField surnameTextField;
    @FXML public TextField emailTextField;
    @FXML public ChoiceBox advisorSelectDropdown;
    @FXML
    private AnchorPane anchorPane;
    private ArrayList<Advisor> advisors;

    private DepartmentOfficer departmentOfficer;
    private Advisor selectedAdvisor;
    @FXML
    public void initialize() {
        advisors = new ArrayList<>();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        departmentOfficer = getModel();
        for (Advisor advisor : departmentOfficer.getAdvisorList().findAdvisorByDepartment(departmentOfficer.getDepartment()).getAdvisors()) {
            advisors.add(advisor);
            advisorSelectDropdown.getItems().add(advisor.getName());
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        close();
    }

    public void onConfirm(ActionEvent actionEvent) {

        // ensure all field are filled
        if (emailTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || studentIdTextField.getText().isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
            return;
        }

        // if studentEmail is not valid
        if (!emailTextField.getText().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            AlertService.showError("กรุณากรอกอีเมลให้ถูกต้อง");
            return;
        }




        String studentId = studentIdTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String email = emailTextField.getText();
        DepartmentOfficer departmentOfficer = getModel();
        Department department = departmentOfficer.getDepartment();

        for (Advisor advisor : advisors) {
            System.out.println(
                    advisor.getName() + " " +
                    advisor.getDepartment().getName() + " " +
                    advisor.getDepartment().getFaculty().getName()
            );
            if (advisor.getName().equals(advisorSelectDropdown.getValue())) {
                selectedAdvisor = advisor;
                break;
            }
        }

        Student newStudent = new Student(
                "b" + studentId,
                "นาย",
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
