package ku.cs.controllers.requestforms;

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

public class AdvisorRequestFormOperationPopupController extends BasePopup<FormDataModel> {
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Label formIdLabel;
    @FXML
    private Label formTitleLabel;
    @FXML
    private Label formStudentIdLabel;

    private RequestForm form;
    private Student student;



    @Override
    public void onPopupOpen() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        super.onPopupOpen();
        form = getModel().getFormObject();
        student = form.getStudent();
        setNisitIdLabel(student.getStudentId());
        setTopicLabel(form.getRequestFormTitle());
        setNumberLabel(form.getRequestFormId());
    }

    @FXML protected void onViewDetailButtonClick(ActionEvent event) {
        if (form instanceof CoEnrollRequestForm){
            changeScene(getModel(), "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml","form");
        }
        else if (form instanceof AddDropRequestForm){
            if (((AddDropRequestForm) form).isAdd()){
                changeScene(getModel(), "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml","form");}
            else{
                changeScene(getModel(), "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml","form");
            }
        }
        else if (form instanceof AbsenceRequestForm){
            changeScene(getModel(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml","form");
        }
    }

    public void setNisitIdLabel(String nisitId) {
        formStudentIdLabel.setText(String.format("รหัสนิสิต %s", nisitId));
    }
    public void setNumberLabel(String number) {
        formIdLabel.setText(String.format("เลขที่ใบคำร้อง %s", number));
    }
    public void setTopicLabel(String topic) {
        formTitleLabel.setText(String.format("เรื่อง %s", topic));
    }
}
