package ku.cs.models.collections;

import ku.cs.models.Faculty;
import ku.cs.models.FacultyApprover;

import java.util.ArrayList;

public class FacultyApproverList {
    private final ArrayList<FacultyApprover> facultyApprovers;

    public FacultyApproverList() {
        facultyApprovers = new ArrayList<>();
    }

    public void addApprover(FacultyApprover facultyApprover) {
        facultyApprovers.add(facultyApprover);
    }

    public ArrayList<FacultyApprover> getApprovers() {
        return facultyApprovers;
    }

    public FacultyApproverList getRelatedToFacultyApproverList(Faculty faculty) {
        FacultyApproverList relatedList = new FacultyApproverList();
        for (FacultyApprover facultyApprover : facultyApprovers) {
            if (facultyApprover.getFaculty().equals(faculty)) {
                relatedList.addApprover(facultyApprover);
            }
        }
        return relatedList;
    }

    public void removeApprover(FacultyApprover approver) {
        facultyApprovers.remove(approver);
    }
}
