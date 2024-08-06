package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class StudentAbsenceRequestFormPopupController extends BasePopup<Object> {

    @FXML public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page2.fxml", "form");
    }
    @FXML public void onButtonToPage3() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page3.fxml", "form");
    }
    @FXML public void onCloseButton() {
        this.close();
    }
}
