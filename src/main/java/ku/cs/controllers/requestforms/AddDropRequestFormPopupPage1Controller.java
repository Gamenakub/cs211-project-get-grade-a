package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class AddDropRequestFormPopupPage1Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private Label requestFormModeLabel;
    @FXML private TextField semesterTextField;
    @FXML private TextField academicYearTextField;
    @FXML private TextField campusTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField studentNameTitleTextField;
    @FXML private TextField studentNameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private MenuButton programMenuButton;
    @FXML private TextField studentFacultyTextField;
    @FXML private TextField studentDepartmentTextField;
    @FXML private TextField studentDepartmentIdTextField;
    @FXML private TextField advisorNameTextField;
    @FXML private TextField advisorIdTextField;

    private AddDropRequestForm addDropRequestForm;

    @Override
    public void onPopupOpen() {
        addDropRequestForm = (AddDropRequestForm) getModel().getFormObject();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        setText();
        boolean readOnly = getModel().isReadOnly();
        if (readOnly) {
            FormAccessibilityController.setUnEditable(anchorPane);
        }
    }

    @FXML
    public void onPreviousPageButton() {
        back();
    }

    @FXML
    public void onNextPageButton() {
        if (getText()) {
            changeScene(getModel(), "/ku/cs/views/request-forms/add-drop-request-form-popup-page2.fxml");
        }
    }

    public boolean getText() {
        String semester = semesterTextField.getText().trim();
        String academicYear = academicYearTextField.getText().trim();
        String campus = campusTextField.getText().trim();
        String phoneNumber = phoneTextField.getText().trim();
        String program = programMenuButton.getText().trim();

        if (semester.isEmpty()) {
            AlertService.showError("กรุณากรอกภาคเรียนให้ครบถ้วน");
            return false;
        } else if (academicYear.isEmpty()) {
            AlertService.showError("กรุณากรอกปีการศึกษาให้ครบถ้วน");
            return false;
        } else if (campus.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อวิทยาเขตให้ครบถ้วน");
            return false;
        } else if (phoneNumber.isEmpty()) {
            AlertService.showError("กรุณากรอกหมายเลขโทรศัพท์ให้ครบถ้วน");
            return false;
        } else if (program.isEmpty() || program.equals("เลือกหลักสูตร")) {
            AlertService.showError("กรุณาเลือกหลักสูตรการเรียนให้ครบถ้วน");
            return false;
        }

        if (!isNumeric(academicYear)) {
            AlertService.showError("ปีการศึกษาต้องเป็นตัวเลข");
            return false;
        }

        if (!phoneNumber.matches("\\d{10}")) {
            AlertService.showError("หมายเลขโทรศัพท์มือถือต้องเป็นตัวเลข 10 หลัก");
            return false;
        }

        addDropRequestForm.setSemester(semester);
        addDropRequestForm.setAcademicYear(academicYear);
        addDropRequestForm.setCampus(campus);
        addDropRequestForm.setPhoneNumber(phoneNumber);
        addDropRequestForm.setProgram(program);

        return true;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void setText() {
        if (addDropRequestForm.isAdd()) {
            requestFormModeLabel.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
        } else if (addDropRequestForm.isDrop()) {
            requestFormModeLabel.setText("ใบคำร้องถอนรายวิชาล่าช้า");
        }
        Student student = addDropRequestForm.getStudent();
        Advisor advisor = addDropRequestForm.getAdvisor();
        String studentNameTitle = student.getNameTitle();
        String studentName = student.getName() + " " + student.getSurname();
        String studentId = student.getStudentId();
        String faculty = student.getFaculty().getName();
        String department = student.getDepartment().getName();
        String departmentId = student.getDepartment().getId();
        String advisorName = advisor.getNameTitle() + advisor.getName() + " " + advisor.getSurname();
        String advisorId = advisor.getAdvisorId();

        semesterTextField.setText(addDropRequestForm.getSemester());
        academicYearTextField.setText(addDropRequestForm.getAcademicYear());
        campusTextField.setText(addDropRequestForm.getCampus());
        phoneTextField.setText(addDropRequestForm.getPhoneNumber());
        studentNameTitleTextField.setText(studentNameTitle);
        studentNameTextField.setText(studentName);
        studentIdTextField.setText(studentId);
        programMenuButton.setText(addDropRequestForm.getProgram());
        studentFacultyTextField.setText(faculty);
        studentDepartmentTextField.setText(department);
        studentDepartmentIdTextField.setText(departmentId);
        advisorNameTextField.setText(advisorName);
        advisorIdTextField.setText(advisorId);

        studentNameTitleTextField.setEditable(false);
        studentNameTextField.setEditable(false);
        studentIdTextField.setEditable(false);
        studentFacultyTextField.setEditable(false);
        studentDepartmentTextField.setEditable(false);
        studentDepartmentIdTextField.setEditable(false);
        advisorNameTextField.setEditable(false);
        advisorIdTextField.setEditable(false);
    }

    public void onThaiProgramMenuItem() {
        programMenuButton.setText("ไทย");
    }

    public void onInterProgramMenuItem() {
        programMenuButton.setText("นานาชาติ");
    }
}
