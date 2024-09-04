package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentCoEnrollRequestFormPopupPage1Controller extends BasePopup<Object>{
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldStudentName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldCourse;
    @FXML private TextField textFieldFaculty;
    @FXML private TextField textFieldDepartment;
    @FXML private TextField textFieldAddress;
    @FXML private TextField textFieldSubjectName;
    @FXML private TextField textFieldSubjectId;
    @FXML private TextField textFieldSubjectSection;
    @FXML private TextField textFieldTeacherName;
    @FXML private TextField textFieldTeacherFaculty;
    @FXML private TextField textFieldTeacherDepartment;

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }
    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page2.fxml", "form");
    }
}
