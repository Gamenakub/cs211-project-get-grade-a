package ku.cs.models.requestforms;

import ku.cs.models.collections.CourseList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

public class AddDropRequestForm extends RequestForm{
    private CourseList courseList;
    private String program;
    private String academicYear;
    private String term;
    private String campus;
    private String mode;
    public static int latestRequestFormId = 1;

    public AddDropRequestForm(String mode, String requestFormId, Student student, Advisor advisor, Status status, CourseList courseList, String program, String academicYear, String term, String campus) {
        super("ใบคำร้องเพิ่มถอนรายวิชา",requestFormId, student, advisor, status);
        this.courseList = courseList;
        this.program = program;
        this.academicYear = academicYear;
        this.term = term;
        this.campus = campus;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode=mode;
    }

    public AddDropRequestForm(String mode, Student student, Advisor advisor, Status status, CourseList courseList, String program, String academicYear, String term, String campus) {
        super("ใบคำร้องเพิ่มถอนรายวิชา","พถ."+(++latestRequestFormId), student, advisor, status);
        this.courseList = courseList;
        this.program = program;
        this.academicYear = academicYear;
        this.term = term;
        this.campus = campus;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode=mode;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public boolean isAdd(){
        return mode.equals("add");
    }

    public boolean isDrop(){
        return mode.equals("drop");
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public String getProgram() {
        return program;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getTerm() {
        return term;
    }

    public String getCampus() {
        return campus;
    }

}
