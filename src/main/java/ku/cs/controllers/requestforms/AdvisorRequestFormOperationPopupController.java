package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;

public class AdvisorRequestFormOperationPopupController extends BasePopup<Object> {
    @FXML protected void onViewDetailButtonClick(ActionEvent event) {
        changeScene(new Object(), "/ku/cs/views/request-forms/advisor-request-form-absence-popup-page1.fxml", "requestforms");
    }
}
