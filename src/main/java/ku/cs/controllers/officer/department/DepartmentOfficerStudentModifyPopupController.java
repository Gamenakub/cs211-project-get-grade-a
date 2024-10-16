package ku.cs.controllers.officer.department;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class DepartmentOfficerStudentModifyPopupController extends BasePopup<Student> {
    @FXML private TextField studentEmailTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private TextField nameTitleTextField;
    @FXML private ChoiceBox<String> advisorSelectChoiceBox;
    @FXML private AnchorPane anchorPane;
    private Student student;
    private AdvisorList advisorList;

    @Override
    public void onPopupOpen() {
        studentEmailTextField.clear();
        surnameTextField.clear();
        nameTextField.clear();
        studentIdTextField.clear();
        advisorSelectChoiceBox.getItems().clear();
        advisorList = ((DepartmentOfficer) Session.getSession().getLoggedInUser()).getAdvisorList();
        student = getModel();
        if (student != null) {
            studentEmailTextField.setText(student.getStudentEmail());
            surnameTextField.setText(student.getSurname());
            nameTextField.setText(student.getName());
            studentIdTextField.setText(student.getStudentId());
            studentIdTextField.setEditable(false);
            studentIdTextField.setOnMouseClicked(event -> {
                AlertService.showInfo("รหัสนิสิตที่ถูกสร้างขึ้นมาแล้ว" + System.lineSeparator() + "ไม่สามารถเปลี่ยนแปลงได้");
            });
            nameTitleTextField.setText(student.getNameTitle());
            AdvisorList usingAdvisorList = advisorList.findAdvisorByDepartment(student.getDepartment());
            for (int i = 0; i < usingAdvisorList.getAdvisors().size(); i++) {
                advisorSelectChoiceBox.getItems().add(usingAdvisorList.getAdvisors().get(i).getName());
            }

            advisorSelectChoiceBox.getItems().add("ไม่มี");

            if (student.getAdvisor() != null) {
                advisorSelectChoiceBox.getSelectionModel().select(usingAdvisorList.getAdvisors().indexOf(student.getAdvisor()));
            } else {
                advisorSelectChoiceBox.getSelectionModel().select(usingAdvisorList.getAdvisors().size());
            }
        }


    }

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        
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

        if (DataProvider.getDataProvider().doesStudentIdExist(studentIdTextField.getText()) && !studentIdTextField.getText().equals(student.getStudentId())) {
            AlertService.showError("รหัสนิสิตนี้มีอยู่ในระบบแล้ว");
            return;
        }

        student.setStudentEmail(studentEmailTextField.getText().trim());
        student.setNameTitle(nameTitleTextField.getText().trim());
        student.setSurname(surnameTextField.getText().trim());
        student.setName(nameTextField.getText().trim());
        student.setStudentId(studentIdTextField.getText().trim());
        if (advisorSelectChoiceBox.getSelectionModel().getSelectedIndex() != -1) {
            int selIndex = advisorSelectChoiceBox.getSelectionModel().getSelectedIndex();
            Advisor advisor;
            if (selIndex == advisorSelectChoiceBox.getItems().size() - 1) {
                advisor = null;
            } else {
                advisor = advisorList.getAdvisors().get(selIndex);
            }
            student.setAdvisor(advisor);
            for (RequestForm requestForm : student.getRequestFormList().getRequestForms()) {
                requestForm.setAdvisor(advisor);
            }
        }
        close();
    }
}
