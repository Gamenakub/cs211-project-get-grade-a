package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentCoEnrollRequestFormPopupPage2Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextArea textAreaRequestFormCause;

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }
    @FXML
    public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onCloseButton() {
        this.close();
    }
}
