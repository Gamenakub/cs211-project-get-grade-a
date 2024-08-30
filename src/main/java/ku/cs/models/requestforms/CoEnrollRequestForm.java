package ku.cs.models.requestforms;

public class CoEnrollRequestForm extends RequestForm {
    private String course;
    private String address;
    private SubjectList subjects;
    private Advisor teacher;

    public CoEnrollRequestForm(String requestFormID, Student student, Advisor advisor, String course, int status, String address) {
        super("ใบคำร้องขอลงทะเบียนเรียนร่วม",requestFormID, student, advisor, status);
        this.course = course;
        this.address = address;
        this.subjects = new SubjectList();
    }

    public void setCourse(String course) { this.course = course; }
    public void setAddress(String address) { this.address = address; }
    public void setSubjects(SubjectList subjects) { this.subjects = subjects; }
    public void setTeacher(Advisor teacher) { this.teacher = teacher; }
    public void addSubject(Subject subject) {
        subjects.addSubject(subject);
    }

    public String getCourse() { return course; }
    public String getAddress() {
        return address;
    }
    public SubjectList getSubjects() {
        return subjects;
    }
    public Advisor getTeacher() {
        return teacher;
    }
}
