package ku.cs.models.requestforms;

import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

public class CoEnrollRequestForm extends RequestForm {
    private String studentYear;
    private String address;
    private String phoneNumber;
    private String courseName;
    private String courseId;
    private String courseSection;
    private String semester;
    private String academicYear;
    private String teacherName;
    private String requestFormCause;

    public CoEnrollRequestForm(String requestFormID, Student student, Advisor advisor, String studentYear, Status status, String address, String phoneNumber, String courseName, String courseId, String courseSection, String semester, String academicYear, String teacherName, String requestFormCause, String rejectedCause) {
        super("ใบคำร้องขอลงทะเบียนเรียนร่วม", requestFormID, student, advisor, status, rejectedCause);
        this.studentYear = studentYear;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseSection = courseSection;
        this.semester = semester;
        this.academicYear = academicYear;
        this.teacherName = teacherName;
        this.requestFormCause = requestFormCause;
    }

    public CoEnrollRequestForm(Student student, Advisor advisor, String studentYear, Status status, String address, String phoneNumber, String courseName, String courseId, String courseSection, String semester, String academicYear, String teacherName, String requestFormCause, String rejectedCause) {
        super("ใบคำร้องขอลงทะเบียนเรียนร่วม", "ใบคำร้องฉบับร่าง", student, advisor, status, rejectedCause);
        this.studentYear = studentYear;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseSection = courseSection;
        this.semester = semester;
        this.academicYear = academicYear;
        this.teacherName = teacherName;
        this.requestFormCause = requestFormCause;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRequestFormCause() {
        return requestFormCause;
    }

    public void setRequestFormCause(String requestFormCause) {
        this.requestFormCause = requestFormCause;
    }
}
