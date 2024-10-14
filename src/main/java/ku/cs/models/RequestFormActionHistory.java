package ku.cs.models;

import ku.cs.models.requestforms.RequestForm;

import java.time.LocalDateTime;

public class RequestFormActionHistory {
    private final String requestId;
    private final String approvedBy;
    private final LocalDateTime approvedAt;
    private final String approverIdentity;
    private final RequestForm.Status action;

    public RequestFormActionHistory(String requestId, String approverIdentity, RequestForm.Status action, String approvedBy) {
        this.requestId = requestId;
        this.action = action;

        this.approverIdentity = approverIdentity;
        this.approvedBy = approvedBy;
        this.approvedAt = LocalDateTime.now();
    }

    public RequestFormActionHistory(String requestId, String approverIdentity, RequestForm.Status action, String approvedBy, LocalDateTime approvedAt) {
        this.approverIdentity = approverIdentity;
        this.action = action;
        this.requestId = requestId;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
    }

    public RequestForm.Status getAction() {
        return action;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getApprovedByType() {
        return approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public String getApproverIdentity() {
        return approverIdentity;
    }
}
