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


    @Override
    public RequestFormActionHistory hashMapToModel(HashMap<String, String> row) {
        String requestId = row.get("requestId");
        String approverIdentity = row.get("approverIdentity");
        String approvedBy = row.get("approvedBy");
        RequestForm.Status action = RequestForm.Status.valueOf(row.get("action"));
        LocalDateTime approvedAt = LocalDateTime.parse(row.get("approvedAt"));

        return new RequestFormActionHistory(requestId, approverIdentity, action, approvedBy, approvedAt);
    }


    @Override
    public RequestFormActionHistoryList collectionInitializer() {
        return new RequestFormActionHistoryList();
    }


    @Override
    public void addModelToList(RequestFormActionHistoryList list, RequestFormActionHistory model) {
        list.addRequestFormApprovingHistory(model);
    }


    @Override
    public HashMap<String, String> modelToHashMap(RequestFormActionHistory model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("requestId", model.getRequestId());
        map.put("approverIdentity", model.getApproverIdentity());
        map.put("action", String.valueOf(model.getAction()));
        map.put("approvedBy", model.getApprovedByType());
        map.put("approvedAt", model.getApprovedAt().toString());
        return map;
    }


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


    @Override
    public ArrayList<RequestFormActionHistory> getCollectionArrayList(RequestFormActionHistoryList collection) {
        return collection.getRequestFormApprovingHistories();
    }
}
