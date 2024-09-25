package ku.cs.models;

public class DepartmentApprover {
    private String name;
    private String surname;
    private String role;
    private Department department;

    public DepartmentApprover(String name, String surname, String role, Department department) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.department = department;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getRole() { return role; }
    public Department getDepartment() { return department; }
    public Faculty getFaculty() { return department.getFaculty(); }
    public void setRole(String role) { this.role = role; }
    public void setDepartment(Department department) { this.department = department; }
    public boolean isName(String name) {
        return this.name.equals(name);
    }
    public String getRepresentativeRole(){
        return getRole()+"ภาควิชา"+getDepartment().getName();
    }
    public void setName(String name) {
        this.name = name;
    }
    public DepartmentApprover getClone() {
        return new DepartmentApprover(name, surname, role, department);
    }

    public boolean compareByValue(DepartmentApprover departmentApprover) {
        return name.equals(departmentApprover.getName()) && surname.equals(departmentApprover.getSurname()) && role.equals(departmentApprover.getRole()) && department.isId(departmentApprover.getDepartment().getId());
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
