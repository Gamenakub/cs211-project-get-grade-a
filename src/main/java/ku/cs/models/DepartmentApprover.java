package ku.cs.models;

public class DepartmentApprover {
    private String name;
    private String surname;
    private String role;
    private Department department;
    private Faculty faculty;

    public DepartmentApprover(String name, String surname, String role, Faculty faculty, Department department) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.faculty = faculty;
        this.department = department;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRole() { return role; }
    public Department getDepartment() { return department; }
    public Faculty getFaculty() { return faculty; }
    public void setRole(String role) { this.role = role; }
    public void setDepartment(Department department) { this.department = department; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
    public boolean isName(String name) {
        return this.name.equals(name);
    }

}