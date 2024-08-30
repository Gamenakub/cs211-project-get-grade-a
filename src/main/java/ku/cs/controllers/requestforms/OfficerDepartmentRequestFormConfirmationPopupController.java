package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;

public class OfficerDepartmentRequestFormConfirmationPopupController extends BasePopup<FormDataModel> {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    public void onRequestSubmitEndAtDepartmentLeader(ActionEvent actionEvent) {
        getModel().setAcceptMessage("สิ้นสุดที่หัวหน้าภาควิชา คำร้องดำเนินการครบถ้วน");
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
    }

    public void onRequestSubmitSetEndToDean(ActionEvent actionEvent) {
        getModel().setAcceptMessage("อนุมัติโดยหัวหน้าภาควิชา คำร้องส่งต่อให้คณบดี");
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
    }

    public void onRequestRefuse(ActionEvent actionEvent) {
        changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-refuse-popup.fxml","form");
    }

    public void onButtonBack(ActionEvent actionEvent) {
        back();
    }
}
