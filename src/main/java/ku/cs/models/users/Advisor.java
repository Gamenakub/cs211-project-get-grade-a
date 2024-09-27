package ku.cs.models.users;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.requestforms.RequestForm;
import ku.cs.models.collections.RequestFormList;
import ku.cs.models.collections.StudentList;

public class Advisor extends User {
    private String advisorId;
    private Department department;
    private RequestFormList requestFormList;
    private StudentList students;

    public Advisor(String username, String password, String nameTitle,String name, String surname, String advisorId, Department department) {
        super(username, password, nameTitle,name, surname,"advisor");
        this.advisorId = advisorId;
        this.department = department;

    }

    public Advisor(String username, String hashedPassword,String nameTitle ,String name, String surname, String role, String recentTime, boolean status,boolean activated, String profilePictureFileName, String advisorId, Department department) {
        super(username, hashedPassword,nameTitle ,name, surname, role, recentTime, status,activated, profilePictureFileName);
        this.advisorId = advisorId;
        this.department = department;
    }


    public String getAdvisorId() {
        return advisorId;
    }

    public Department getDepartment() {
        return department;
    }


    public RequestFormList getRequestFormList() {
        return requestFormList;
    }

    public StudentList getStudents() {
        return students;
    }

    public void setAdvisorId(String id){
        this.advisorId=id;
    }

    public Faculty getFaculty() {
        return department.getFaculty();
    }

    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList = requestFormList;
    }

    public void setStudents(StudentList students) {
        this.students = students;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public boolean checkAdvisorById(String advisorId) { return this.advisorId.equals(advisorId);}
    public void rejectRequestForm(RequestForm requestForm){}
    public void acceptRequestForm(RequestForm requestForm){}
}
