package ku.cs.models;

public class DepartmentApprover {
    private String name;
    private String surname;
    private String role;
    private final Department department;
    private String nameTitle;

    public DepartmentApprover(String nameTitle, String name, String surname, String role, Department department) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.department = department;
        this.nameTitle = nameTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public String getRepresentativeRole() {
        return getRole() + "ภาควิชา" + getDepartment().getName();
    }

    public DepartmentApprover getClone() {
        return new DepartmentApprover(nameTitle, name, surname, role, department);
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
        return nameTitle + name + " " + surname + " (" + role + "ภาควิชา" + department.getName() + ")";
    }

    public boolean isLeaderOrSubLeader() {
        return role.equals("รักษาการณ์หัวหน้า") || role.equals("หัวหน้า");
    }
}
