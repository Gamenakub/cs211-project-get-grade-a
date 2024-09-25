package ku.cs.models.requestforms;

import ku.cs.models.Course;
import ku.cs.models.collections.CourseList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

import java.time.LocalDate;

public class AbsenceRequestForm extends RequestForm {
    private String program;
    private String academicYear;
    private String phoneNumber;
    private String facebookID;
    private String lineID;
    private String absenceType;
    private LocalDate absenceDateTimeFrom;
    private LocalDate absenceDateTimeUntil;
    private CourseList courseList;
    public static int latestRequestFormId = 2;

    public AbsenceRequestForm(String requestFormId, Student student, Advisor advisor, Status status, String program, String academicYear, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDate absenceDateTimeFrom, LocalDate absenceDateTimeUntil) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ",requestFormId, student, advisor, status);
        this.program = program;
        this.academicYear = academicYear;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.courseList = new CourseList();
    }
    public AbsenceRequestForm(Student student, Advisor advisor, Status status, String program, String academicYear, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDate absenceDateTimeFrom, LocalDate absenceDateTimeUntil) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ","ขร."+(++latestRequestFormId), student, advisor, status);
        this.program = program;
        this.academicYear = academicYear;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.courseList = new CourseList();
    }

    public void setProgram(String program) { this.program = program; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setFacebookID(String facebookID) { this.facebookID = facebookID;}
    public void setLineID(String lineID) {this.lineID = lineID; }
    public void setAbsenceType(String absenceType) { this.absenceType = absenceType; }
    public void setAbsenceDateFrom(LocalDate absenceDateTimeFrom) { this.absenceDateTimeFrom = absenceDateTimeFrom; }
    public void setAbsenceDateUntil(LocalDate absenceDateTimeUntil) { this.absenceDateTimeUntil = absenceDateTimeUntil; }
    public void setCourseList(CourseList courseList) { this.courseList = courseList; }
    public void addCourse(Course course) { this.courseList.addCourse(course); }

    public String getProgram() { return program; }
    public String getAcademicYear() { return academicYear; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFacebookID() { return facebookID; }
    public String getLineID() { return lineID;}
    public String getAbsenceType() { return absenceType; }
    public LocalDate getAbsenceDateFrom() { return absenceDateTimeFrom; }
    public LocalDate getAbsenceDateUntil() { return absenceDateTimeUntil; }
    public CourseList getCourseList() { return courseList; }
}

