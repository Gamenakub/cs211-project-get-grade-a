package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class AdvisorFormAbsenceController extends BasePopup<Object> {
    @FXML
    public void onButtonToPage1() {
        changeScene(new Object(), "/ku/cs/views/request-forms/advisor-request-form-absence-popup-page1.fxml", "requestforms");
    }
    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/advisor-request-form-absence-popup-page2.fxml", "requestforms");
    }
    @FXML public void onButtonToPage3() {
        changeScene(new Object(), "/ku/cs/views/request-forms/advisor-request-form-absence-popup-page3.fxml", "requestforms");
    }
    @FXML public void onButtonToPage4() {
        changeScene(new Object(), "/ku/cs/views/request-forms/advisor-request-form-absence-popup-page4.fxml", "requestforms");
    }
    @FXML public void onConfirmButtonClick() {
        close();
    }
}
