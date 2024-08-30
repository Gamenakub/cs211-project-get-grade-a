package ku.cs.models.users.officers;

import ku.cs.models.collections.RequestFormList;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.users.User;

public class Officer extends User {
    public static final String role = "Officer";
    private String officerEmail;
    private final RequestFormList requestFormList;
    public Officer(String username, String password, String name, String surname, String email) {
        super(username, password, name, surname);
        this.requestFormList = new RequestFormList();
        this.officerEmail = email;
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

    public boolean checkOfficerByEmail(String officerEmail) {
        return this.officerEmail.equals(officerEmail);
    }
}
