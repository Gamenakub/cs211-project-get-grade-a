package ku.cs.models.collections;

import ku.cs.models.FacultyApprover;

import java.util.ArrayList;

public class FacultyApproverList {
    private ArrayList<FacultyApprover> facultyApprovers;
    public FacultyApproverList() {
        facultyApprovers = new ArrayList<>();
    }
    public void addApprover(FacultyApprover facultyApprover) {
        facultyApprovers.add(facultyApprover);
    }
    public void setApprovers(ArrayList<FacultyApprover> facultyApprovers) {
        this.facultyApprovers = this.facultyApprovers;
    }
    public ArrayList<FacultyApprover> getApprovers() {
        return facultyApprovers;
    }
    public FacultyApprover findApproverByNname(String name) {
        for (FacultyApprover facultyApprover : facultyApprovers) {
            if (facultyApprover.isName(name)) {
                return facultyApprover;
            }
        }
        return null;
    }
}
