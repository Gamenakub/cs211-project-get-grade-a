package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.AbsenceCourseComponentController;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Course;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AbsenceRequestForm;
import ku.cs.services.AlertService;
import ku.cs.services.Session;

import java.io.IOException;
import java.util.ArrayList;

public class AbsenceRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private VBox courseListVBox;
    private AbsenceRequestForm absenceRequestForm;
    private CourseList relatedCourseList;
    private boolean readOnly;
    private boolean isAddCourseButtonDisabled = false;
    private final ArrayList<AbsenceCourseComponentController> absenceCourseComponentControllers = new ArrayList<>();

    @Override
    public void onPopupOpen() {
        absenceRequestForm = (AbsenceRequestForm) getModel().getFormObject();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session.getSession().getThemeProvider().setTheme(courseListVBox);
        relatedCourseList = absenceRequestForm.getCourseList().getCourseByRelatedRequestFormId(absenceRequestForm.getRequestFormId());
        readOnly = getModel().isReadOnly();

        if (readOnly) {
            FormAccessibilityController.setUnEditable(anchorPane);
        }
        setCourses(relatedCourseList);
        if (relatedCourseList.getCourseListSize() == 0) {
            onAddCourseButton();
        } else {
            for (Course course1 : relatedCourseList.getCourses()) {
                courseListVBox.getChildren().add(getCourseComponent(course1));
            }
        }
    }

    @FXML
    public void onAddCourseButton() {
        if (isAddCourseButtonDisabled) {
            AlertService.showWarning("ท่านสามารถใส่รายวิชาได้สูงสุด 9 รายวิชา");
            return;
        }
        if (!absenceCourseComponentControllers.isEmpty()) {
            AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
            try {
                absenceCourseComponentController.confirm();
                relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
            } catch (IllegalArgumentException e) {
                AlertService.showWarning(e.getMessage());
                return;
            }
        }
        Course course = new Course("", "", "", "", "", "", "", absenceRequestForm.getRequestFormId());
        relatedCourseList.getCourses().add(course);
        courseListVBox.getChildren().add(getCourseComponent(course));
        if (relatedCourseList.getCourseListSize() >= 9) {
            isAddCourseButtonDisabled = true;
        }
    }


    public VBox getCourseComponent(Course course) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/request-form-absence-course-component.fxml"));
        VBox vBox = null;
        try {
            vBox = fxmlLoader.load();
            AbsenceCourseComponentController absenceCourseComponentController = fxmlLoader.getController();
            absenceCourseComponentController.initialize(course, readOnly);
            VBox finalVBox = vBox;
            absenceCourseComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    if (relatedCourseList.getCourseListSize() > 1) {
                        relatedCourseList.getCourses().remove(course);
                        courseListVBox.getChildren().remove(finalVBox);
                        reloadVBox();
                    } else {
                        AlertService.showWarning("ต้องมีรายวิชาอย่างน้อย 1 รายวิชา");
                    }
                }
            });
            absenceCourseComponentControllers.add(absenceCourseComponentController);
        } catch (IOException e) {
            AlertService.showError("ไฟล์โปรแกรมไม่สมบูรณ์ กรุณาลองใหม่ในภายหลัง");
        }
        return vBox;
    }

    public void reloadVBox() {
        courseListVBox.getChildren().clear();

        for (Course course1 : relatedCourseList.getCourses()) {
            courseListVBox.getChildren().add(getCourseComponent(course1));
        }
    }

    public void setCourses() {
        absenceRequestForm.setCourseList(relatedCourseList);
    }

    public void setCourses(CourseList courseList) {
        this.relatedCourseList = courseList;
    }

    @FXML
    public void onPreviousPageButton() {
        AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
        try {
            absenceCourseComponentController.confirm();
            relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
            setCourses();
            back();
        } catch (IllegalArgumentException e) {
            if (AlertService.showConfirmation("ท่านไม่ได้ใส่ข้อมูลรายวิชาให้ครบ\nหากย้อนกลับไปรายวิชาล่าสุดจะไม่ถูกบันทึกไว้")) {
                relatedCourseList.getCourses().remove(relatedCourseList.getCourseListSize() - 1);
                setCourses();
                back();
            }
        }
    }

    @FXML
    public void onNextPageButton() {
        if (relatedCourseList.getCourses().isEmpty()) {
            AlertService.showWarning("กรุณาใส่รายวิชาอย่างน้อย 1 รายวิชา");
            return;
        }
        if (!absenceCourseComponentControllers.isEmpty()) {
            AbsenceCourseComponentController absenceCourseComponentController = absenceCourseComponentControllers.getLast();
            try {
                absenceCourseComponentController.confirm();
                relatedCourseList.addCourse(absenceCourseComponentController.getCourse());
                setCourses();
                changeScene(getModel(), "/ku/cs/views/request-forms/absence-request-form-popup-page3.fxml");
            } catch (IllegalArgumentException e) {
                AlertService.showWarning(e.getMessage());
            }
        }
    }
}
