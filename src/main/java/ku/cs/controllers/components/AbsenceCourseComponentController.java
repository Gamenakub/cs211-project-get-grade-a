package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.Course;

public class AbsenceCourseComponentController {
    @FXML private TextField courseNameTextField;
    @FXML private TextField courseIdTextField;
    @FXML private TextField courseYearTextField;
    @FXML private TextField courseSectionTextField;
    @FXML private TextField courseTeacherTextField;
    @FXML private Button deleteButton;

    private Course course;
    private EventCallback onUpdateCallback;

    public void onUpdate(EventCallback onUpdateCallback) {
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initialize(Course course, boolean readOnly) {
        this.course = course;
        setTextField();
        if (readOnly) {
            deleteButton.setVisible(false);
            return;
        }
        deleteButton.setOnAction(event -> onUpdateCallback.onEvent("delete"));
    }

    public void confirm() {
        String courseName = courseNameTextField.getText();
        String courseId = courseIdTextField.getText();
        String courseYear = courseYearTextField.getText();
        String courseSection = courseSectionTextField.getText();
        String courseTeacher = courseTeacherTextField.getText();

        if (courseName.isEmpty() || courseId.isEmpty() || courseYear.isEmpty() || courseSection.isEmpty() || courseTeacher.isEmpty()) {
            throw new IllegalArgumentException("กรุณาใส่ข้อมูลรายวิชาให้ครบถ้วน");
        }

        if (!isNumeric(courseYear)) {
            throw new IllegalArgumentException("ปีการศึกษาต้องเป็นตัวเลขจำนวนเต็ม");
        }

        course.setCourseName(courseName);
        course.setCourseId(courseId);
        course.setCourseYear(courseYear);
        course.setLectureSection(courseSection);
        course.setProfessorName(courseTeacher);
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void setTextField() {
        courseNameTextField.setText(course.getCourseName());
        courseIdTextField.setText(course.getCourseId());
        courseYearTextField.setText(course.getCourseYear());
        courseSectionTextField.setText(course.getLectureSection());
        courseTeacherTextField.setText(course.getProfessorName());
    }

    public Course getCourse() {
        return course;
    }


}
