package ku.cs.controllers.requestforms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

import java.io.IOException;
import java.time.LocalDateTime;

public class OfficerRequestFormRefusePopupController extends BasePopup<FormDataModel> {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private RequestForm requestForm;
    @FXML
    private TextArea rejectTextArea;


    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        requestForm = getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    public void onConfirmationPage(ActionEvent actionEvent) {
        try {
            if(rejectTextArea.getText().isEmpty()) throw new IOException();
        }catch (IOException e) {
            AlertService.showError("กรุณากรอกสาเหตุที่ปฏิเสธ");
        }
        if(Session.getSession().getLoggedInUser() instanceof Advisor){
            Advisor advisor = (Advisor) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(),advisor.getUsername(), RequestForm.Status.REJECTED_BY_ADVISOR, RequestFormActionHistory.ApproverType.ADVISOR);
            requestForm.setStatus(requestFormActionHistory);
            requestForm.setTimeStamp(LocalDateTime.now());
            ((Advisor)Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);

        }
        else if (Session.getSession().getLoggedInUser() instanceof DepartmentOfficer){
            DepartmentOfficer departmentOfficer = (DepartmentOfficer) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(),departmentOfficer.getUsername(), RequestForm.Status.REFUSED_BY_DEPARTMENT, RequestFormActionHistory.ApproverType.DEPARTMENT);
            requestForm.setStatus(requestFormActionHistory);
            //requestForm.setTimeStamp(LocalDateTime.now());
            //((DepartmentOfficer)Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);
        }
        else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer){
            FacultyOfficer facultyOfficer = (FacultyOfficer) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(),facultyOfficer.getUsername(), RequestForm.Status.REFUSED_BY_FACULTY, RequestFormActionHistory.ApproverType.FACULTY);
            requestForm.setStatus(requestFormActionHistory);
            // requestForm.setTimeStamp(LocalDateTime.now());
            // ((FacultyOfficer)Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);
        }
        close();
    }

    public void onButtonBackPage(ActionEvent actionEvent) {
        back();
    }
}
