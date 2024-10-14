package ku.cs.controllers.requestforms;

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

public class AdvisorRequestFormOperationPopupController extends BasePopup<FormDataModel> {
    @FXML AnchorPane anchorPane;
    @FXML private Label formIdLabel;
    @FXML private Label formTitleLabel;
    @FXML private Label formStudentIdLabel;

    private RequestForm form;

    @Override
    public void onPopupOpen() {
        form = getModel().getFormObject();
        Student student = form.getStudent();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        setStudentIdLabel(student.getStudentId());
        setTopicLabel(form.getRequestFormTitle());
        setNumberLabel(form.getRequestFormId());
    }

    @FXML
    public void onViewDetailButton() {
        if (form instanceof CoEnrollRequestForm) {
            changeScene(getModel(), "/ku/cs/views/request-forms/co-enroll-request-form-popup-page1.fxml");
        } else if (form instanceof AddDropRequestForm) {
            if (((AddDropRequestForm) form).isAdd()) {
                changeScene(getModel(), "/ku/cs/views/request-forms/add-drop-request-form-popup-page1.fxml");
            } else {
                changeScene(getModel(), "/ku/cs/views/request-forms/add-drop-request-form-popup-page1.fxml");
            }
        } else if (form instanceof AbsenceRequestForm) {
            changeScene(getModel(), "/ku/cs/views/request-forms/absence-request-form-popup-page1.fxml");
        }
    }

    public void setStudentIdLabel(String studentId) {
        formStudentIdLabel.setText(String.format("รหัสนิสิต %s", studentId));
    }

    public void setNumberLabel(String number) {
        formIdLabel.setText(String.format("เลขที่ใบคำร้อง %s", number));
    }

    public void setTopicLabel(String topic) {
        formTitleLabel.setText(String.format("เรื่อง %s", topic));
    }
}
