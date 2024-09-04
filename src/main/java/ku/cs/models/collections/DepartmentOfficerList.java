package ku.cs.models.collections;

import ku.cs.models.users.officers.Officer;
import ku.cs.models.users.officers.DepartmentOfficer;

import java.util.ArrayList;

public class DepartmentOfficerList extends OfficerList {
    public ArrayList<DepartmentOfficer> findByFacultyName(String facultyName){
        ArrayList<DepartmentOfficer> matchedOfficer = new ArrayList<DepartmentOfficer>();
        for (Officer officer : getOfficers()){
            DepartmentOfficer departmentOfficer = (DepartmentOfficer) officer;
            if (departmentOfficer.getFaculty().isName(facultyName)){
                matchedOfficer.add(departmentOfficer);
            }
        }
        return matchedOfficer;
    }

    public ArrayList<DepartmentOfficer> findByDepartmentName(String departmentName){
        ArrayList<DepartmentOfficer> matchedOfficer = new ArrayList<DepartmentOfficer>();
        for (Officer officer : getOfficers()){
            DepartmentOfficer departmentOfficer = (DepartmentOfficer) officer;
            if (departmentOfficer.getDepartment().isName(departmentName)){
                matchedOfficer.add(departmentOfficer);
            }
        }
        return matchedOfficer;
    }

}