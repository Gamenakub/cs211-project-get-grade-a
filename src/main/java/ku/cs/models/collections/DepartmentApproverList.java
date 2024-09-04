package ku.cs.models.collections;

import ku.cs.models.DepartmentApprover;

import java.util.ArrayList;

public class DepartmentApproverList {
    private ArrayList<DepartmentApprover> departmentApprovers;
    public DepartmentApproverList() {
        departmentApprovers = new ArrayList<>();
    }
    public void addApprover(DepartmentApprover departmentApprover) {
        departmentApprovers.add(departmentApprover);
    }
    public void setApprovers(ArrayList<DepartmentApprover> departmentApprovers) {
        this.departmentApprovers = departmentApprovers;
    }
    public ArrayList<DepartmentApprover> getApprovers() {
        return departmentApprovers;
    }
    public DepartmentApprover findApproverByUsername(String name) {
        for (DepartmentApprover departmentApprover : departmentApprovers) {
            if (departmentApprover.isName(name)) {
                return departmentApprover;
            }
        }
        return null;
    }
}
