package ku.cs.models.collections;

import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.Officer;

import java.util.ArrayList;

public class DepartmentOfficerList extends OfficerList implements Searchable<DepartmentOfficer> {
    public ArrayList<DepartmentOfficer> getDepartmentOfficers() {
        ArrayList<DepartmentOfficer> departmentOfficers = new ArrayList<>();
        for (Officer officer : getOfficers()) {
            DepartmentOfficer departmentOfficer = (DepartmentOfficer) officer;
            departmentOfficers.add(departmentOfficer);
        }
        return departmentOfficers;
    }

    @Override
    public ArrayList<DepartmentOfficer> search(String term) {
        ArrayList<DepartmentOfficer> targetDepartmentOfficers = new ArrayList<>();
        for (DepartmentOfficer departmentOfficer : getDepartmentOfficers()) {
            String name = departmentOfficer.getNameTitle() + departmentOfficer.getName() + " " + departmentOfficer.getSurname();
            String departmentName = departmentOfficer.getDepartment().getName();
            String facultyName = departmentOfficer.getFaculty().getName();
            if (name.contains(term)) {
                targetDepartmentOfficers.add(departmentOfficer);
            } else if (departmentName.contains(term)) {
                targetDepartmentOfficers.add(departmentOfficer);
            } else if (facultyName.contains(term)) {
                targetDepartmentOfficers.add(departmentOfficer);
            }
        }
        return targetDepartmentOfficers;
    }
}