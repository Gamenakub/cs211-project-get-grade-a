package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;

import static ku.cs.services.AlertService.showWarning;

public class AbsenceCourseComponentController {
    @FXML private TextField textFieldCourseName;
    @FXML private TextField textFieldCourseId;
    @FXML private TextField textFieldCourseYear;
    @FXML private TextField textFieldCourseSection;
    @FXML private TextField textFieldCourseTeacher;
    @FXML private Button deleteButton;
    @FXML private VBox vBox;

    private Course course;
    private CourseList courseList;
    private EventCallback onUpdateCallback = (eventName) -> {};

    public void onUpdate(EventCallback onUpdateCallback){
        this.onUpdateCallback = onUpdateCallback;
    }

    public void initialize(CourseList courseList, Course course) {
        this.course = course;
        this.courseList = courseList;
        setTextField();
        deleteButton.setOnAction(event -> {
            onUpdateCallback.onEvent("delete");
        });
    }

    public boolean confirm(){
        String courseName = textFieldCourseName.getText();
        String courseId = textFieldCourseId.getText();
        String courseYear = textFieldCourseYear.getText();
        String courseSection = textFieldCourseSection.getText();
        String courseTeacher = textFieldCourseTeacher.getText();
        if(courseName.isEmpty() || courseId.isEmpty() ||  courseYear.isEmpty() || courseSection.isEmpty() || courseTeacher.isEmpty()){
            showWarning("กรุณาใส่ข้อมูลให้ครบก่อนเพิ่มรายวิชา\nหรือเปลี่ยนไปหน้าถัดไป");
            return false;
        }
        course.setCourseName(courseName);
        course.setCourseId(courseId);
        course.setCourseYear(courseYear);
        course.setLectureSection(courseSection);
        course.setProfessorName(courseTeacher);
        return true;
    }

    public void setTextField() {
        textFieldCourseName.setText(course.getCourseName());
        textFieldCourseId.setText(course.getCourseId());
        textFieldCourseYear.setText(course.getCourseYear());
        textFieldCourseSection.setText(course.getLectureSection());
        textFieldCourseTeacher.setText(course.getProfessorName());
    }

    public Course getCourse() {
        return course;
    }


}
