package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentAbsenceRequestFormPopupPage3Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextArea textAreaRequestFormCause;

    @FXML public void initialize(){
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    };
    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page2.fxml", "form");
    }
    @FXML public void onCloseButton() {
        this.close();
    }
}
