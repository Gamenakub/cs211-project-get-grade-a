package ku.cs.models;

public class FacultyApprover {
    private String name;
    private String surname;
    private String role;
    private Faculty faculty;

    public FacultyApprover(String name, String surname, String role, Faculty faculty, Department department) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.faculty = faculty;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRole() { return role; }
    public Faculty getFaculty() { return faculty; }
    public void setRole(String role) { this.role = role; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
    public boolean isName(String name) {
        return this.name.equals(name);
    }

}
