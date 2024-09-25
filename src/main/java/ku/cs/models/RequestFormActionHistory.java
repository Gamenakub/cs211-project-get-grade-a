package ku.cs.models;

import ku.cs.models.requestforms.RequestForm;

import java.time.LocalDateTime;

public class RequestFormActionHistory {
    private String requestId;
    private ApproverType approvedBy;
    private LocalDateTime approvedAt;
    private String approverIdentity;
    private RequestForm.Status action;

    public RequestForm.Status getAction() {
        return action;
    }

    public enum ApproverType {
        FACULTY,
        DEPARTMENT,
        STUDENT, ADVISOR
    }

    public RequestFormActionHistory(String requestId, String approverIdentity, RequestForm.Status action, ApproverType approvedBy) {
        this.requestId = requestId;
        this.action = action;

        this.approverIdentity = approverIdentity;
        this.approvedBy = approvedBy;
        this.approvedAt = LocalDateTime.now();
    }

    public RequestFormActionHistory(String requestId, String approverIdentity, RequestForm.Status action, ApproverType approvedBy, LocalDateTime approvedAt) {
        this.approverIdentity = approverIdentity;
        this.action = action;
        this.requestId = requestId;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }

    public String getRequestId() {
        return requestId;
    }

    public ApproverType getApprovedByType() {
        return approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public String getApproverIdentity() {
        return approverIdentity;
    }
}
