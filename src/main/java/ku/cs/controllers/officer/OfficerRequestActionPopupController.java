package ku.cs.controllers.officer;

import javafx.event.ActionEvent;
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

public class OfficerRequestActionPopupController extends BasePopup<FormDataModel> {
    @FXML
    private Label topicLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label nisitIdLabel;
    @FXML
    private AnchorPane anchorPane;
    private RequestForm form;
    private Student student;

    @FXML
    public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
    }

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        form = getModel().getFormObject();
        student = form.getStudent();
        setNisitIdLabel(student.getStudentId());
        setTopicLabel(form.getRequestFormTitle());
        setNumberLabel(form.getRequestFormId());
    }

    public void onRequestDetailViewAndOperate(ActionEvent actionEvent) {
        //changeScene(getModel(), "/ku/cs/views/formCheckingPopup/officer-form-add-late-popup-page1.fxml","form");
        if (form instanceof CoEnrollRequestForm){
            changeScene(getModel(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml","form");
        }
        else if (form instanceof AddDropRequestForm){
            changeScene(getModel(), "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml","form");
        }
        else if (form instanceof AbsenceRequestForm){
            changeScene(getModel(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml","form");
        }
        else{
            //System.out.println(getModel().getFormType());
        }
    }

    public void setNisitIdLabel(String nisitId) {
        nisitIdLabel.setText(String.format("รหัสนิสิต %s", nisitId));
    }
    public void setNumberLabel(String number) {
        numberLabel.setText(String.format("เลขที่ใบคำร้อง %s", number));
    }
    public void setTopicLabel(String topic) {
        topicLabel.setText(String.format("เรื่อง %s", topic));
    }

    public void onDownloadRequest(ActionEvent actionEvent) {
    }
}
