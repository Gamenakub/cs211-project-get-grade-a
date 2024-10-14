package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;

public class AddDropCourseComponentController {
    @FXML private TextField courseNoTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseYearTextField;
    @FXML private TextField courseLectureSectionTextField;
    @FXML private TextField coursePracticeSectionTextField;
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseLectureCreditTextField;
    @FXML private TextField coursePracticeCreditTextField;
    @FXML private MenuButton enrollTypeMenuButton;
    @FXML private Button deleteButton;

    private Course course;
    private CourseList courseList;
    private EventCallback onUpdateCallback;

    public void onUpdate(EventCallback onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initialize(CourseList courseList, Course course, boolean readOnly) {
        this.course = course;
        this.courseList = courseList;
        setTextField();
        if (readOnly) {
            deleteButton.setVisible(false);
            return;
        }
        deleteButton.setOnAction(event -> onUpdateCallback.onEvent("delete"));
    }

    public void confirm() {
        String courseId = courseIdTextField.getText();
        String courseYear = courseYearTextField.getText();
        String courseLectureSection = courseLectureSectionTextField.getText();
        String coursePracticeSection = coursePracticeSectionTextField.getText();
        String courseName = courseNameTextField.getText();
        String courseLectureCredit = courseLectureCreditTextField.getText();
        String coursePracticeCredit = coursePracticeCreditTextField.getText();
        String enrollType = enrollTypeMenuButton.getText();

        if (courseId.isEmpty() || courseYear.isEmpty() || courseLectureSection.isEmpty() || courseName.isEmpty() || courseLectureCredit.isEmpty() || enrollType.isEmpty()) {
            throw new IllegalArgumentException("กรุณาใส่ข้อมูลรายวิชาให้ครบถ้วน");
        }

        if (isNotNumeric(courseYear)) {
            throw new IllegalArgumentException("ปีรายวิชาต้องเป็นตัวเลขจำนวนเต็ม");
        }

        if (isNotNumeric(courseLectureSection)) {
            throw new IllegalArgumentException("หมู่บรรยายต้องเป็นตัวเลขจำนวนเต็ม");
        }

        if (isNotNumeric(courseLectureCredit)) {
            throw new IllegalArgumentException("หน่วยกิตบรรยายต้องเป็นตัวเลข");
        }

        if (!enrollType.equals("Credit") && !enrollType.equals("Audit")) {
            throw new IllegalArgumentException("ประเภทการลงทะเบียนต้องเป็น Credit หรือ Audit");
        }

        course.setCourseId(courseId);
        course.setCourseYear(courseYear);
        course.setLectureSection(courseLectureSection);
        course.setPracticeSection(coursePracticeSection);
        course.setCourseName(courseName);
        course.setLectureCredit(courseLectureCredit);
        course.setPracticeCredit(coursePracticeCredit);
        course.setCourseType(enrollType);
    }

    private boolean isNotNumeric(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public void setTextField() {
        courseNoTextField.setText(courseList.getCourseListSize() + "");
        courseIdTextField.setText(course.getCourseId());
        courseYearTextField.setText(course.getCourseYear());
        courseLectureSectionTextField.setText(course.getLectureSection());
        coursePracticeSectionTextField.setText(course.getPracticeSection());
        courseNameTextField.setText(course.getCourseName());
        courseLectureCreditTextField.setText(course.getLectureCredit());
        coursePracticeCreditTextField.setText(course.getPracticeCredit());
        enrollTypeMenuButton.setText(course.getCourseType());
    }

    public Course getCourse() {
        return course;
    }

    @FXML
    public void onEnrollTypeCreditMenuItem() {
        enrollTypeMenuButton.setText("Credit");
    }

    @FXML
    public void onEnrollTypeAuditMenuItem() {
        enrollTypeMenuButton.setText("Audit");
    }
}