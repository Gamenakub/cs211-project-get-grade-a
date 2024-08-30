package ku.cs.models.users.officers.faculty;

import ku.cs.models.Faculty;
import ku.cs.models.users.officers.Officer;

public class FacultyOfficer extends Officer {
    public static final String role = "FacultyOfficer";
    private Faculty faculty;
    public FacultyOfficer(String username, String password, String name, String surname, String email, Faculty faculty) {
        super(username, password, name, surname, email);
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
