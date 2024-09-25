package ku.cs.controllers.officer.department;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class DepartmentOfficerStudentModifyPopupController extends BasePopup<Student> {
    @FXML public TextField studentEmailTextField;
    @FXML public TextField surnameTextField;
    @FXML public TextField nameTextField;
    @FXML public TextField studentIdTextField;
    @FXML private ChoiceBox advisorSelectDropdown;
    @FXML
    private AnchorPane anchorPane;
    private Student student;
    private AdvisorList advisorList;
    @Override
    public void onPopupOpen() {
        studentEmailTextField.clear();
        surnameTextField.clear();
        nameTextField.clear();
        studentIdTextField.clear();
        advisorSelectDropdown.getItems().clear();
        advisorList = ((DepartmentOfficer)Session.getSession().getLoggedInUser()).getAdvisorList();
        student = getModel();
        if(student != null) {
            studentEmailTextField.setText(student.getStudentEmail());
            surnameTextField.setText(student.getSurname());
            nameTextField.setText(student.getName());
            studentIdTextField.setText(student.getStudentId());
            AdvisorList usingAdvisorList = advisorList.findAdvisorByDepartment(student.getDepartment());
            for (int i = 0; i < usingAdvisorList.getAdvisors().size(); i++) {
                //System.out.println("KOAISGIASNG)(EQWG "+usingAdvisorList.getAdvisors().get(i).getDepartment());
                advisorSelectDropdown.getItems().add(usingAdvisorList.getAdvisors().get(i).getName());
            }
            // add "ไม่มี" to the dropdown
            advisorSelectDropdown.getItems().add("ไม่มี");
            // select first advisor
            if (student.getAdvisor() != null) {
                advisorSelectDropdown.getSelectionModel().select(usingAdvisorList.getAdvisors().indexOf(student.getAdvisor()));
            }
        }


    }

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }
    public void onCancel(ActionEvent actionEvent) {
        close();
    }

    public void onConfirm(ActionEvent actionEvent) {
        // ensure all field are filled
        if (studentEmailTextField.getText().trim().isEmpty() || surnameTextField.getText().trim().isEmpty() || nameTextField.getText().trim().isEmpty() || studentIdTextField.getText().trim().isEmpty()) {
            AlertService.showError("กรุณากรอกข้อมูลให้ครบถ้วน");
            return;
        }

        // if studentEmail is not valid
        if (!studentEmailTextField.getText().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            AlertService.showError("กรุณากรอกอีเมลให้ถูกต้อง");
            return;
        }

        student.setStudentEmail(studentEmailTextField.getText().trim());
        student.setSurname(surnameTextField.getText().trim());
        student.setName(nameTextField.getText().trim());
        student.setStudentId(studentIdTextField.getText().trim());
        if (advisorSelectDropdown.getSelectionModel().getSelectedIndex() != -1) {
            int selIndex = advisorSelectDropdown.getSelectionModel().getSelectedIndex();
            Advisor advisor;
            if (selIndex == advisorSelectDropdown.getItems().size() - 1) {
                advisor = null;
            } else {
                advisor = advisorList.getAdvisors().get(selIndex);
            }
            student.setAdvisor(advisor);
        }
        close();
    }
}
