package ku.cs.models.collections;

import ku.cs.models.Department;

import java.util.ArrayList;

public class DepartmentList implements Searchable<Department> {
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

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    @Override
    public ArrayList<Department> search(String term) {
        ArrayList<Department> targetDepartments = new ArrayList<>();
        for(Department department : departments) {
            String name = department.getName();
            String id = department.getId();
            String facultyName = department.getFaculty().getName();
            if(name.contains(term)){
                targetDepartments.add(department);
            } else if (id.contains(term)){
                targetDepartments.add(department);
            } else if (facultyName.contains(term)){
                targetDepartments.add(department);
            }
        }
        return targetDepartments;
    }
}
