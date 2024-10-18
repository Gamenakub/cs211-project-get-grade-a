package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

import java.io.IOException;

public class RequestFormRejectPopupController extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private RequestForm requestForm;
    @FXML private TextArea rejectTextArea;
    @FXML private Button confirmationButton;

    private boolean rejected;

    @Override
    public void onPopupOpen() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        requestForm = getModel().getFormObject();
        
        Session.getSession().getThemeProvider().setTheme(anchorPane);

        RequestForm.Status status = requestForm.getStatus();
        rejected = false;
        if (status.equals(RequestForm.Status.REJECTED_BY_FACULTY) || status.equals(RequestForm.Status.REJECTED_BY_DEPARTMENT) || status.equals(RequestForm.Status.REJECTED_BY_ADVISOR)) {
            rejectTextArea.setEditable(false);
            rejected = true;
            rejectTextArea.setText(requestForm.getRejectedCause());
        }
        if (rejected) {
            confirmationButton.setText("ยืนยัน");
            confirmationButton.getStyleClass().remove("deny-button");
            confirmationButton.getStyleClass().add("green-button");
        }
    }

    @FXML
    private void onConfirmationPageButton() {
        try {
            if (rejectTextArea.getText().isEmpty()) throw new IOException();
            requestForm.setRejectedCause(rejectTextArea.getText());
        } catch (IOException e) {
            AlertService.showError("กรุณากรอกสาเหตุที่ปฏิเสธ");
            return;
        }
        if (Session.getSession().getLoggedInUser() instanceof Student) {
            changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml");
            return;
        }
        if (Session.getSession().getLoggedInUser() instanceof Advisor advisor) {
            if (rejected) {
                close();
                return;
            }
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(), advisor.getFullName(), RequestForm.Status.REJECTED_BY_ADVISOR, "อาจารย์ที่ปรึกษา");
            requestForm.setStatus(requestFormActionHistory);
            ((Advisor) Session.getSession().getLoggedInUser()).getRequestFormList().removeForm(requestForm);

        } else if (Session.getSession().getLoggedInUser() instanceof DepartmentOfficer departmentOfficer) {
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(), departmentOfficer.getFullName(), RequestForm.Status.REJECTED_BY_DEPARTMENT, "หัวหน้าภาควิชา" + getModel().getFormObject().getStudent().getDepartment().getName());
            requestForm.setStatus(requestFormActionHistory);
            departmentOfficer.getRequestFormList().removeForm(requestForm);
        } else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer facultyOfficer) {
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(requestForm.getRequestFormId(), facultyOfficer.getFullName(), RequestForm.Status.REJECTED_BY_FACULTY, "คณบดีคณะ" + getModel().getFormObject().getStudent().getFaculty().getName());
            requestForm.setStatus(requestFormActionHistory);
            facultyOfficer.getRequestFormList().removeForm(requestForm);
        }
        DataProvider.getDataProvider().saveUser();
        close();
    }

    @FXML
    private void onBackPageButton() {
        back();
    }
}
