package ku.cs.models.requestforms;

import ku.cs.models.collections.CourseList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

public class AddDropRequestForm extends RequestForm {
    private CourseList courseList;
    private String program;
    private String academicYear;
    private String semester;
    private String campus;
    private String phoneNumber;
    private final String mode;

    public AddDropRequestForm(String mode, String requestFormId, Student student, Advisor advisor, Status status, CourseList courseList, String program, String academicYear, String semester, String campus, String phoneNumber, String rejectedCause) {
        super("ใบคำร้องเพิ่มถอนรายวิชา", requestFormId, student, advisor, status, rejectedCause);
        this.courseList = courseList;
        this.program = program;
        this.academicYear = academicYear;
        this.semester = semester;
        this.campus = campus;
        this.phoneNumber = phoneNumber;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode = mode;
    }

    public AddDropRequestForm(String mode, Student student, Advisor advisor, Status status, CourseList courseList, String program, String academicYear, String semester, String campus, String phoneNumber, String rejectedCause) {
        super("ใบคำร้องเพิ่มถอนรายวิชา", "ใบคำร้องฉบับร่าง", student, advisor, status, rejectedCause);
        this.courseList = courseList;
        this.program = program;
        this.academicYear = academicYear;
        this.semester = semester;
        this.campus = campus;
        this.phoneNumber = phoneNumber;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode = mode;
    }

    public AddDropRequestForm(String requestFormId, String mode, Student student, Advisor advisor, Status status, String program, String academicYear, String semester, String campus, String phoneNumber, String rejectedCause) {
        super("ใบคำร้องเพิ่มถอนรายวิชา", requestFormId, student, advisor, status, rejectedCause);
        this.program = program;
        this.academicYear = academicYear;
        this.semester = semester;
        this.campus = campus;
        this.phoneNumber = phoneNumber;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public boolean isAdd() {
        return mode.equals("add");
    }

    public boolean isDrop() {
        return mode.equals("drop");
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String term) {
        this.semester = term;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
