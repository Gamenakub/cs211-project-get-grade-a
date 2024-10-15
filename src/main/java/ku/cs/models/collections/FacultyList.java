package ku.cs.models.collections;

import ku.cs.models.Faculty;
import java.util.ArrayList;

public class FacultyList implements Searchable<Faculty> {
    private final ArrayList<Faculty> faculties;

    public FacultyList() {
        faculties = new ArrayList<>();
    }

    public ArrayList<Faculty> getFaculties() {
        return faculties;
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public Faculty findFacultyByName(String name) {
        for (Faculty faculty : faculties) {
            if (faculty.isName(name)) {
                return faculty;
            }
        }
        return null;
    }

    public Faculty findFacultyById(String Id) {
        for (Faculty faculty : faculties) {
            if (faculty.isId(Id)) {
                return faculty;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Faculty> search(String keyword) {
        ArrayList<Faculty> targetFaculties = new ArrayList<>();
        for (Faculty faculty : faculties) {
            String name = faculty.getName();
            String id = faculty.getId();
            if (name.contains(keyword)) {
                targetFaculties.add(faculty);
            } else if (id.contains(keyword)) {
                targetFaculties.add(faculty);
            }
        }
        return targetFaculties;
    }
}
