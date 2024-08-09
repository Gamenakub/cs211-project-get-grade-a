package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

public class OfficerFacultyRequestFormConfirmationPopupController extends BasePopup<FormDataModel> {
    public void onRequestRefuse(ActionEvent actionEvent) {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-refuse-popup.fxml","form");
    }

    public void onRequestEndAtDean(ActionEvent actionEvent) {
        getModel().setAcceptMessage("สิ้นสุดที่คณะบดี คำร้องดำเนินการครบถ้วน");
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
    }

    public void onButtonBack(ActionEvent actionEvent) {
        back();
    }
}
