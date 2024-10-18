package ku.cs.models;

public class FacultyApprover {
    private String name;
    private String surname;
    private String role;
    private final Faculty faculty;
    private String nameTitle;

    public FacultyApprover(String nameTitle, String name, String surname, String role, Faculty faculty) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.faculty = faculty;
        this.nameTitle = nameTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String s) {
        this.surname = s;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getFullName() {
        return nameTitle + name + " " + surname;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String text) {
        nameTitle = text;
    }

    public String getFullRepresentationString() {
        return nameTitle + name + " " + surname + " (" + role + "คณะ" + faculty.getName() + ")";
    }
}
