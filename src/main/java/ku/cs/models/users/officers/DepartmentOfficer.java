package ku.cs.models.users.officers;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.AdvisorList;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Student;
import ku.cs.services.PermissionControlledCollection;

import java.util.ArrayList;

public class DepartmentOfficer extends Officer{
    public static final String role = "departmentOfficer";
    private Department department;
    private StudentList studentList;

    public PermissionControlledCollection<Student> getStudentWriteOnlyCollection() {
        return studentPermissionControlledCollection;
    }

    private PermissionControlledCollection<Student> studentPermissionControlledCollection;
    private AdvisorList advisorList;
    private DepartmentApproverList departmentApproverList;

    public DepartmentOfficer(String username, String password, String nameTitle, String name, String surname, Department department) {
        super(username, password, nameTitle, name, surname, role);
        this.department = department;
        this.studentList = new StudentList();
        this.advisorList = new AdvisorList(new ArrayList<>());
        this.departmentApproverList = new DepartmentApproverList();
    }

    public DepartmentOfficer(String username, String hashedPassword, String nameTitle, String name, String surname, String role, String recentTime, boolean status, boolean activated, String profilePictureFileName, Department department, StudentList studentList) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.department = department;
        this.studentList = studentList;
    }

    public void setAdvisorList(AdvisorList advisorList) {
        this.advisorList = advisorList;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }

    public void setStudentListWriter(PermissionControlledCollection<Student> studentPermissionControlledCollection) {
        this.studentPermissionControlledCollection = studentPermissionControlledCollection;
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void addStudent(Student student) {
        studentList.addStudent(student);
        studentPermissionControlledCollection.add(student);
    }

    public Department getDepartment() {
        return department;
    }

    public Faculty getFaculty() {
        return department.getFaculty();
    }

    public AdvisorList getAdvisorList() {
        return advisorList;
    }

    public DepartmentApproverList getDepartmentApproverList() {
        return departmentApproverList;
    }

    public void setDepartmentApproverList(DepartmentApproverList departmentApproverList) {
        this.departmentApproverList = departmentApproverList;
    }
}
