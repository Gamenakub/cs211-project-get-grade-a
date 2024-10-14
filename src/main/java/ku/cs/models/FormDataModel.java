package ku.cs.models;

import ku.cs.models.requestforms.RequestForm;

public class FormDataModel {
    private String acceptMessage;
    private final RequestForm formObject;
    private final boolean isReadOnly;

    public FormDataModel(boolean isReadOnly, RequestForm formObject) {
        this.formObject = formObject;
        this.isReadOnly = isReadOnly;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }

    public RequestForm getFormObject() {
        return formObject;
    }

}
