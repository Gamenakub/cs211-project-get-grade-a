package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

public class CoEnrollRequestFormPopupPage1Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField studentNameTitleTextField;
    @FXML private TextField studentNameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private TextField studentYearTextField;
    @FXML private TextField facultyTextField;
    @FXML private TextField departmentTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseSectionTextField;
    @FXML private TextField semesterTextField;
    @FXML private TextField academicYearTextField;
    @FXML private TextField teacherNameTextField;
    @FXML private TextField teacherFacultyTextField;
    @FXML private TextField teacherDepartmentTextField;

    private CoEnrollRequestForm coEnrollRequestForm;

    @Override
    public void onPopupOpen() {
        coEnrollRequestForm = (CoEnrollRequestForm) getModel().getFormObject();
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
            changeScene(getModel(), "/ku/cs/views/request-forms/co-enroll-request-form-popup-page2.fxml");
        }
    }

    public boolean getText() {
        String studentYear = studentYearTextField.getText();
        String address = addressTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String courseName = courseNameTextField.getText();
        String courseId = courseIdTextField.getText();
        String courseSection = courseSectionTextField.getText();
        String semester = semesterTextField.getText();
        String academicYear = academicYearTextField.getText();
        String teacherName = teacherNameTextField.getText();

        if (studentYear.isEmpty() || isNotNumeric(studentYear)) {
            AlertService.showError("กรุณากรอกชั้นปีของนิสิตให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (address.isEmpty()) {
            AlertService.showError("กรุณากรอกที่อยู่ให้ครบถ้วน");
            return false;
        } else if (phoneNumber.isEmpty() || !isValidPhoneNumber(phoneNumber)) {
            AlertService.showError("กรุณากรอกหมายเลขโทรศัพท์ให้ครบถ้วนและถูกต้อง (10 หลัก)");
            return false;
        } else if (courseName.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่อวิชาให้ครบถ้วน");
            return false;
        } else if (courseId.isEmpty()) {
            AlertService.showError("กรุณากรอกรหัสวิชาให้ครบถ้วน");
            return false;
        } else if (courseSection.isEmpty() || isNotNumeric(courseSection)) {
            AlertService.showError("กรุณากรอกหมู่เรียนให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (semester.isEmpty()) {
            AlertService.showError("กรุณากรอกภาคเรียนให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (academicYear.isEmpty() || isNotNumeric(academicYear)) {
            AlertService.showError("กรุณากรอกปีการศึกษาให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (teacherName.isEmpty()) {
            AlertService.showError("กรุณากรอกชื่ออาจารย์ผู้สอนให้ครบถ้วน");
            return false;
        } else {
            coEnrollRequestForm.setStudentYear(studentYear);
            coEnrollRequestForm.setAddress(address);
            coEnrollRequestForm.setPhoneNumber(phoneNumber);
            coEnrollRequestForm.setCourseName(courseName);
            coEnrollRequestForm.setCourseId(courseId);
            coEnrollRequestForm.setCourseSection(courseSection);
            coEnrollRequestForm.setSemester(semester);
            coEnrollRequestForm.setAcademicYear(academicYear);
            coEnrollRequestForm.setTeacherName(teacherName);
            return true;
        }
    }

    private boolean isNotNumeric(String str) {
        return !str.matches("\\d+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public void setText() {
        studentNameTitleTextField.setText(coEnrollRequestForm.getStudent().getNameTitle());
        studentNameTextField.setText(coEnrollRequestForm.getStudent().getName() + " " + coEnrollRequestForm.getStudent().getSurname());
        studentIdTextField.setText(coEnrollRequestForm.getStudent().getStudentId());
        studentYearTextField.setText(coEnrollRequestForm.getStudentYear());
        facultyTextField.setText(coEnrollRequestForm.getStudent().getFaculty().getName());
        departmentTextField.setText(coEnrollRequestForm.getStudent().getDepartment().getName());
        addressTextField.setText(coEnrollRequestForm.getAddress());
        phoneNumberTextField.setText(coEnrollRequestForm.getPhoneNumber());
        teacherFacultyTextField.setText(coEnrollRequestForm.getStudent().getFaculty().getName());
        teacherDepartmentTextField.setText(coEnrollRequestForm.getStudent().getDepartment().getName());
        courseNameTextField.setText(coEnrollRequestForm.getCourseName());
        courseIdTextField.setText(coEnrollRequestForm.getCourseId());
        courseSectionTextField.setText(coEnrollRequestForm.getCourseSection());
        semesterTextField.setText(coEnrollRequestForm.getSemester());
        academicYearTextField.setText(coEnrollRequestForm.getAcademicYear());
        teacherNameTextField.setText(coEnrollRequestForm.getTeacherName());

        studentNameTitleTextField.setEditable(false);
        studentNameTextField.setEditable(false);
        studentIdTextField.setEditable(false);
        departmentTextField.setEditable(false);
        facultyTextField.setEditable(false);
        teacherDepartmentTextField.setEditable(false);
        teacherFacultyTextField.setEditable(false);
    }
}