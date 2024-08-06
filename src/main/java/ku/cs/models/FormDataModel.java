package ku.cs.models;

public class FormDataModel {
    private String formHolder;
    private String acceptMessage;
    private String formType;
    private String nisitId;
    private String topic;
    private String number;
    public FormDataModel(String formHolder, String formType, String nisitId, String topic, String number) {
        this.formHolder = formHolder;
        this.formType = formType;
        this.nisitId = nisitId;
        this.topic = topic;
        this.number = number;
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

    public String getNisitId() {
        return nisitId;
    }

    public String getTopic() {
        return topic;
    }

    public String getNumber() {
        return number;
    }
}
