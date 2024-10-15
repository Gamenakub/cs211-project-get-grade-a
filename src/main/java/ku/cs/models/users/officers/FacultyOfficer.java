package ku.cs.models.users.officers;

import ku.cs.models.Faculty;
import ku.cs.models.collections.FacultyApproverList;
import java.time.LocalDateTime;

public class FacultyOfficer extends Officer {
    private Faculty faculty;
    private final FacultyApproverList facultyApproverList;

    public FacultyOfficer(String username, String password, String nameTitle, String name, String surname, Faculty faculty) {
        super(username, password, nameTitle, name, surname, "facultyOfficer");
        this.faculty = faculty;
        this.facultyApproverList = new FacultyApproverList();
    }

    public FacultyOfficer(String username, String hashedPassword, String nameTitle, String name, String surname, String role, LocalDateTime recentTime, boolean status, boolean activated, String profilePictureFileName, Faculty faculty) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.faculty = faculty;
        this.facultyApproverList = new FacultyApproverList();
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public FacultyApproverList getFacultyApproverList() {
        return facultyApproverList;
    }

}
