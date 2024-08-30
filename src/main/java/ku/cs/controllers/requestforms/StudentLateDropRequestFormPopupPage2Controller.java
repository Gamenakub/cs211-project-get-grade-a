package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentLateDropRequestFormPopupPage2Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldSubjectNo;
    @FXML private MenuButton menuButtonEnrollType;
    @FXML private TextField textFieldSubjectId;
    @FXML private TextField textFieldSubjectLectureSection;
    @FXML private TextField textFieldSubjectPracticeSection;
    @FXML private TextField textFieldSubjectName;
    @FXML private TextField textFieldSubjectLectureCredit;
    @FXML private TextField textFieldSubjectPracticeCredit;

    private String enrollType = "Credit";

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @FXML public void onMenuButtonEnrollTypeCredit() {
        enrollType = "Credit";
        menuButtonEnrollType.setText("Credit");
    }
    @FXML public void onMenuButtonEnrollTypeAudit() {
        enrollType = "Audit";
        menuButtonEnrollType.setText("Audit");
    }
    @FXML
    public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-late-drop-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onButtonToPage3() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-late-drop-request-form-popup-page3.fxml", "form");
    }
}
