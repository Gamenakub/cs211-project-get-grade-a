package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.FormDataModel;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

public class StudentAddDropRequestFormPopupPage1Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private Label labelRequestFormMode;
    @FXML private TextField textFieldTerm;
    @FXML private TextField textFieldAcademicYear;
    @FXML private TextField textFieldCampus;
    @FXML private TextField textFieldStudentNameTitle;
    @FXML private TextField textFieldStudentName;
    @FXML private TextField textFieldStudentId;
    @FXML private TextField textFieldStudentProgram;
    @FXML private TextField textFieldStudentFaculty;
    @FXML private TextField textFieldStudentDepartment;
    @FXML private TextField textFieldStudentDepartmentId;
    @FXML private TextField textFieldAdvisorName;
    @FXML private TextField textFieldAdvisorId;
    private boolean readOnly;
    private AddDropRequestForm addDropRequestForm;
    @Override
    public void onPopupOpen() {
        addDropRequestForm = (AddDropRequestForm) getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        setText();
        readOnly=getModel().isReadonly();
        if (readOnly){
            SetEditable.setEditable(anchorPane);
        }
    }

    @FXML public void onButtonToPage2() {
        if(!getModel().isReadonly()) {
            getText();
        }
        changeScene(getModel(), "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page2.fxml", "form");
    }

    public void getText() {
        addDropRequestForm.setTerm(textFieldTerm.getText());
        addDropRequestForm.setAcademicYear(textFieldAcademicYear.getText());
        addDropRequestForm.setCampus(textFieldCampus.getText());
        addDropRequestForm.setProgram(textFieldStudentProgram.getText());
    }
    public void setText() {
        if(addDropRequestForm.isAdd()) {
            labelRequestFormMode.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
        } else {
            labelRequestFormMode.setText("ใบคำร้องถอนรายวิชาล่าช้า");
        }
        String studentNameTitle = addDropRequestForm.getStudent().getNameTitle();
        String studentName = addDropRequestForm.getStudent().getName() + " " + addDropRequestForm.getStudent().getSurname();
        String studentId = addDropRequestForm.getStudent().getStudentId();
        String faculty = addDropRequestForm.getStudent().getFaculty().getName();
        String department = addDropRequestForm.getStudent().getDepartment().getName();
        String departmentId = addDropRequestForm.getStudent().getDepartment().getId();
        String advisorName = addDropRequestForm.getStudent().getAdvisor().getName() + " " + addDropRequestForm.getStudent().getAdvisor().getSurname();
        String advisorId = addDropRequestForm.getStudent().getAdvisor().getAdvisorId();

        textFieldTerm.setText(addDropRequestForm.getTerm());
        textFieldAcademicYear.setText(addDropRequestForm.getAcademicYear());
        textFieldCampus.setText(addDropRequestForm.getCampus());
        textFieldStudentNameTitle.setText(studentNameTitle);
        textFieldStudentName.setText(studentName);
        textFieldStudentId.setText(studentId);
        textFieldStudentProgram.setText(addDropRequestForm.getProgram());
        textFieldStudentFaculty.setText(faculty);
        textFieldStudentDepartment.setText(department);
        textFieldStudentDepartmentId.setText(departmentId);
        textFieldAdvisorName.setText(advisorName);
        textFieldAdvisorId.setText(advisorId);
    }
}
