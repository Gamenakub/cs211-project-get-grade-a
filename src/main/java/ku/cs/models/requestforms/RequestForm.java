package ku.cs.models.requestforms;

import ku.cs.models.Advisor;
import ku.cs.models.DepartmentApproverList;
import ku.cs.models.Student;

import java.time.LocalDateTime;

public class RequestForm {
    private String requestFormTitle;
    private String requestFormId;
    private Student student;
    private Advisor advisor;
    private String requestFormCause;
    private int status;
    private LocalDateTime timeStamp;
    private final DepartmentApproverList approvers;

    public RequestForm(String title,String requestFormId, Student student, Advisor advisor, int status) {
        this.requestFormTitle = title;
        this.requestFormId = requestFormId;
        this.student = student;
        this.advisor = advisor;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
        this.approvers = new DepartmentApproverList();
    }

    public void setRequestFormId(String requestFormId) { this.requestFormId = requestFormId;}
    public void setStudent(Student student) { this.student = student; }
    public void setAdvisor(Advisor advisor) { this.advisor = advisor; }
    public void setRequestFormCause(String requestFormCause) { this.requestFormCause = requestFormCause; }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }
    public void setStatus(int status) {
        this.status = status;
    }

    public void addRequestFormCause(String requestFormCause) {
        this.requestFormCause = requestFormCause;
    }
    public void updateTimeStamp() {
        this.timeStamp = LocalDateTime.now();
    }

    public String getRequestFormTitle() {
        return requestFormTitle;
    }

    public String getRequestFormId() { return requestFormId; }
    public Student getStudent() { return student; }
    public Advisor getAdvisor() { return advisor; }
    public String getRequestFormCause() { return requestFormCause;}
    public int getStatus() { return status; }

    public String getTimeStamp() {
        return timeStamp.getDayOfMonth() + "/" + timeStamp.getMonthValue() + "/" + timeStamp.getYear() + "\n" +
                timeStamp.getHour() + ":" + timeStamp.getMinute() + ":" + timeStamp.getSecond();
    }
    public DepartmentApproverList getApprovers() { return approvers; }

    public boolean checkRequestFormById(String requestFormID) { return requestFormID.equals(this.requestFormId);}
}
