package ku.cs.models.users;

import ku.cs.models.collections.*;

public class Admin extends User {
    DepartmentList departmentList;
    FacultyList facultyList;

    StudentList studentList;
    AdvisorList advisorList;
    DepartmentOfficerList departmentOfficerList;
    FacultyOfficerList facultyOfficerList;

    RequestFormList requestFormList;

    public Admin(String username, String password, String nameTitle, String name, String surname, String role, String recentTime, boolean status, boolean activated, String profilePictureFileName) {
        super(username, password, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }

    public DepartmentList getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(DepartmentList departmentList) {
        this.departmentList = departmentList;
    }

    public FacultyList getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(FacultyList faculty) {
        this.facultyList = faculty;
    }

    public AdvisorList getAdvisorList() {
        return advisorList;
    }

    public void setAdvisorList(AdvisorList advisors) {
        this.advisorList = advisors;
    }

    public DepartmentOfficerList getDepartmentOfficerList() {
        return departmentOfficerList;
    }

    public void setDepartmentOfficerList(DepartmentOfficerList departmentOfficerList) {
        this.departmentOfficerList = departmentOfficerList;
    }

    public FacultyOfficerList getFacultyOfficerList() {
        return facultyOfficerList;
    }

    public void setFacultyOfficerList(FacultyOfficerList facultyOfficerList) {
        this.facultyOfficerList = facultyOfficerList;
    }

    public RequestFormList getRequestFormList() {
        return requestFormList;
    }

    public void setRequestFormList(RequestFormList requestFormList) {
        this.requestFormList = requestFormList;
    }

    public UserList getUserList() {
        UserList userList = new UserList();
        userList.addUsers(advisorList.getAdvisors());
        userList.addUsers(studentList.getStudents());
        userList.addUsers(departmentOfficerList.getOfficers());
        userList.addUsers(facultyOfficerList.getOfficers());
        return userList;
    }
}