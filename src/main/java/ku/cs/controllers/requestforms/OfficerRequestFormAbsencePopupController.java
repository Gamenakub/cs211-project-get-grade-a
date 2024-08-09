package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

import java.util.Objects;

public class OfficerRequestFormAbsencePopupController extends BasePopup<FormDataModel> {
    @FXML protected void onButtonToPage1() {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-absence-popup-page1.fxml","form");
    }
    @FXML protected void onButtonToPage2() {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-absence-popup-page2.fxml","form");
    }
    @FXML protected void onButtonToPage3() {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-absence-popup-page3.fxml","form");
    }

    public void onConfirmationPage(ActionEvent actionEvent) {
        if (Objects.equals(getModel().getFormHolder(), "Department")){
            changeScene(getModel(), "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml","form");
        }
        else{
            changeScene(getModel(), "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml","form");
        }
    }
}
