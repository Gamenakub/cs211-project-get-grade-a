package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class StudentCoEnrollRequestFormPopupController extends BasePopup<Object>{
    @FXML
    public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page2.fxml", "form");
    }
    @FXML public void onCloseButton() {
        this.close();
    }
}
