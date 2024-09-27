package ku.cs.models;

public class Course {
    private String relatedRequestFormId;
    private String courseName;
    private String courseId;
    private String courseYear;
    private String courseType;
    private String lectureSection;
    private String practiceSection;
    private String lectureCredit;
    private String practiceCredit;
    private String professorName;

    public Course(){}
    public Course(String courseName, String courseId, String courseYear, String courseType, String lectureSection, String lectureCredit, String professorName, String relatedRequestFormId){
        this.relatedRequestFormId = relatedRequestFormId;
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseYear = courseYear;
        this.courseType = courseType;
        this.lectureSection = lectureSection;
        this.lectureCredit = lectureCredit;
        this.practiceSection = "-";
        this.practiceCredit = "-";
        this.professorName = professorName;
    }

    public Course(String courseName, String courseId, String courseYear, String courseType, String lectureSection, String practiceSection, String lectureCredit, String practiceCredit, String professorName, String relatedRequestFormId) {
        this.relatedRequestFormId = relatedRequestFormId;
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseYear = courseYear;
        this.courseType = courseType;
        this.lectureSection = lectureSection;
        this.lectureCredit = lectureCredit;
        this.practiceSection = practiceSection;
        this.practiceCredit = practiceCredit;
        this.professorName = professorName;
    }

    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public void setCourseYear(String courseYear) { this.courseYear = courseYear; }
    public void setCourseType(String courseType) { this.courseType = courseType; }
    public void setLectureSection(String lectureSection) { this.lectureSection = lectureSection; }
    public void setPracticeSection(String practiceSection) { this.practiceSection = practiceSection; }
    public void setLectureCredit(String lectureCredit) { this.lectureCredit = lectureCredit; }
    public void setPracticeCredit(String practiceCredit) { this.practiceCredit = practiceCredit; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

    public String getCourseName() { return courseName; }
    public String getCourseId() { return courseId; }
    public String getCourseYear() { return courseYear; }
    public String getCourseType() { return courseType; }
    public String getLectureSection() { return lectureSection; }
    public String getPracticeSection() { return practiceSection; }
    public String getLectureCredit() { return lectureCredit;}
    public String getPracticeCredit() { return practiceCredit; }
    public String getProfessorName() { return professorName; }
    public boolean checkSubjectById(String subjectId) { return this.courseId.equals(subjectId); }

    public String getRelatedRequestFormId() {
        return relatedRequestFormId;
    }
}
