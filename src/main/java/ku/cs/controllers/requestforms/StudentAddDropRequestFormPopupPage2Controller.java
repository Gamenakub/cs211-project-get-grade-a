package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.AddDropCourseComponentController;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Course;
import ku.cs.models.FormDataModel;
import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Student;
import ku.cs.services.DataProvider;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

import java.io.IOException;
import java.util.ArrayList;

import static ku.cs.services.AlertService.showConfirmation;
import static ku.cs.services.AlertService.showWarning;

public class StudentAddDropRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private VBox courseListVBox;
    @FXML private Label requestFormModeLabel;
    @FXML private Button addCourseButton;
    @FXML private Button deleteCourseButton;

    private Course course;
    private CourseList relatedCourseList;
    private final ArrayList<Course> awaitDeleteCourse = new ArrayList<>();
    private AddDropRequestForm addDropRequestForm;
    private boolean isAddCourseButtonDisabled = false;
    private ArrayList<AddDropCourseComponentController> addDropCourseComponentControllers = new ArrayList<>();
    private boolean readOnly;
    @Override
    public void onPopupOpen() {
        addDropRequestForm = (AddDropRequestForm) getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        Session.getSession().getTheme().setTheme(courseListVBox);
        relatedCourseList = addDropRequestForm.getCourseList().getCourseByRelatedRequestFormId(addDropRequestForm.getRequestFormId());
        setText();
        readOnly=getModel().isReadonly();
        if (readOnly){
            SetEditable.setEditable(anchorPane);
        }
        setCourses(relatedCourseList);
        if(relatedCourseList.getCourseListSize() == 0) {
            onAddCourseButton();
        } else {
            for(Course course1 : relatedCourseList.getCourses()){
                courseListVBox.getChildren().add(getCourseComponent(course1));
            }
        }
    }

    public void onAddCourseButton() {
        if (isAddCourseButtonDisabled) {
            showWarning("ท่านสามารถใส่รายวิชาได้สูงสุด 6 รายการ");
            return;
        }
        if(!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            if(addDropCourseComponentController.confirm()) {
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
            } else {
                return;
            }
        }
        Course course = new Course("", "", "", "Credit", "", "", "", addDropRequestForm.getRequestFormId());
        relatedCourseList.getCourses().add(course);
        courseListVBox.getChildren().add(getCourseComponent(course));

        if(relatedCourseList.getCourseListSize() >= 6) {
            isAddCourseButtonDisabled = true;
        }
    }

    public VBox getCourseComponent(Course course) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/request-forms-add-drop-subject.fxml"));
        VBox vBox = null;
        try {
            vBox = fxmlLoader.load();
            Session.getSession().getTheme().setTheme(vBox);
            AddDropCourseComponentController addDropCourseComponentController = fxmlLoader.getController();
            addDropCourseComponentController.initialize(relatedCourseList, course);
            VBox finalVBox = vBox;
            addDropCourseComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    if(relatedCourseList.getCourseListSize() > 1) {
                        relatedCourseList.getCourses().remove(course);
                        awaitDeleteCourse.add(course);
                        courseListVBox.getChildren().remove(finalVBox);
                        reloadVBox();
                    } else {
                        showWarning("ต้องมีรายวิชาอย่างน้อย 1 รายการ");
                    }

                }
            });
            addDropCourseComponentControllers.add(addDropCourseComponentController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vBox;
    }

    public void reloadVBox() {
        System.out.println("clear");
        courseListVBox.getChildren().clear();

        for(Course course1 : relatedCourseList.getCourses()){
            courseListVBox.getChildren().add(getCourseComponent(course1));
        }
    }

    public void setText() {
        if(addDropRequestForm.isAdd()){
            requestFormModeLabel.setText("ใบคำร้องลงทะเบียนเรียนล่าช้า");
        } else {
            requestFormModeLabel.setText("ใบคำร้องถอนรายวิชาล่าช้า");
        }
    }

    public void getCourses() {
        addDropRequestForm.setCourseList(relatedCourseList);
    }

    public void setCourses(CourseList courseList) {
        this.relatedCourseList = courseList;
    }

    @FXML
    public void onButtonToPage1() {
        if(!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            if(addDropCourseComponentController.confirm()) {
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
            } else {
                return;
            }
        }
        getCourses();
        changeScene(getModel(), "/ku/cs/views/request-forms/student-add-drop-request-form-popup-page1.fxml", "form");
    }

    @FXML public void onCloseButton() {
        if(!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            if(addDropCourseComponentController.confirm()) {
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
            } else {
                return;
            }
        }
        getCourses();
        if(relatedCourseList.getCourses().isEmpty()) {
            showWarning("กรุณาใส่รายวิชาอย่างน้อย 1 รายการ");
            return;
        }
        if (addDropRequestForm.getStatus() == RequestForm.Status.CREATING){
            Student student = (Student) Session.getSession().getLoggedInUser();
            RequestFormActionHistory requestFormActionHistory = new RequestFormActionHistory(addDropRequestForm.getRequestFormId(), student.getUsername(), RequestForm.Status.PENDING_TO_ADVISOR, RequestFormActionHistory.ApproverType.STUDENT);
            addDropRequestForm.setStatus(requestFormActionHistory);
            if(showConfirmation("ท่านต้องการบันทึกข้อมูลใบคำร้องหรือไม่")){
                student.getRequestFormList().addRequestForm(addDropRequestForm);
                DataProvider.getDataProvider().getRequestFormList().addRequestForm(addDropRequestForm);
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            }
        }
        else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_DEPARTMENT){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            } else {
                changeScene(getModel(), "/ku/cs/views/request-forms/department-officer-form-confirmation-popup.fxml", "form"); // TODO: After implement
            }
        }
        else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_FACULTY){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            } else {
                changeScene(getModel(), "/ku/cs/views/request-forms/faculty-officer-form-confirmation-popup.fxml", "form"); // TODO: After implement
            }
        }
        else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR){
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/preview-pdf.fxml", "preview pdf");
            } else {
                changeScene(getModel(), "/ku/cs/views/request-forms/advisor-request-form-confirmation.fxml", "form"); // TODO: After implement
            }
        }
    }
}
