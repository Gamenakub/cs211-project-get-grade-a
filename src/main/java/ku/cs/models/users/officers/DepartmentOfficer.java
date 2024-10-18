package ku.cs.models.users.officers;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.collections.StudentList;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DepartmentOfficer extends Officer {
    private Department department;
    private StudentList studentList;
    private AdvisorList advisorList;
    private final DepartmentApproverList departmentApproverList;
    public DepartmentOfficer(String username, String password, String nameTitle, String name, String surname, Department department) {
        super(username, password, nameTitle, name, surname, "departmentOfficer");
        this.department = department;
        this.studentList = new StudentList();
        this.advisorList = new AdvisorList(new ArrayList<>());
        this.departmentApproverList = new DepartmentApproverList();
    }

    public DepartmentOfficer(String username, String hashedPassword, String nameTitle, String name, String surname, String role, LocalDateTime recentTime, boolean status, boolean activated, String profilePictureFileName, Department department, StudentList studentList) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.department = department;
        this.studentList = studentList;
        this.departmentApproverList = new DepartmentApproverList();
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return department.getFaculty();
    }

    public AdvisorList getAdvisorList() {
        return advisorList;
    }

    public void setAdvisorList(AdvisorList advisorList) {
        this.advisorList = advisorList;
    }

    public DepartmentApproverList getDepartmentApproverList() {
        return departmentApproverList;
    }

}
