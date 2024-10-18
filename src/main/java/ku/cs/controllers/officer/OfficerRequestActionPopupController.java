package ku.cs.controllers.officer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.Session;

public class OfficerRequestActionPopupController extends BasePopup<FormDataModel> {
    @FXML private Label topicLabel;
    @FXML private Label numberLabel;
    @FXML private Label studentIdLabel;
    @FXML private AnchorPane anchorPane;
    private RequestForm form;

    @FXML
    public void initialize() {
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        
    }

    @Override
    public void onPopupOpen() {
        form = getModel().getFormObject();
        Student student = form.getStudent();
        setStudentIdLabel(student.getStudentId());
        setTopicLabel(form.getRequestFormTitle());
        setNumberLabel(form.getRequestFormId());
    }

    public void onRequestDetailViewAndOperateButton() {

        switch (form) {
            case CoEnrollRequestForm coEnrollRequestForm ->
                    changeScene(getModel(), "/ku/cs/views/request-forms/co-enroll-request-form-popup-page1.fxml", "form");
            case AddDropRequestForm addDropRequestForm ->
                    changeScene(getModel(), "/ku/cs/views/request-forms/add-drop-request-form-popup-page1.fxml", "form");
            case AbsenceRequestForm absenceRequestForm -> 
                    changeScene(getModel(), "/ku/cs/views/request-forms/absence-request-form-popup-page1.fxml", "form");
            default -> throw new IllegalStateException("Unexpected value: " + form);
        }
    }

    public void setStudentIdLabel(String studentId) {
        studentIdLabel.setText(String.format("รหัสนิสิต %s", studentId));
    }

    public void setNumberLabel(String number) {
        numberLabel.setText(String.format("เลขที่ใบคำร้อง %s", number));
    }

    public void setTopicLabel(String topic) {
        topicLabel.setText(String.format("เรื่อง %s", topic));
    }

    public void onDownloadRequestButton() {
        changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
    }
}
