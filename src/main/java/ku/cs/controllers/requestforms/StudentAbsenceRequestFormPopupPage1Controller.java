package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;

public class StudentAbsenceRequestFormPopupPage1Controller extends BasePopup<Object> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldCourse;
    @FXML private TextField textFieldYear;
    @FXML private TextField textFieldFaculty;
    @FXML private TextField textFieldDepartment;
    @FXML private TextField textFieldPhoneNumber;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldFacebook;
    @FXML private TextField textFieldLineId;
    @FXML private MenuButton menuButtonAbsenceType;
    @FXML private DatePicker datePickerAbsenceFrom;
    @FXML private DatePicker datePickerAbsenceUntil;

    private String absenceType = "ลาป่วย";

    @FXML public void initialize(){
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        };

    @FXML public void onMenuButtonAbsenceTypeSick() {
        absenceType = "ลาป่วย";
        menuButtonAbsenceType.setText("ลาป่วย");
    }
    @FXML public void onMenuButtonAbsenceTypePersonal() {
        absenceType = "ลากิจ";
        menuButtonAbsenceType.setText("ลากิจ");
    }

    @FXML public void onButtonToPage2() {
        changeScene(new Object(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page2.fxml", "form");
    }
}
