package ku.cs.models.requestforms;

import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.collections.RequestFormActionHistoryList;
import ku.cs.models.users.Advisor;
import ku.cs.models.users.Student;
import java.time.LocalDateTime;

public class RequestForm {

    private final String requestFormTitle;
    private String requestFormId;
    private final Student student;
    private Advisor advisor;
    private Status status;
    private String rejectedCause;
    private RequestFormActionHistoryList requestFormActionHistoryList = new RequestFormActionHistoryList();

    public RequestForm(String title, String requestFormId, Student student, Advisor advisor, Status status, String rejectedCause) {
        this.requestFormTitle = title;
        this.requestFormId = requestFormId;
        this.student = student;
        this.advisor = advisor;
        this.status = status;
        this.rejectedCause = rejectedCause;
    }

    public void setRequestFormApprovingHistoryList(RequestFormActionHistoryList requestFormActionHistoryList) {
        this.requestFormActionHistoryList = requestFormActionHistoryList;
    }

    public void setRequestFormNumber(String requestFormId) {
        this.requestFormId = requestFormId;
    }

    public RequestFormActionHistoryList getRequestFormActionHistoryList() {
        return requestFormActionHistoryList;
    }

    public void addApprovingHistory(RequestFormActionHistory approvingHistory) {
        requestFormActionHistoryList.addRequestFormApprovingHistory(approvingHistory);
    }

    public String getRequestFormTitle() {
        return requestFormTitle;
    }

    public String getRequestFormId() {
        return requestFormId;
    }

    public Student getStudent() {
        return student;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(RequestFormActionHistory actionOwner) {
        this.status = actionOwner.getAction();
        addApprovingHistory(actionOwner);
    }

    public String getRejectedCause() {
        return rejectedCause;
    }

    public void setRejectedCause(String rejectedCause) {
        this.rejectedCause = rejectedCause;
    }

    public LocalDateTime getTimeStamp() {
        return requestFormActionHistoryList.getLatestRequestFormApprovingHistory().getApprovedAt();
    }

    public enum Status {
        CREATING("คำร้องใหม่\nกำลังถูกสร้าง"),PENDING_TO_ADVISOR("ใบคำร้องใหม่\nคำร้องส่งต่อให้อาจารย์ที่ปรึกษา"), PENDING_TO_DEPARTMENT("อนุมัติโดยอาจารย์ที่ปรึกษา\nคำร้องส่งต่อให้หัวหน้าภาควิชา"), REJECTED_BY_ADVISOR("ปฏิเสธโดยอาจารย์ที่ปรึกษา\nคำร้องถูกปฏิเสธ"), APPROVED_BY_DEPARTMENT("อนุมัติโดยหัวหน้าภาควิชา\nคำร้องดำเนินการครบถ้วน"), PENDING_TO_FACULTY("อนุมัติโดยหัวหน้าภาควิชา\nคำร้องส่งต่อให้คณบดี"), REJECTED_BY_DEPARTMENT("ปฏิเสธโดยหัวหน้าภาควิชา\nคำร้องถูกปฏิเสธ"), REJECTED_BY_FACULTY("ปฏิเสธโดยคณบดี\nคำร้องถูกปฏิเสธ"), APPROVED_BY_FACULTY("อนุมัติโดยคณบดี\nคำร้องดำเนินการครบถ้วน");
        private final String label;

        Status(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

}
