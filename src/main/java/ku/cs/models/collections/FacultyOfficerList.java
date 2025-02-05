package ku.cs.models.collections;

import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.models.users.officers.Officer;
import java.util.ArrayList;

public class FacultyOfficerList extends OfficerList implements Searchable<FacultyOfficer> {

    public ArrayList<FacultyOfficer> getFacultyOfficers() {
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        for (Officer officer : getOfficers()) {
            FacultyOfficer facultyOfficer = (FacultyOfficer) officer;
            facultyOfficers.add(facultyOfficer);
        }
        return facultyOfficers;
    }


    @Override
    public ArrayList<FacultyOfficer> search(String term) {
        ArrayList<FacultyOfficer> targetFacultyOfficers = new ArrayList<>();
        for (FacultyOfficer facultyOfficer : getFacultyOfficers()) {
            String facultyName = facultyOfficer.getFaculty().getName();
            if (facultyOfficer.getFullName().contains(term)) {
                targetFacultyOfficers.add(facultyOfficer);
            } else if (facultyOfficer.getUsername().contains(term)) {
                targetFacultyOfficers.add(facultyOfficer);
            } else if (facultyName.contains(term)) {
                targetFacultyOfficers.add(facultyOfficer);
            }
        }
        return targetFacultyOfficers;
    }

}
