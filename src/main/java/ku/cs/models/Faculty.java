package ku.cs.models;

import java.util.ArrayList;

public class Faculty {
    private String name;
    private String id;
    private ArrayList<Department> departments;

    public Faculty(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Department> getDepartments() {
        return this.departments;
    }

    public void addDepartment(Department department) {
        if (department != null) {
            this.departments.add(department);
        }
    }

    public boolean isName(String name){
        return this.name.equals(name);
    }

    public boolean isId(String Id){
        return this.name.equals(Id);
    }

}
