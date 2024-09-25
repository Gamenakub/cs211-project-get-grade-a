package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

import static ku.cs.services.AlertService.showConfirmation;

public class StudentCoEnrollRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    public Button endPopupButton;
    @FXML private AnchorPane anchorPane;
    @FXML private TextArea textAreaRequestFormCause;
    private CoEnrollRequestForm coEnrollRequestForm;
    private boolean readOnly;

    @Override
    public void onPopupOpen() {
        coEnrollRequestForm = (CoEnrollRequestForm) getModel().getFormObject();
        if (coEnrollRequestForm.getStatus() == RequestForm.Status.CREATING){
            endPopupButton.setText("ถัดไป");
        }
        else{
            endPopupButton.setText("ยืนยัน");
        }
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        setText();
        readOnly=getModel().isReadonly();
        if (readOnly){
            SetEditable.setEditable(anchorPane);
        }
    }

    @FXML
    public void onButtonToPage1() {
        getText();
        changeScene(getModel(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onCloseButton() {
        getText();
        if (coEnrollRequestForm.getStatus() == RequestForm.Status.CREATING){
            Student student = (Student) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(coEnrollRequestForm.getRequestFormId(), student.getUsername(), RequestForm.Status.PENDING_TO_ADVISOR, RequestFormActionHistory.ApproverType.STUDENT);
            coEnrollRequestForm.setStatus(requestFormActionHistory);
            if(showConfirmation("ท่านต้องการบันทึกข้อมูลใบคำร้องหรือไม่")){
                student.getRequestFormList().addRequestForm(coEnrollRequestForm);
                DataProvider.getDataProvider().getRequestFormList().addRequestForm(coEnrollRequestForm);
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
        }
        else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_DEPARTMENT){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
            else if (Session.getSession().getLoggedInUser() instanceof Advisor){
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
            else if (Session.getSession().getLoggedInUser() instanceof DepartmentOfficer){
                changeScene(getModel(), "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml", "form");
            }
            else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer){
                changeScene(getModel(), "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml", "form");
            }
            else {
                changeScene(getModel(), "/ku/cs/views/request-forms/advisor-request-form-confirmation.fxml", "form"); // TODO: After implement
            }
        }
        else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_FACULTY){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
            else if (Session.getSession().getLoggedInUser() instanceof Advisor){
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
            else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer){
                changeScene(getModel(), "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml", "form"); // TODO: After implement
            }
        }
        else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }

            else {
                changeScene(getModel(), "/ku/cs/views/request-forms/advisor-request-form-confirmation.fxml", "form"); // TODO: After implement
            }
        }
    }

    public void getText() {
        coEnrollRequestForm.setRequestFormCause(textAreaRequestFormCause.getText());
    }
    public void setText() {
        textAreaRequestFormCause.setText(coEnrollRequestForm.getRequestFormCause());
    }
}
