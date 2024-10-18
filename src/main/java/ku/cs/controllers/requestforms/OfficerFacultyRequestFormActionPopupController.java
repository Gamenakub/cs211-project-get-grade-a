package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.OfficerConfirmationPopupDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.Session;

public class OfficerFacultyRequestFormActionPopupController extends BasePopup<FormDataModel> {
    public AnchorPane anchorPane;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
    }

    @FXML
    public void onPreviousButton() {
        back();
    }

    @FXML
    public void onRequestSubmitSetEndToDeanButton() {
        OfficerConfirmationPopupDataModel model = new OfficerConfirmationPopupDataModel(getModel().getFormObject(), RequestForm.Status.PENDING_TO_FACULTY);
        changeScene(model, "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml");
    }

    @FXML
    public void onRejectRequestButton() {
        FacultyOfficer officer = (FacultyOfficer) Session.getSession().getLoggedInUser();
        changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
    }
}