package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import ku.cs.controllers.components.BasePopup;

public class StudentCreateRequestFormPopupController extends BasePopup<Object> {
    private String formPath = "/ku/cs/views/request-forms/student-late-enroll-request-form-popup-page1.fxml";

    @FXML private MenuButton menuButton;

    @FXML public void onSelectLateEnrollRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-late-enroll-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
    }
    @FXML public void onSelectCoEnrollRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-co-enroll-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงทะเบียนเรียนร่วม");
    }
    @FXML public void onSelectLateDropRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-late-drop-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลงถอนรายวิชาล่าช้า");
    }
    @FXML public void onSelectAbsenceRequestFormMenuItem() {
        formPath = "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml";
        menuButton.setText("ใบคำร้องลาป่วยลากิจ");
    }
    @FXML
    public void onCreateRequestFormButton(){
        changeScene(new Object(), formPath, "form");
    }
}
