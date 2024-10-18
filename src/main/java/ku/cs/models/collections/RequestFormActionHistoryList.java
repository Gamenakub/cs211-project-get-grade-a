package ku.cs.models.collections;

import ku.cs.models.RequestFormActionHistory;
import java.util.ArrayList;

public class RequestFormActionHistoryList {
    private final ArrayList<RequestFormActionHistory> requestFormApprovingHistories;

    public RequestFormActionHistoryList() {
        requestFormApprovingHistories = new ArrayList<>();
    }

    public void addRequestFormApprovingHistory(RequestFormActionHistory requestFormActionHistory) {
        requestFormApprovingHistories.add(requestFormActionHistory);
    }

    public ArrayList<RequestFormActionHistory> getRequestFormApprovingHistories() {
        return requestFormApprovingHistories;
    }

    public RequestFormActionHistoryList getRelatedToRequestFormApprovingHistoryList(String requestId) {
        RequestFormActionHistoryList relatedList = new RequestFormActionHistoryList();
        for (RequestFormActionHistory requestFormActionHistory : requestFormApprovingHistories) {
            if (requestFormActionHistory.getRequestId().equals(requestId)) {
                relatedList.addRequestFormApprovingHistory(requestFormActionHistory);
            }
        }
        return relatedList;
    }

    public RequestFormActionHistory getLatestRequestFormApprovingHistory() {
        if (requestFormApprovingHistories.isEmpty()) {
            return null;
        }
        RequestFormActionHistory latest = requestFormApprovingHistories.getFirst();
        for (RequestFormActionHistory requestFormActionHistory : requestFormApprovingHistories) {
            if (requestFormActionHistory.getApprovedAt().isAfter(latest.getApprovedAt())) {
                latest = requestFormActionHistory;
            }
        }
        return latest;
    }


}
