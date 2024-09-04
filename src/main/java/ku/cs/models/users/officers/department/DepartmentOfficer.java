package ku.cs.models.users.officers.department;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.users.officers.Officer;

public class DepartmentOfficer extends Officer {
    public static final String role = "DepartmentOfficer";
    private Department department;

    public DepartmentOfficer(String username, String password, String name, String surname, String email, Department department) {
        super(username, password, name, surname, email);
        this.department = department;
    }
    public Department getDepartment() {
        return department;
    }

    public Faculty getFaculty() {
        return department.getFaculty();
    }
}
