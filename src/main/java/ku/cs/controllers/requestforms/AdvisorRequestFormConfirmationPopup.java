package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.services.Session;

import java.time.LocalDateTime;

public class AdvisorRequestFormConfirmationPopup extends BasePopup<FormDataModel> {


        @FXML
        private AnchorPane anchorPane;
    @FXML
    private RequestForm requestForm;


    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        requestForm = getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

        @FXML
        public void onAcceptButtonClick(){
            Advisor advisor = (Advisor) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(), advisor.getUsername(), RequestForm.Status.PENDING_TO_DEPARTMENT, RequestFormActionHistory.ApproverType.ADVISOR);
            requestForm.setStatus(requestFormActionHistory);
            requestForm.setTimeStamp(LocalDateTime.now());
            getModel().setAcceptMessage("ส่งต่อให้เจ้าหน้าที่ภาควิชาเรียบร้อย");
            ((Advisor)Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-accept-popup.fxml","form");
        }

    
        @FXML
        public void onDenyButtonClick(){
            changeScene(getModel(), "/ku/cs/views/request-forms/officer-form-refuse-popup.fxml","form");
        }


    public void onButtonToPage3(ActionEvent actionEvent) {
        back();
    }
}
