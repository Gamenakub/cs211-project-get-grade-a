package ku.cs.models.requestforms;

import ku.cs.models.collections.CourseList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

import java.time.LocalDate;

public class AbsenceRequestForm extends RequestForm {
    private String studentYear;
    private String phoneNumber;
    private String facebookID;
    private String lineID;
    private String absenceType;
    private LocalDate absenceDateTimeFrom;
    private LocalDate absenceDateTimeUntil;
    private CourseList courseList;
    private String requestFormCause;

    public AbsenceRequestForm(Student student, Advisor advisor, Status status, String studentYear, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDate absenceDateTimeFrom, LocalDate absenceDateTimeUntil, CourseList courseList, String requestFormCause, String rejectedCause) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ", "ใบคำร้องฉบับร่าง", student, advisor, status, rejectedCause);
        this.studentYear = studentYear;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.courseList = courseList;
        this.requestFormCause = requestFormCause;
    }

    public AbsenceRequestForm(String requestFormId, Student student, Advisor advisor, Status status, String studentYear, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDate absenceDateTimeFrom, LocalDate absenceDateTimeUntil, CourseList courseList, String requestFormCause, String rejectedCause) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ", requestFormId, student, advisor, status, rejectedCause);
        this.studentYear = studentYear;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.courseList = courseList;
        this.requestFormCause = requestFormCause;
    }

    public AbsenceRequestForm(String requestFormId, Student student, Advisor advisor, Status status, String studentYear, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDate absenceDateTimeFrom, LocalDate absenceDateTimeUntil, String requestFormCause, String rejectedCause) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ", requestFormId, student, advisor, status, rejectedCause);
        this.studentYear = studentYear;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.courseList = new CourseList();
        this.requestFormCause = requestFormCause;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public void setStudentYear(String studentYear) {
        this.studentYear = studentYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(String absenceType) {
        this.absenceType = absenceType;
    }

    public LocalDate getAbsenceDateFrom() {
        return absenceDateTimeFrom;
    }

    public void setAbsenceDateFrom(LocalDate absenceDateTimeFrom) {
        this.absenceDateTimeFrom = absenceDateTimeFrom;
    }

    public LocalDate getAbsenceDateUntil() {
        return absenceDateTimeUntil;
    }

    public void setAbsenceDateUntil(LocalDate absenceDateTimeUntil) {
        this.absenceDateTimeUntil = absenceDateTimeUntil;
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

    public String getRequestFormCause() {
        return requestFormCause;
    }

    public void setRequestFormCause(String requestFormCause) {
        this.requestFormCause = requestFormCause;
    }
}

