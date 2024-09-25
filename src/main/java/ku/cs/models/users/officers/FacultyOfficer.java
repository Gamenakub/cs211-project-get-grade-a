package ku.cs.models.users.officers;

import ku.cs.models.Faculty;
import ku.cs.models.collections.FacultyApproverList;

public class FacultyOfficer extends Officer{
    public static final String role = "facultyOfficer";
    private Faculty faculty;
    private FacultyApproverList facultyApproverList;
    public FacultyOfficer(String username, String password, String nameTitle, String name, String surname, Faculty faculty) {
        super(username, password, nameTitle, name, surname, role);
        this.faculty = faculty;
        this.facultyApproverList = new FacultyApproverList();
    }

    public FacultyOfficer(String username, String hashedPassword, String nameTitle, String name, String surname, String role, String recentTime, boolean status, boolean activated, String profilePictureFileName, Faculty faculty) {
        super(username, hashedPassword, nameTitle, name, surname, role, recentTime, status, activated, profilePictureFileName);
        this.faculty = faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public FacultyApproverList getFacultyApproverList() {
        return facultyApproverList;
    }

    public void setFacultyApproverList(FacultyApproverList facultyApproverList) {
        this.facultyApproverList = facultyApproverList;
    }

    public String getFullName() {
        return getName() + " " + getSurname();
    }
}
