package ku.cs.services.datasource;


import ku.cs.models.RequestFormActionHistory;
import ku.cs.models.collections.RequestFormActionHistoryList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestFormApprovingHistoryDataSource implements Writable<RequestFormActionHistoryList, RequestFormActionHistory>, Readable<RequestFormActionHistoryList, RequestFormActionHistory> {
    @Override
    public String getFileName() {
        return "request_form_action_history.csv";
    }

    @Override
    public String getDirectory() {
        return "data";
    }

    // Converts a row (HashMap) into a RequestFormApprovingHistory object
    @Override
    public RequestFormActionHistory hashMapToModel(HashMap<String, String> row) {
        String requestId = row.get("requestId");
        String approverIdentity = row.get("approverIdentity"); // You may need to deserialize this based on its actual type
        RequestFormActionHistory.ApproverType approvedBy = RequestFormActionHistory.ApproverType.valueOf(row.get("approvedBy"));
        RequestForm.Status action = RequestForm.Status.valueOf(row.get("action"));
        LocalDateTime approvedAt = LocalDateTime.parse(row.get("approvedAt"));
        RequestFormActionHistory history = new RequestFormActionHistory(requestId, approverIdentity, action, approvedBy, approvedAt);
        // Using reflection or modifying the constructor to accept approvedAt (optional)
        return history;
    }

    // Initializes a new collection (RequestFormApprovingHistoryList)
    @Override
    public RequestFormActionHistoryList collectionInitializer() {
        return new RequestFormActionHistoryList();
    }

    // Adds a RequestFormApprovingHistory model to the list
    @Override
    public void addModelToList(RequestFormActionHistoryList list, RequestFormActionHistory model) {
        list.addRequestFormApprovingHistory(model);
    }

    // Converts a RequestFormApprovingHistory model to a HashMap
    @Override
    public HashMap<String, String> modelToHashMap(RequestFormActionHistory model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("requestId", model.getRequestId());
        map.put("approverIdentity", model.getApproverIdentity()); // Serialization logic may vary
        map.put("action", String.valueOf(model.getAction()));
        map.put("approvedBy", model.getApprovedByType().name());
        map.put("approvedAt", model.getApprovedAt().toString());
        return map;
    }

    // Retrieves the list of table headers for the CSV file
    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("requestId");
        header.add("approverIdentity");
        header.add("action");
        header.add("approvedBy");
        header.add("approvedAt");
        return header;
    }

    // Converts the collection (RequestFormApprovingHistoryList) into an ArrayList of models
    @Override
    public ArrayList<RequestFormActionHistory> getCollectionArrayList(RequestFormActionHistoryList collection) {
        return collection.getRequestFormApprovingHistories();
    }
}
