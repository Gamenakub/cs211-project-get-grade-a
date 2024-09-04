package ku.cs.models.requestforms;

import ku.cs.models.collections.SubjectList;
import ku.cs.models.users.Student;
import ku.cs.models.users.Advisor;

public class AddDropRequestForm extends RequestForm {
    private SubjectList subjectList;
    private String course;
    private String year;
    private String term;
    private String campus;
    private String mode;

    public AddDropRequestForm(String mode, String requestFormId, Student student, Advisor advisor, int status, SubjectList subjectList, String course, String year, String term, String campus) {
        super("ใบคำร้องเพิ่มถอนรายวิชา",requestFormId, student, advisor, status);
        this.subjectList = subjectList;
        this.course = course;
        this.year = year;
        this.term = term;
        this.campus = campus;
        if (!(mode.equals("add") || mode.equals("drop")))
            throw new IllegalArgumentException("AddDropRequestForm requires 'add' or 'drop'");
        this.mode=mode;
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

    public SubjectList getSubjectList() {
        return subjectList;
    }

    public String getCourse() {
        return course;
    }

    public String getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }

    public String getCampus() {
        return campus;
    }

}
