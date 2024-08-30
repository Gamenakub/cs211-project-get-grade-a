package ku.cs.models.collections;

import ku.cs.models.users.officers.Officer;
import ku.cs.models.users.officers.faculty.FacultyOfficer;

import java.util.ArrayList;

public class FacultyOfficerList extends OfficerList{
    public ArrayList<FacultyOfficer> findByFacultyName(String facultyName){
        ArrayList<FacultyOfficer> matchedOfficer = new ArrayList<FacultyOfficer>();
        for (Officer officer : getOfficers()){
            FacultyOfficer facultyOfficer = (FacultyOfficer) officer;
            if (facultyOfficer.getFaculty().isName(facultyName)){
                matchedOfficer.add(facultyOfficer);
            }
        }
        return matchedOfficer;
    }
}
