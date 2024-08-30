package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentAbsenceRequestFormPopupPage2Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldSubjectName;
    @FXML private TextField textFieldSubjectId;
    @FXML private TextField textFieldSubjectSection;
    @FXML private TextField textFieldSubjectTeacher;

    @FXML public void initialize(){
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    };

    @FXML public void onAddSubjectButton() {
        //add new subject
    }
    @FXML public void onDeleteSubjectButton() {
        //delete subject
    }

    @FXML public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onButtonToPage3() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page3.fxml", "form");
    }
}
