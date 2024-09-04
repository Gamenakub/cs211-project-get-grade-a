package ku.cs.models.collections;

import ku.cs.models.requestforms.RequestForm;

import java.util.ArrayList;

public class RequestFormList {
    private ArrayList<RequestForm> requestForms;
    public RequestFormList() {
        requestForms = new ArrayList<>();
    }
    public void addRequestForm(RequestForm requestForm) { requestForms.add(requestForm); }
    public ArrayList<RequestForm> getRequestForms() { return requestForms; }
    public RequestForm findRequestFormById(String requestFormId) {
        for (RequestForm requestForm : requestForms) {
            if(requestForm.checkRequestFormById(requestFormId))
                return requestForm;
        }
        return null;
    }
}
