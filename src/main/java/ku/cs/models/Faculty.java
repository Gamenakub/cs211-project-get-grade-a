package ku.cs.models;

import ku.cs.models.collections.DepartmentList;

public class Faculty implements Comparable<Faculty> {
    private String name;
    private String id;
    private DepartmentList departmentList;

    public Faculty(String name, String id) {
        this.name = name;
        this.id = id;
        this.departmentList = new DepartmentList();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DepartmentList getDepartmentList() {
        return this.departmentList;
    }

    public void setDepartmentList(DepartmentList departmentList) {
        this.departmentList = departmentList;
    }

    public Department addDepartment(String departmentName, String departmentId) {
        Department department = new Department(departmentName, departmentId, this);
        departmentList.addDepartment(department);
        return department;
    }

    public void addDepartment(Department department) {
        departmentList.addDepartment(department);
        department.setFaculty(this);
    }

    public void removeDepartment(Department department) {
        departmentList.removeDepartment(department);
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public boolean isId(String Id) {
        return this.id.equals(Id);
    }

    @Override
    public int compareTo(Faculty o) {
        return (this.getId()).compareTo(o.getId());
    }
}
