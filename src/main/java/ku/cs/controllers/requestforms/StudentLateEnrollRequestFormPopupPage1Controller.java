package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentLateEnrollRequestFormPopupPage1Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldTerm;
    @FXML private TextField textFieldYear;
    @FXML private TextField textFieldCampus;
    @FXML private TextField textFieldStudentName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldStudentCourse;
    @FXML private TextField textFieldStudentFaculty;
    @FXML private TextField textFieldStudentDepartment;
    @FXML private TextField textFieldStudentDepartmentId;
    @FXML private TextField textFieldAdvisorName;
    @FXML private TextField textFieldAdvisorId;

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-late-enroll-request-form-popup-page2.fxml", "form");
    }
}
