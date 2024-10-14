package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;

public class CoEnrollRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    @FXML private Button endPopupButton;
    @FXML private AnchorPane anchorPane;
    @FXML private TextArea requestFormCauseTextArea;
    private CoEnrollRequestForm coEnrollRequestForm;

    @Override
    public void onPopupOpen() {
        coEnrollRequestForm = (CoEnrollRequestForm) getModel().getFormObject();
        if (coEnrollRequestForm.getStatus() == RequestForm.Status.CREATING) {
            endPopupButton.setText("ถัดไป");
        } else {
            endPopupButton.setText("ยืนยัน");
        }
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        setText();
        boolean readOnly = getModel().isReadOnly();
        if (readOnly) {
            FormAccessibilityController.setUnEditable(anchorPane);
        }
    }

    @FXML
    public void onPreviousPageButton() {
        coEnrollRequestForm.setRequestFormCause(requestFormCauseTextArea.getText());
        back();
    }

    @FXML
    public void onNextPageButton() {
            if(getText()) {
                if (coEnrollRequestForm.getStatus() == RequestForm.Status.CREATING) {
                    Student student = (Student) Session.getSession().getLoggedInUser();
                    if (AlertService.showConfirmation("ท่านต้องการบันทึกข้อมูลใบคำร้องหรือไม่")) {
                        RequestFormNumberProvider.getInstance().applyFormNumber(coEnrollRequestForm);
                        student.getRequestFormList().addRequestForm(coEnrollRequestForm);
                        DataProvider.getDataProvider().saveUser();
                        AlertService.showInfo(coEnrollRequestForm.getStatus().getLabel());
                        this.close();
                    }
                } else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_DEPARTMENT) {
                    if (Session.getSession().getLoggedInUser() instanceof Student) {
                        this.close();
                    } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                        this.close();
                    } else if (Session.getSession().getLoggedInUser() instanceof DepartmentOfficer) {
                        changeScene(getModel(), "/ku/cs/views/officer/department/department-officer-form-action-popup.fxml");
                    } else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer) {
                        changeScene(getModel(), "/ku/cs/views/officer/faculty/faculty-officer-form-action-popup.fxml");
                    }
                } else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_FACULTY) {
                    if (Session.getSession().getLoggedInUser() instanceof Student) {
                        this.close();
                    } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                        this.close();
                    } else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer) {
                        changeScene(getModel(), "/ku/cs/views/officer/faculty/faculty-officer-form-action-popup.fxml");
                    }
                } else if (coEnrollRequestForm.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR) {
                    if (Session.getSession().getLoggedInUser() instanceof Student) {
                        this.close();
                    } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                        changeScene(getModel(), "/ku/cs/views/request-forms/advisor-request-form-confirmation.fxml");
                    }
                } else if (coEnrollRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_ADVISOR || coEnrollRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_DEPARTMENT || coEnrollRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_FACULTY) {
                    if (Session.getSession().getLoggedInUser() instanceof Student) {
                        changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
                    } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                        changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
                    }
                } else if (coEnrollRequestForm.getStatus() == RequestForm.Status.APPROVED_BY_DEPARTMENT || coEnrollRequestForm.getStatus() == RequestForm.Status.APPROVED_BY_FACULTY) {
                    this.close();
                }
            }
    }

    public boolean getText() {
        String requestFormCause = requestFormCauseTextArea.getText();

        if (requestFormCause.isEmpty()) {
            AlertService.showWarning("กรุณากรอกเหตุผลในการขอลงทะเบียนเรียนร่วมให้ครบถ้วน");
            return false;
        } else {
            coEnrollRequestForm.setRequestFormCause(requestFormCause);
            return true;
        }
    }

    public void setText() {
        requestFormCauseTextArea.setText(coEnrollRequestForm.getRequestFormCause());
    }
}
