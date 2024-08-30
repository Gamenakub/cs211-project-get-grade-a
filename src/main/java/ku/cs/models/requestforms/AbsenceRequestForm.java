package ku.cs.models.requestforms;

import java.time.LocalDateTime;

public class AbsenceRequestForm extends RequestForm {
    private String course;
    private String year;
    private String phoneNumber;
    private String facebookID;
    private String lineID;
    private String absenceType;
    private LocalDateTime absenceDateTimeFrom;
    private LocalDateTime absenceDateTimeUntil;
    private SubjectList subjects;

    public AbsenceRequestForm(String requestFormId, Student student, Advisor advisor, int status, String course, String year, String phoneNumber, String facebookID, String lineID, String absenceType, LocalDateTime absenceDateTimeFrom, LocalDateTime absenceDateTimeUntil) {
        super("ใบคำร้องขอลาป่วยหรือลากิจ",requestFormId, student, advisor, status);
        this.course = course;
        this.year = year;
        this.phoneNumber = phoneNumber;
        this.facebookID = facebookID;
        this.lineID = lineID;
        this.absenceType = absenceType;
        this.absenceDateTimeFrom = absenceDateTimeFrom;
        this.absenceDateTimeUntil = absenceDateTimeUntil;
        this.subjects = new SubjectList();
    }

    public void setCourse(String course) { this.course = course; }
    public void setYear(String year) { this.year = year; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setFacebookID(String facebookID) { this.facebookID = facebookID;}
    public void setLineID(String lineID) {this.lineID = lineID; }
    public void setAbsenceType(String absenceType) { this.absenceType = absenceType; }
    public void setAbsenceDateTimeFrom(LocalDateTime absenceDateTimeFrom) { this.absenceDateTimeFrom = absenceDateTimeFrom; }
    public void setAbsenceDateTimeUntil(LocalDateTime absenceDateTimeUntil) { this.absenceDateTimeUntil = absenceDateTimeUntil; }
    public void setSubjects(SubjectList subjects) { this.subjects = subjects; }
    public void addSubject(Subject subject) { this.subjects.addSubject(subject); }

    public String getCourse() { return course; }
    public String getYear() { return year; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFacebookID() { return facebookID; }
    public String getLineID() { return lineID;}
    public String getAbsenceType() { return absenceType; }
    public LocalDateTime getAbsenceDateTimeFrom() { return absenceDateTimeFrom; }
    public LocalDateTime getAbsenceDateTimeUntil() { return absenceDateTimeUntil; }
    public SubjectList getSubjects() { return subjects; }
}

