package ku.cs.models;

public class Department {
    private String name;
    private String id;
    private Faculty faculty;

    public Department(String name, String id, Faculty faculty) {
        this.name = name;
        this.id = id;
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public boolean isName(String name){
        return this.name.equals(name);
    }

    public boolean isId(String Id){
        return this.name.equals(Id);
    }
}