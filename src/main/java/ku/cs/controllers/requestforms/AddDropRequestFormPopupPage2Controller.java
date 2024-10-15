package ku.cs.controllers.requestforms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.AddDropCourseComponentController;
import ku.cs.controllers.components.BasePopup;
import ku.cs.models.Course;
import ku.cs.models.FormDataModel;
import ku.cs.models.collections.CourseList;
import ku.cs.models.requestforms.AddDropRequestForm;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.services.AlertService;
import ku.cs.services.DataProvider;
import ku.cs.services.RequestFormNumberProvider;
import ku.cs.services.Session;
import java.io.IOException;
import java.util.ArrayList;

public class AddDropRequestFormPopupPage2Controller extends BasePopup<FormDataModel> {
    @FXML private AnchorPane anchorPane;
    @FXML private VBox courseListVBox;
    @FXML private Label requestFormModeLabel;
    private CourseList relatedCourseList;
    private AddDropRequestForm addDropRequestForm;
    private boolean isAddCourseButtonDisabled = false;
    private final ArrayList<AddDropCourseComponentController> addDropCourseComponentControllers = new ArrayList<>();
    private boolean readOnly;

    @Override
    public void onPopupOpen() {
        addDropRequestForm = (AddDropRequestForm) getModel().getFormObject();
        Session.getSession().getThemeProvider().setTheme(anchorPane);
        Session.getSession().getThemeProvider().setTheme(courseListVBox);
        relatedCourseList = addDropRequestForm.getCourseList().getCourseByRelatedRequestFormId(addDropRequestForm.getRequestFormId());
        setText();
        readOnly = getModel().isReadOnly();

        setCourses(relatedCourseList);
        if (relatedCourseList.getCourseListSize() == 0) {
            onAddCourseButton();
        } else {
            for (Course course1 : relatedCourseList.getCourses()) {
                courseListVBox.getChildren().add(getCourseComponent(course1));
            }
        }
        if (readOnly) {
            FormAccessibilityController.setUnEditable(anchorPane);
        }
    }

    @FXML
    public void onAddCourseButton() {
        if (isAddCourseButtonDisabled) {
            AlertService.showWarning("ท่านสามารถใส่รายวิชาได้สูงสุด 6 รายการ");
            return;
        }
        if (!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            try {
                addDropCourseComponentController.confirm();
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
            } catch (IllegalArgumentException e) {
                AlertService.showWarning(e.getMessage());
                return;
            }
        }
        Course course = new Course("", "", "", "Credit", "", "", "", addDropRequestForm.getRequestFormId());
        relatedCourseList.getCourses().add(course);
        courseListVBox.getChildren().add(getCourseComponent(course));

        if (relatedCourseList.getCourseListSize() >= 6) {
            isAddCourseButtonDisabled = true;
        }
    }

    public VBox getCourseComponent(Course course) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/views/components/request-forms-add-drop-course-component.fxml"));
        VBox vBox = null;
        try {
            vBox = fxmlLoader.load();
            Session.getSession().getThemeProvider().setTheme(vBox);
            AddDropCourseComponentController addDropCourseComponentController = fxmlLoader.getController();
            addDropCourseComponentController.initialize(relatedCourseList, course, readOnly);
            VBox finalVBox = vBox;
            addDropCourseComponentController.onUpdate((eventName) -> {
                if (eventName.equals("delete")) {
                    if (relatedCourseList.getCourseListSize() > 1) {
                        relatedCourseList.getCourses().remove(course);
                        courseListVBox.getChildren().remove(finalVBox);
                        reloadVBox();
                    } else {
                        AlertService.showWarning("ต้องมีรายวิชาอย่างน้อย 1 รายการ");
                    }

                }
            });
            addDropCourseComponentControllers.add(addDropCourseComponentController);
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

    public void setText() {
        if (addDropRequestForm.isAdd()) {
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
    public void onPreviousPageButton() {
        if (!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            try {
                addDropCourseComponentController.confirm();
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
                getCourses();
                back();
            } catch (IllegalArgumentException e) {
                if (AlertService.showConfirmation("ท่านไม่ได้ใส่ข้อมูลรายวิชาให้ครบ\nหากย้อนกลับไปรายวิชาล่าสุดจะไม่ถูกบันทึกไว้")) {
                    relatedCourseList.getCourses().remove(relatedCourseList.getCourseListSize() - 1);
                    getCourses();
                    back();
                }
            }
        }
    }

    @FXML
    public void onNextPageButton() {
        if (!addDropCourseComponentControllers.isEmpty()) {
            AddDropCourseComponentController addDropCourseComponentController = addDropCourseComponentControllers.getLast();
            try {
                addDropCourseComponentController.confirm();
                relatedCourseList.addCourse(addDropCourseComponentController.getCourse());
                getCourses();
            } catch (IllegalArgumentException e) {
                AlertService.showWarning(e.getMessage());
                return;
            }
        }
        if (addDropRequestForm.getStatus() == RequestForm.Status.CREATING) {
            if (relatedCourseList.getCourses().isEmpty()) {
                AlertService.showWarning("กรุณาใส่รายวิชาอย่างน้อย 1 รายการ");
                return;
            }
            Student student = (Student) Session.getSession().getLoggedInUser();
            if (AlertService.showConfirmation("ท่านต้องการบันทึกข้อมูลใบคำร้องหรือไม่")) {
                RequestFormNumberProvider.getInstance().applyFormNumber(addDropRequestForm);
                student.getRequestFormList().addRequestForm(addDropRequestForm);
                DataProvider.getDataProvider().saveUser();
                AlertService.showInfo(addDropRequestForm.getStatus().getLabel());
                this.close();
            }
        } else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_DEPARTMENT) {
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                this.close();
            } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                this.close();
            } else if (Session.getSession().getLoggedInUser() instanceof DepartmentOfficer) {
                changeScene(getModel(), "/ku/cs/views/officer/department/department-officer-form-action-popup.fxml");
            }
        } else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_FACULTY) {
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                this.close();
            } else if (Session.getSession().getLoggedInUser() instanceof FacultyOfficer) {
                changeScene(getModel(), "/ku/cs/views/officer/faculty/faculty-officer-form-action-popup.fxml");
            }
        } else if (addDropRequestForm.getStatus() == RequestForm.Status.PENDING_TO_ADVISOR) {
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                this.close();
            } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                changeScene(getModel(), "/ku/cs/views/request-forms/advisor-request-form-confirmation.fxml");
            }
        } else if (addDropRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_ADVISOR || addDropRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_DEPARTMENT || addDropRequestForm.getStatus() == RequestForm.Status.REJECTED_BY_FACULTY) {
            if (Session.getSession().getLoggedInUser() instanceof Student) {
                changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
            } else if (Session.getSession().getLoggedInUser() instanceof Advisor) {
                changeScene(getModel(), "/ku/cs/views/request-forms/form-reject-popup.fxml");
            }
        } else if (addDropRequestForm.getStatus() == RequestForm.Status.APPROVED_BY_DEPARTMENT || addDropRequestForm.getStatus() == RequestForm.Status.APPROVED_BY_FACULTY) {
            this.close();
        }
    }
}
