package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.CoEnrollRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.Session;

import java.time.LocalDate;

import static ku.cs.services.AlertService.showWarning;

public class StudentCreateRequestFormPopupController extends BasePopup<Student> {
    @FXML private AnchorPane anchorPane;
    @FXML private MenuButton menuButton;

    private RequestForm requestForm;
    private Student student;
    private String formPath;

    @Override
    public void onPopupOpen() {
        super.onPopupOpen();
        student = (Student) Session.getSession().getLoggedInUser();
        onSelectAddRequestFormMenuItem();
    }

    @FXML public void initialize() {
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
    }
    @FXML public void onSelectAddRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
    }
    @FXML public void onSelectDropRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงถอนรายวิชาล่าช้า");
    }
    @FXML public void onSelectCoEnrollRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนร่วม");
    }
    @FXML public void onSelectAbsenceRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลาป่วยลากิจ");
    }
    @FXML
    public void onCreateRequestFormButton(){
        if(menuButton.getText().equals("ใบคำร้องลงทะเบียนเรียนล่าช้า")){
            requestForm = new AddDropRequestForm("add", student, student.getAdvisor(), RequestForm.Status.CREATING, new CourseList(), student.getDepartment().getName(), "", "", "");
        } else if (menuButton.getText().equals("ใบคำร้องลงถอนรายวิชาล่าช้า")) {
            requestForm = new AddDropRequestForm("drop", student, student.getAdvisor(), RequestForm.Status.CREATING, new CourseList(), student.getDepartment().getName(), "", "", "");
        } else if (menuButton.getText().equals("ใบคำร้องลงทะเบียนเรียนร่วม")) {
            requestForm = new CoEnrollRequestForm(student, student.getAdvisor(), "", RequestForm.Status.CREATING, "");
        } else if (menuButton.getText().equals("ใบคำร้องลาป่วยลากิจ")) {
            requestForm = new AbsenceRequestForm(student, student.getAdvisor(), RequestForm.Status.CREATING, "", "", "", "", "", "Credit", LocalDate.now(), LocalDate.now().plusDays(1));
        }
        FormDataModel formDataModel = new FormDataModel(false, Session.getSession().getLoggedInUser(),requestForm);
        if(student.getAdvisor()!=null) {
            changeScene(formDataModel, formPath, "form");
        } else {
            showWarning("คุณไม่สามารถสร้างใบคำร้องได้เ \nนื่องจากคุณยังไม่มีอาจารย์ที่ปรึกษา");
            this.close();
        }
    }
}