package ku.cs.models.collections;

import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.models.users.officers.Officer;

import java.util.ArrayList;

public class FacultyOfficerList extends OfficerList implements Searchable<FacultyOfficer> {
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

    public ArrayList<FacultyOfficer> getFacultyOfficers(){
        ArrayList<FacultyOfficer> facultyOfficers = new ArrayList<>();
        for (Officer officer : getOfficers()){
            FacultyOfficer facultyOfficer = (FacultyOfficer) officer;
            facultyOfficers.add(facultyOfficer);
        }
        return facultyOfficers;
    }


    @Override
    public ArrayList<FacultyOfficer> search(String term) {
        ArrayList<FacultyOfficer> targetFacultyOfficers = new ArrayList<>();
        for (FacultyOfficer facultyOfficer : getFacultyOfficers()) {
            String name = facultyOfficer.getNameTitle() + facultyOfficer.getName() + " " + facultyOfficer.getSurname();
            String facultyName = facultyOfficer.getFaculty().getName();
            if (name.contains(term)) {
                targetFacultyOfficers.add(facultyOfficer);
            } else if (facultyName.contains(term)) {
                targetFacultyOfficers.add(facultyOfficer);
            }
        }
        return targetFacultyOfficers;
    }

}
