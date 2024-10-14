package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.AlertService;
import ku.cs.services.RequestFormNumberProvider;
import ku.cs.services.Session;

public class CreateRequestFormPopupPageController extends BasePopup<Student> {
    @FXML private AnchorPane anchorPane;
    @FXML private MenuButton menuButton;

    private RequestForm requestForm;
    private Student student;
    private String formPath;

    @Override
    public void onPopupOpen() {
        student = (Student) Session.getSession().getLoggedInUser();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        onSelectAddRequestFormMenuItem();
    }

    @FXML
    public void onSelectAddRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/add-drop-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
    }

    @FXML
    public void onSelectDropRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/add-drop-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องถอนรายวิชาล่าช้า");
    }

    @FXML
    public void onSelectCoEnrollRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/co-enroll-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนร่วม");
    }

    @FXML
    public void onSelectAbsenceRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/absence-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลาป่วยลากิจ");
    }

    @FXML
    public void onCreateRequestFormButton() {
        if (menuButton.getText().equals("ใบคำร้องลงทะเบียนเรียนล่าช้า")) {
            requestForm = RequestFormNumberProvider.getInstance().createAddRequestFormNumber(student);
        } else if (menuButton.getText().equals("ใบคำร้องถอนรายวิชาล่าช้า")) {
            requestForm = RequestFormNumberProvider.getInstance().createDropRequestFormNumber(student);
        } else if (menuButton.getText().equals("ใบคำร้องลงทะเบียนเรียนร่วม")) {
            requestForm = RequestFormNumberProvider.getInstance().createCoEnrollRequestFormNumber(student);
        } else if (menuButton.getText().equals("ใบคำร้องลาป่วยลากิจ")) {
            requestForm = RequestFormNumberProvider.getInstance().createAbsenceRequestFormNumber(student);
        }
        FormDataModel formDataModel = new FormDataModel(false, requestForm);
        if (student.getAdvisor() != null) {
            changeScene(formDataModel, formPath);
        } else {
            AlertService.showWarning("คุณไม่สามารถสร้างใบคำร้องได้ \nเนื่องจากคุณยังไม่มีอาจารย์ที่ปรึกษา");
            this.close();
        }
    }
}