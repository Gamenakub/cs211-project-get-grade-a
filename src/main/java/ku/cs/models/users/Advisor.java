package ku.cs.models.users;

import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.requestforms.RequestForm;

public class Advisor extends User {
    public static final String roleString = "advisor";
    private final String advisorId;
    private final String department;
    private final String faculty;
    private RequestFormList requestForms;
    private StudentList students;

    public Advisor(String username, String password, String name, String surname, String advisorId, String department, String faculty) {
        super(username, password, name, surname);
        this.advisorId = advisorId;
        this.department = department;
        this.faculty = faculty;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public boolean checkAdvisorById(String advisorId) { return this.advisorId.equals(advisorId);}
    public void rejectRequestForm(RequestForm requestForm){}
    public void acceptRequestForm(RequestForm requestForm){}
}
