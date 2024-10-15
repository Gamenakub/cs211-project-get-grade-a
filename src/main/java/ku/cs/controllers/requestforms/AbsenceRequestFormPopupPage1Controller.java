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
import ku.cs.services.AlertService;
import ku.cs.services.Session;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AbsenceRequestFormPopupPage1Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField nameTitleTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField studentIdTextField;
    @FXML private TextField studentYearTextField;
    @FXML private TextField facultyTextField;
    @FXML private TextField departmentTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField facebookTextField;
    @FXML private TextField lineIdTextField;
    @FXML private MenuButton absenceTypeMenuButton;
    @FXML private DatePicker absenceFromDatePicker;
    @FXML private DatePicker absenceUntilDatePicker;

    private AbsenceRequestForm absenceRequestForm;

    @Override
    public void onPopupOpen() {
        absenceRequestForm = (AbsenceRequestForm) getModel().getFormObject();
        boolean readOnly = getModel().isReadOnly();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        convert(absenceFromDatePicker);
        convert(absenceUntilDatePicker);
        setText();
        if (readOnly) {
            FormAccessibilityController.setUnEditable(anchorPane);
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

    @FXML
    public void onAbsenceTypeSickMenuItem() {
        absenceTypeMenuButton.setText("ลาป่วย");
    }

    @FXML
    public void onAbsenceTypePersonalMenuItem() {
        absenceTypeMenuButton.setText("ลากิจ");
    }

    @FXML
    public void onPreviousPopupButton() {
        back();
    }

    @FXML
    public void onNextPageButton() {
        if (getText()) {
            changeScene(getModel(), "/ku/cs/views/request-forms/absence-request-form-popup-page2.fxml");
        }
    }

    public boolean getText() {
        String studentYear = studentYearTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String facebookId = facebookTextField.getText();
        String lineId = lineIdTextField.getText();
        LocalDate absenceFromDate = absenceFromDatePicker.getValue();
        LocalDate absenceUntilDate = absenceUntilDatePicker.getValue();

        if (studentYear.isEmpty() || !isNumeric(studentYear)) {
            AlertService.showError("กรุณากรอกชั้นปีของนิสิตให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (phoneNumber.isEmpty() || !isValidPhoneNumber(phoneNumber)) {
            AlertService.showError("กรุณากรอกหมายเลขโทรศัพท์ให้ครบถ้วนและถูกต้อง (10 หลัก)");
            return false;
        } else if (facebookId.isEmpty()) {
            AlertService.showError("กรุณากรอก Facebook ID ให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (lineId.isEmpty()) {
            AlertService.showError("กรุณากรอก Line ID ให้ครบถ้วนและถูกต้อง");
            return false;
        } else if (absenceFromDate == null) {
            AlertService.showError("กรุณาเลือกวันที่เริ่มต้นการลา");
            return false;
        } else if (absenceUntilDate == null) {
            AlertService.showError("กรุณาเลือกวันที่สิ้นสุดการลา");
            return false;
        } else if (absenceUntilDate.isBefore(absenceFromDate)) {
            AlertService.showError("วันที่สิ้นสุดการลาต้องไม่ก่อนวันที่เริ่มต้นการลา");
            return false;
        } else {
            absenceRequestForm.setStudentYear(studentYear);
            absenceRequestForm.setPhoneNumber(phoneNumber);
            absenceRequestForm.setFacebookID(facebookId);
            absenceRequestForm.setLineID(lineId);
            absenceRequestForm.setAbsenceType(absenceTypeMenuButton.getText());
            absenceRequestForm.setAbsenceDateFrom(absenceFromDate);
            absenceRequestForm.setAbsenceDateUntil(absenceUntilDate);
            return true;
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    public void setText() {
        nameTitleTextField.setText(absenceRequestForm.getStudent().getNameTitle());
        nameTextField.setText(absenceRequestForm.getStudent().getName() + " " + absenceRequestForm.getStudent().getSurname());
        studentIdTextField.setText(absenceRequestForm.getStudent().getStudentId());
        studentYearTextField.setText(absenceRequestForm.getStudentYear());
        facultyTextField.setText(absenceRequestForm.getStudent().getFaculty().getName());
        departmentTextField.setText(absenceRequestForm.getStudent().getDepartment().getName());
        phoneNumberTextField.setText(absenceRequestForm.getPhoneNumber());
        emailTextField.setText(absenceRequestForm.getStudent().getStudentEmail());
        facebookTextField.setText(absenceRequestForm.getFacebookID());
        lineIdTextField.setText(absenceRequestForm.getLineID());
        absenceTypeMenuButton.setText(absenceRequestForm.getAbsenceType());
        absenceFromDatePicker.setValue(absenceRequestForm.getAbsenceDateFrom());
        absenceUntilDatePicker.setValue(absenceRequestForm.getAbsenceDateUntil());
        nameTitleTextField.setEditable(false);
        nameTextField.setEditable(false);
        studentIdTextField.setEditable(false);
        facultyTextField.setEditable(false);
        departmentTextField.setEditable(false);
        emailTextField.setEditable(false);
    }
}