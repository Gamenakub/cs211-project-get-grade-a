package ku.cs.models.users.officers;

import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.User;

public class Officer extends User {
    public static final String role = "officer";
    private String officerEmail;
    private RequestFormList requestFormList;

    public Officer(String username, String password,String nameTitle, String name, String surname, String role) {
        super(username, password, nameTitle, name, surname, role);
        this.requestFormList = new RequestFormList();
    }

    public Officer(String username, String hashedPassword,String nameTitle, String name, String surname, String role, String recentTime, boolean status,boolean activated, String profilePictureFileName){
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.requestFormList = new RequestFormList();
    }

    public void addRequestForm(RequestForm requestForm) {
        if(requestForm != null) {
            requestFormList.addRequestForm(requestForm);
        }
    }

    public String getOfficerEmail() {
        return officerEmail;
    }

    public void setOfficerEmail(String officerEmail) {
        this.officerEmail = officerEmail;
    }

    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList = requestFormList;
    }

    public RequestFormList getRequestFormList() {
        return requestFormList;
    }

    public boolean checkOfficerByEmail(String officerEmail) {
        return this.officerEmail.equals(officerEmail);
    }
}
