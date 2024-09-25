package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.AbsenceCourseComponentController;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Course;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.services.Session;
import ku.cs.services.SetEditable;

import java.io.IOException;
import java.util.ArrayList;

import static ku.cs.services.AlertService.showWarning;

public class StudentAbsenceRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private Button addCourseButton;
    @FXML private Button deleteCourseButton;
    @FXML private VBox courseListVBox;

    private Course course;
    private AbsenceRequestForm absenceRequestForm;
    private CourseList relatedCourseList;
    private final ArrayList<Course> awaitDeleteCourse = new ArrayList<>();
    private boolean readOnly;
    private boolean isAddCourseButtonDisabled = false;
    private ArrayList<AbsenceCourseComponentController> absenceCourseComponentControllers = new ArrayList<>();

    @Override
    public void onPopupOpen() {
        absenceRequestForm = (AbsenceRequestForm) getModel().getFormObject();
        anchorPane.getStylesheets().add(getClass().getResource("/ku/cs/views/styles/main-style.css").toString());
        Session.getSession().getTheme().setTheme(anchorPane);
        Session.getSession().getTheme().setTheme(courseListVBox);
        relatedCourseList = absenceRequestForm.getCourseList().getCourseByRelatedRequestFormId(absenceRequestForm.getRequestFormId());
        readOnly=getModel().isReadonly();
        System.out.println("readdddd"+readOnly);
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
            showWarning("ท่านสามารถใส่รายวิชาได้สูงสุด 8 รายการ");
            return;
        }
        if(!absenceCourseComponentControllers.isEmpty()) {
            AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
            if(absenceCourseComponentController.confirm()) {
                relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
            } else {
                return;
            }
        }
        Course course = new Course("", "", "", "", "", "", "", absenceRequestForm.getRequestFormId());
        relatedCourseList.getCourses().add(course);
        courseListVBox.getChildren().add(getCourseComponent(course));
        if(relatedCourseList.getCourseListSize() >= 8) {
            isAddCourseButtonDisabled = true;
        }
    }


    public VBox getCourseComponent(Course course) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/request-form-absence-subject.fxml"));
        VBox vBox = null;
        try {
            vBox = fxmlLoader.load();
            AbsenceCourseComponentController absenceCourseComponentController = fxmlLoader.getController();
            absenceCourseComponentController.initialize(relatedCourseList, course);
            VBox finalVBox = vBox;
            absenceCourseComponentController.onUpdate((eventName) -> {
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
            absenceCourseComponentControllers.add(absenceCourseComponentController);
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

    public void getCourses() {
        absenceRequestForm.setCourseList(relatedCourseList);
    }

    public void setCourses(CourseList courseList) {
        this.relatedCourseList = courseList;
    }

    @FXML public void onButtonToPage1() {
        AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
        absenceCourseComponentController.confirm();
        relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
        getCourses();
        changeScene(getModel(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page1.fxml", "form");
    }
    @FXML public void onButtonToPage3() {
        if(!absenceCourseComponentControllers.isEmpty()) {
            AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
            if(absenceCourseComponentController.confirm()) {
                relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
            } else {
                return;
            }
        }
        getCourses();
        if(relatedCourseList.getCourses().isEmpty()) {
            showWarning("กรุณาใส่รายวิชาอย่างน้อย 1 รายการ");
            return;
        }
        changeScene(getModel(), "/ku/cs/views/request-forms/student-absence-request-form-popup-page3.fxml", "form");
    }
}
