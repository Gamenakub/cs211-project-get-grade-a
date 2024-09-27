package ku.cs.models.requestforms;

import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.collections.RequestFormActionHistoryList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import ku.cs.services.Utility;

import java.time.LocalDateTime;

public class RequestForm {

    public RequestFormActionHistoryList getRequestFormApprovingHistoryList() {
        return requestFormActionHistoryList;
    }

    public void setRequestFormApprovingHistoryList(RequestFormActionHistoryList requestFormActionHistoryList) {
        this.requestFormActionHistoryList = requestFormActionHistoryList;
    }

    public enum Status {
        PENDING_TO_ADVISOR, PENDING_TO_DEPARTMENT, REJECTED_BY_ADVISOR, CANCELED_BY_STUDENT, APPROVED_BY_DEPARTMENT, PENDING_TO_FACULTY, REFUSED_BY_DEPARTMENT, CREATING, REFUSED_BY_FACULTY, APPROVED_BY_FACULTY;
    }

    private String requestFormTitle;
    private String requestFormId;
    private Student student;
    private Advisor advisor;
    private String requestFormCause;
    private Status status;
    private LocalDateTime timeStamp;
    private String rejectedFormCause;

    public RequestFormActionHistoryList getRequestFormActionHistoryList() {
        return requestFormActionHistoryList;
    }

    private RequestFormActionHistoryList requestFormActionHistoryList = new RequestFormActionHistoryList();

    //constructor สำหรับสร้าง object ใหม่
    public RequestForm(String title,String requestFormId, Student student, Advisor advisor, Status status) {
        this.requestFormTitle = title;
        this.requestFormId = requestFormId;
        this.student = student;
        this.advisor = advisor;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }
    //constructor สำหรับดึง object
    public RequestForm(String title,String requestFormId, Student student, Advisor advisor, Status status, LocalDateTime timeStamp) {
        this.requestFormTitle = title;
        this.requestFormId = requestFormId;
        this.student = student;
        this.advisor = advisor;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public void setRequestFormId(String requestFormId) { this.requestFormId = requestFormId;}
    public void setStudent(Student student) { this.student = student; }
    public void setAdvisor(Advisor advisor) { this.advisor = advisor; }
    public void setRequestFormCause(String requestFormCause) { this.requestFormCause = requestFormCause; }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }
    public void setStatus(RequestFormActionHistory actionOwner) {
        this.status = actionOwner.getAction();
        addApprovingHistory(actionOwner);
    }
    public void addApprovingHistory(RequestFormActionHistory approvingHistory) {
        requestFormActionHistoryList.addRequestFormApprovingHistory(approvingHistory);
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


    public String getSenderId(){
        return student.getStudentId();
    }

    public String getRequestFormId() { return requestFormId; }
    public Student getStudent() { return student; }
    public Advisor getAdvisor() { return advisor; }
    public String getRequestFormCause() { return requestFormCause;}
    public Status getStatus() { return status; }

    public String getTimeStamp() {
        return Utility.getTimeStamp(timeStamp);
    }

    public boolean checkRequestFormById(String requestFormID) { return requestFormID.equals(this.requestFormId);}

}
