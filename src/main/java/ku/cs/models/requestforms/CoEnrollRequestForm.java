package ku.cs.models.requestforms;

import ku.cs.models.Course;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;

public class CoEnrollRequestForm extends RequestForm {
    public static int latestRequestFormId = 1;
    private String program;
    private String address;
    private Course course;
    private String teacher;

    public CoEnrollRequestForm(String requestFormID, Student student, Advisor advisor, String program, Status status, String address) {
        super("ใบคำร้องขอลงทะเบียนเรียนร่วม",requestFormID, student, advisor, status);
        this.program = program;
        this.address = address;
        this.course = new Course();
    }

    public CoEnrollRequestForm(Student student, Advisor advisor, String program, Status status, String address) {
        super("ใบคำร้องขอลงทะเบียนเรียนร่วม","ลร."+(++latestRequestFormId), student, advisor, status);
        this.program = program;
        this.address = address;
        this.course = new Course();
    }

    public void setProgram(String program) { this.program = program; }
    public void setAddress(String address) { this.address = address; }
    public void setSubject(Course course) { this.course = course; }
    public void setTeacher(String teacher) { this.teacher = teacher; }

    public String getProgram() { return program; }
    public String getAddress() {
        return address;
    }
    public Course getSubject() {
        return course;
    }
    public String getTeacher() {
        return teacher;
    }
}
