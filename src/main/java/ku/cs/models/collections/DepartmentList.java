package ku.cs.models.collections;

import ku.cs.models.Department;
import java.util.ArrayList;

public class DepartmentList implements Searchable<Department> {
    private final ArrayList<Department> departments;

    public DepartmentList() {
        departments = new ArrayList<>();
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        departments.add(department);
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
    public ArrayList<Department> search(String keyword) {
        ArrayList<Department> targetDepartments = new ArrayList<>();
        for (Department department : departments) {
            String name = department.getName();
            String id = department.getId();
            String facultyName = department.getFaculty().getName();
            if (name.contains(keyword)) {
                targetDepartments.add(department);
            } else if (id.contains(keyword)) {
                targetDepartments.add(department);
            } else if (facultyName.contains(keyword)) {
                targetDepartments.add(department);
            }
        }
        return targetDepartments;
    }
}
