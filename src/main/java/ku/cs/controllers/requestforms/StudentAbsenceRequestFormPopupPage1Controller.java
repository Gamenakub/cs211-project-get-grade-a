package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentAbsenceRequestFormPopupPage1Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField textFieldNameTitle;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldProgram;
    @FXML private TextField textFieldAcademicYear;
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
    private AbsenceRequestForm absenceRequestForm;
    private boolean readOnly;

    @Override
    public void onPopupOpen() {
        absenceRequestForm = (AbsenceRequestForm) getModel().getFormObject();
        readOnly = getModel().isReadonly();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        convert(datePickerAbsenceFrom);
        convert(datePickerAbsenceUntil);
        setText();
        onMenuButtonAbsenceTypeSick();
        if (readOnly){
            SetEditable.setEditable(anchorPane);
        }
    }

    private void convert(DatePicker datePickerAbsence) {
        datePickerAbsence.setConverter(
                new StringConverter<>() {
                    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    @Override
                    public String toString(LocalDate date) {
                        return (date != null) ? dateFormatter.format(date) : "";
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        return (string != null && !string.isEmpty())
                                ? LocalDate.parse(string, dateFormatter)
                                : null;
                    }
                });
    }

    @FXML public void onMenuButtonAbsenceTypeSick() {
        absenceType = "ลาป่วย";
        menuButtonAbsenceType.setText("ลาป่วย");
    }
    @FXML public void onMenuButtonAbsenceTypePersonal() {
        absenceType = "ลากิจ";
        menuButtonAbsenceType.setText("ลากิจ");
    }

    @FXML public void onButtonToPage2() {
        if(!getModel().isReadonly()) {
            getText();
        }
        changeScene(getModel(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page2.fxml", "form");
    }

    public void getText() {
        absenceRequestForm.setProgram(textFieldProgram.getText());
        absenceRequestForm.setAcademicYear(textFieldAcademicYear.getText());
        absenceRequestForm.setPhoneNumber(textFieldPhoneNumber.getText());
        absenceRequestForm.setFacebookID(textFieldFacebook.getText());
        absenceRequestForm.setLineID(textFieldLineId.getText());
        absenceRequestForm.setAbsenceType(absenceType);
        absenceRequestForm.setAbsenceDateFrom(datePickerAbsenceFrom.getValue());
        absenceRequestForm.setAbsenceDateUntil(datePickerAbsenceUntil.getValue());
    }

    public void setText() {
        textFieldNameTitle.setText(absenceRequestForm.getStudent().getNameTitle());
        textFieldName.setText(absenceRequestForm.getStudent().getName() + " " + absenceRequestForm.getStudent().getSurname());
        textFieldStudentId.setText(absenceRequestForm.getStudent().getStudentId());
        textFieldProgram.setText(absenceRequestForm.getProgram());
        textFieldAcademicYear.setText(absenceRequestForm.getAcademicYear());
        textFieldFaculty.setText(absenceRequestForm.getStudent().getFaculty().getName());
        textFieldDepartment.setText(absenceRequestForm.getStudent().getDepartment().getName());
        textFieldPhoneNumber.setText(absenceRequestForm.getPhoneNumber());
        textFieldEmail.setText(absenceRequestForm.getStudent().getStudentEmail());
        textFieldFacebook.setText(absenceRequestForm.getFacebookID());
        textFieldLineId.setText(absenceRequestForm.getLineID());
        menuButtonAbsenceType.setText(absenceRequestForm.getAbsenceType());
        datePickerAbsenceFrom.setValue(absenceRequestForm.getAbsenceDateFrom());
        datePickerAbsenceUntil.setValue(absenceRequestForm.getAbsenceDateUntil());
    }
}
