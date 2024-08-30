package ku.cs.models;

import ku.cs.models.users.Advisor;

public class Subject {
    private String subjectName;
    private String subjectId;
    private String subjectType;
    private String lectureSection;
    private String practiceSection;
    private String lectureCredit;
    private String practiceCredit;
    private Advisor teacher;

    public Subject(){}
    public Subject(String subjectName, String subjectId, String subjectType, String lectureSection, String lectureCredit, Advisor teacher){
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.subjectType = subjectType;
        this.lectureSection = lectureSection;
        this.lectureCredit = lectureCredit;
        this.practiceSection = "-";
        this.practiceCredit = "-";
        this.teacher = teacher;
    }

    public Subject(String subjectName, String subjectId, String subjectType, String lectureSection, String practiceSection, String lectureCredit, String practiceCredit, Advisor teacher) {
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.subjectType = subjectType;
        this.lectureSection = lectureSection;
        this.lectureCredit = lectureCredit;
        this.practiceSection = practiceSection;
        this.practiceCredit = practiceCredit;
        this.teacher = teacher;
    }

    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setSubjectType(String subjectType) { this.subjectType = subjectType; }
    public void setLectureSection(String lectureSection) { this.lectureSection = lectureSection; }
    public void setPracticeSection(String practiceSection) { this.practiceSection = practiceSection; }
    public void setLectureCredit(String lectureCredit) { this.lectureCredit = lectureCredit; }
    public void setPracticeCredit(String practiceCredit) { this.practiceCredit = practiceCredit; }
    public void setTeacher(Advisor teacher) { this.teacher = teacher; }

    public String getSubjectName() { return subjectName; }
    public String getSubjectId() { return subjectId; }
    public String getSubjectType() { return subjectType; }
    public String getLectureSection() { return lectureSection; }
    public String getPracticeSection() { return practiceSection; }
    public String getLectureCredit() { return lectureCredit;}
    public String getPracticeCredit() { return practiceCredit; }
    public Advisor getTeacher() { return teacher; }
    public boolean checkSubjectById(String subjectId) { return this.subjectId.equals(subjectId); }
}
