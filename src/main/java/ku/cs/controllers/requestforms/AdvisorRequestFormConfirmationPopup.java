package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class AdvisorRequestFormConfirmationPopup extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private RequestForm requestForm;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        requestForm = getModel().getFormObject();
    }

    @FXML
    public void onAcceptButton() {
        Advisor advisor = (Advisor) Session.getSession().getLoggedInUser();
        RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(), advisor.getFullName(), RequestForm.Status.PENDING_TO_DEPARTMENT, "อาจารย์ที่ปรึกษา");
        requestForm.setStatus(requestFormActionHistory);
        ((Advisor) Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);
        DataProvider.getDataProvider().saveUser();
        AlertService.showInfo("ส่งต่อให้เจ้าหน้าที่ภาควิชาเรียบร้อย");
        changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview-pdf");

    }

    @FXML
    public void onRejectButton() {
        changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
    }

    public void onPreviousPageButton() {
        back();
    }
}
