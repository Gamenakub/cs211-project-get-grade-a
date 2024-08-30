package ku.cs.models.collections;

import ku.cs.models.Department;

import java.util.ArrayList;

public class DepartmentList {
    private ArrayList<Department> departments;
    public DepartmentList() {
        departments = new ArrayList<Department>();
    }
    public ArrayList<Department> getDepartments() {
        return departments;
    }
    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Department findDepartmentByName(String name) {
        for (Department department : departments) {
            if (department.isName(name)) {
                return department;
            }
        }
        return null;
    }

    public Department findDepartmentById(String Id) {
        for (Department department : departments) {
            if (department.isId(Id)) {
                return department;
            }
        }
        return null;
    }

}
