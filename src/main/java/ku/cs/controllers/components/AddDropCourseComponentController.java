package ku.cs.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import ku.cs.controllers.components.tables.EventCallback;
import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;

import static ku.cs.services.AlertService.showWarning;

public class AddDropCourseComponentController {
    @FXML private TextField textFieldCourseNo;
    @FXML private TextField textFieldCourseId;
    @FXML private TextField textFieldCourseYear;
    @FXML private TextField textFieldCourseLectureSection;
    @FXML private TextField textFieldCoursePracticeSection;
    @FXML private TextField textFieldCourseName;
    @FXML private TextField textFieldCourseLectureCredit;
    @FXML private TextField textFieldCoursePracticeCredit;
    @FXML private MenuButton menuButtonEnrollType;
    @FXML private Button deleteButton;

    private String enrollType = "Credit";

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
        String courseId = textFieldCourseId.getText();
        String courseYear = textFieldCourseYear.getText();
        String courseLectureSection = textFieldCourseLectureSection.getText();
        String coursePracticeSection = textFieldCoursePracticeSection.getText();
        String courseName = textFieldCourseName.getText();
        String courseLectureCredit = textFieldCourseLectureCredit.getText();
        String subjectPracticeCredit = textFieldCoursePracticeCredit.getText();
        String enrollType = menuButtonEnrollType.getText();
        if(courseId.isEmpty() || courseYear.isEmpty() || courseLectureSection.isEmpty() || coursePracticeSection.isEmpty() || courseName.isEmpty()
                || courseLectureCredit.isEmpty() || subjectPracticeCredit.isEmpty()){
            showWarning("กรุณาใส่ข้อมูลให้ครบก่อนเพิ่มรายวิชา\nหรือเปลี่ยนไปหน้าถัดไป");
            return false;
        }
        course.setCourseId(courseId);
        course.setCourseYear(courseYear);
        course.setLectureSection(courseLectureSection);
        course.setPracticeSection(coursePracticeSection);
        course.setCourseName(courseName);
        course.setLectureCredit(courseLectureCredit);
        course.setPracticeCredit(subjectPracticeCredit);
        course.setCourseType(enrollType);
        return true;
    }

    public void setTextField() {
        textFieldCourseNo.setText(courseList.getCourseListSize()+"");
        textFieldCourseId.setText(course.getCourseId());
        textFieldCourseYear.setText(course.getCourseYear());
        textFieldCourseLectureSection.setText(course.getLectureSection());
        textFieldCoursePracticeSection.setText(course.getPracticeSection());
        textFieldCourseName.setText(course.getCourseName());
        textFieldCourseLectureCredit.setText(course.getLectureCredit());
        textFieldCoursePracticeCredit.setText(course.getPracticeCredit());
        menuButtonEnrollType.setText(course.getCourseType());
    }

    public Course getCourse() {
        return course;
    }

    public void onMenuButtonEnrollTypeCredit() {
        enrollType = "Credit";
        menuButtonEnrollType.setText("Credit");
    }

    public void onMenuButtonEnrollTypeAudit() {
        enrollType = "Audit";
        menuButtonEnrollType.setText("Audit");
    }
}