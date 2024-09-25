package ku.cs.models;

import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.User;

public class FormDataModel {
    private String formHolder;
    private String acceptMessage;
    private String formType;
    private String nisitId;
    private String topic;
    private String number;
    private RequestForm formObject;
    private boolean isReadonly;
    private User user;
    public FormDataModel(boolean isReadonly, User user, RequestForm formObject) {
        this.formObject = formObject;
        this.isReadonly = isReadonly;
        this.user = user;
    }
    public boolean isReadonly() {
        return isReadonly;
    }
    public String getFormHolder() {
        return formHolder;
    }
    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }
    public String getAcceptMessage() {
        return acceptMessage;
    }
    public String getFormType() {
        return formType;
    }

    public void setFormObject(RequestForm obj) {
        this.formObject = obj;
    }
    public RequestForm getFormObject() {
        return formObject;
    }
}
