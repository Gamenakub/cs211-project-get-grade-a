package ku.cs.models.collections;

import ku.cs.models.RequestFormActionHistory;

import java.util.ArrayList;

public class RequestFormActionHistoryList {
    private ArrayList<RequestFormActionHistory> requestFormApprovingHistories;

    public RequestFormActionHistoryList() {
        requestFormApprovingHistories = new ArrayList<>();
    }

    public void addRequestFormApprovingHistory(RequestFormActionHistory requestFormActionHistory) {
        requestFormApprovingHistories.add(requestFormActionHistory);
    }

    public void setRequestFormApprovingHistories(ArrayList<RequestFormActionHistory> requestFormApprovingHistories) {
        this.requestFormApprovingHistories = requestFormApprovingHistories;
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

    public RequestFormActionHistory getLatestRequestFormApprovingHistory(){
        if (requestFormApprovingHistories.size() == 0) {
            return null;
        }
        RequestFormActionHistory latest = requestFormApprovingHistories.get(0);
        for (RequestFormActionHistory requestFormActionHistory : requestFormApprovingHistories) {
            if (requestFormActionHistory.getApprovedAt().isAfter(latest.getApprovedAt())) {
                latest = requestFormActionHistory;
            }
        }
        return latest;
    }


}
