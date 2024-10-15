package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.OfficerConfirmationPopupDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class OfficerDepartmentRequestFormActionPopupController extends BasePopup<FormDataModel> {
    @FXML public AnchorPane anchorPane;

    @FXML
    public void onPreviousPageButton() {
        back();
    }

    @FXML
    public void onRequestSubmitEndAtDepartmentLeaderButton() {
        OfficerConfirmationPopupDataModel model = new OfficerConfirmationPopupDataModel(getModel().getFormObject(),RequestForm.Status.APPROVED_BY_DEPARTMENT);
        changeScene(model, "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml");
    }

    @FXML
    public void onRequestSubmitSetEndToDeanButton() {
        OfficerConfirmationPopupDataModel model = new OfficerConfirmationPopupDataModel(getModel().getFormObject(),RequestForm.Status.PENDING_TO_FACULTY);
        changeScene(model, "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml");
    }

    @FXML
    public void onRejectRequestButton() {
        DepartmentOfficer officer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
        DataProvider.getDataProvider().saveUser();
        changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
    }

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
    }
}
