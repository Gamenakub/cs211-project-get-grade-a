package ku.cs.models.users.officers;

import ku.cs.models.collections.RequestFormList;
import ku.cs.models.users.User;

import java.time.LocalDateTime;

public class Officer extends User {
    private RequestFormList requestFormList;

    public Officer(String username, String password, String nameTitle, String name, String surname, String role) {
        super(username, password, nameTitle, name, surname, role);
        this.requestFormList = new RequestFormList();
    }

    public Officer(String username, String hashedPassword, String nameTitle, String name, String surname, String role, LocalDateTime recentTime, boolean status, boolean activated, String profilePictureFileName) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.requestFormList = new RequestFormList();
    }

    public RequestFormList getRequestFormList() {
        return requestFormList;
    }

    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList = requestFormList;
    }

}
