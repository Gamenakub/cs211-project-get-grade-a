package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Course;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

public class StudentCoEnrollRequestFormPopupPage1Controller extends BasePopup<FormDataModel>{
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldStudentNameTitle;
    @FXML private TextField textFieldStudentName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldProgram;
    @FXML private TextField textFieldFaculty;
    @FXML private TextField textFieldDepartment;
    @FXML private TextField textFieldAddress;
    @FXML private TextField textFieldSubjectName;
    @FXML private TextField textFieldSubjectId;
    @FXML private TextField textFieldSubjectSection;
    @FXML private TextField textFieldTeacherName;
    @FXML private TextField textFieldTeacherFaculty;
    @FXML private TextField textFieldTeacherDepartment;

    private CoEnrollRequestForm coEnrollRequestForm;
    private String teacher;
    private Course course;
    private boolean readOnly;

    @Override
    public void onPopupOpen() {
        coEnrollRequestForm = (CoEnrollRequestForm) getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        setText();
        readOnly=getModel().isReadonly();
        if (readOnly){
            SetEditable.setEditable(anchorPane);
        }
        Student student ;
        if (Session.getSession().getLoggedInUser() instanceof Student) {
            student = (Student) Session.getSession().getLoggedInUser();
        }
        teacher = "somshock";
        course = new Course("", "", "", "", "", "", "", coEnrollRequestForm.getRequestFormId());
        coEnrollRequestForm.setTeacher(teacher);
        coEnrollRequestForm.setSubject(course);
    }

    @FXML public void onButtonToPage2() {
        getText();
        changeScene(getModel(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page2.fxml", "form");
    }

    public void getText() { //TODO finish this function
        coEnrollRequestForm.setProgram(textFieldProgram.getText());
        coEnrollRequestForm.setAddress(textFieldAddress.getText());
        Course course = new Course(
                textFieldSubjectName.getText(),
                textFieldSubjectId.getText(),
                "", //
                "Lecture",
                textFieldSubjectSection.getText(),
                textFieldSubjectSection.getText(),
                textFieldTeacherName.getText(),
                coEnrollRequestForm.getRequestFormId()
        );
        coEnrollRequestForm.setSubject(course);
    }

    public void setText() {
        textFieldStudentNameTitle.setText(coEnrollRequestForm.getStudent().getNameTitle());
        textFieldStudentName.setText(coEnrollRequestForm.getStudent().getName() + " " + coEnrollRequestForm.getStudent().getSurname());
        textFieldStudentId.setText(coEnrollRequestForm.getStudent().getStudentId());
        textFieldProgram.setText(coEnrollRequestForm.getProgram());
        textFieldFaculty.setText(coEnrollRequestForm.getStudent().getFaculty().getName());
        textFieldDepartment.setText(coEnrollRequestForm.getStudent().getDepartment().getName());
        textFieldAddress.setText(coEnrollRequestForm.getAddress());
        textFieldTeacherFaculty.setText(coEnrollRequestForm.getStudent().getFaculty().getName());
        textFieldTeacherDepartment.setText(coEnrollRequestForm.getStudent().getDepartment().getName());
        textFieldSubjectName.setText(coEnrollRequestForm.getSubject().getCourseName());
        textFieldSubjectId.setText(coEnrollRequestForm.getSubject().getCourseId());
        textFieldSubjectSection.setText(coEnrollRequestForm.getSubject().getLectureSection());
        textFieldTeacherName.setText(coEnrollRequestForm.getSubject().getProfessorName());
    }
}
