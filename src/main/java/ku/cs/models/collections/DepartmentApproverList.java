package ku.cs.models.collections;

import ku.cs.models.Department;
import ku.cs.models.DepartmentApprover;

import java.util.ArrayList;

public class DepartmentApproverList {
    private final ArrayList<DepartmentApprover> departmentApprovers;

    public DepartmentApproverList() {
        departmentApprovers = new ArrayList<>();
    }

    public void addApprover(DepartmentApprover departmentApprover) {
        departmentApprovers.add(departmentApprover);
    }

    public ArrayList<DepartmentApprover> getApprovers() {
        return departmentApprovers;
    }

    public DepartmentApproverList getRelatedToDepartmentApproverList(Department department) {
        DepartmentApproverList relatedDepartmentApproverList = new DepartmentApproverList();
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getDepartment().isId(department.getId())) {
                relatedDepartmentApproverList.addApprover(approver);
            }
        }
        return relatedDepartmentApproverList;
    }

    public ArrayList<String> getAvailableRole() {
        ArrayList<String> roles = new ArrayList<>();
        boolean hasHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("หัวหน้า")) {
                hasHead = true;
                break;
            }
        }
        boolean hasSecretary = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รักษาการณ์หัวหน้า")) {
                hasSecretary = true;
                break;
            }
        }
        if (!hasHead && !hasSecretary) {
            roles.add("หัวหน้า");
            roles.add("รักษาการณ์หัวหน้า");
        }

        boolean hasViceHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รองหัวหน้า")) {
                hasViceHead = true;
                break;
            }
        }
        if (!hasViceHead) {
            roles.add("รองหัวหน้า");
        }
        return roles;
    }

    public ArrayList<String> getUnavailableRole() {
        ArrayList<String> roles = new ArrayList<>();

        boolean hasHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("หัวหน้า")) {
                hasHead = true;
                break;
            }
        }
        if (hasHead) {
            roles.add("หัวหน้า");
            roles.add("รักษาการณ์หัวหน้า");
        }

        boolean hasViceHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รองหัวหน้า")) {
                hasViceHead = true;
                break;
            }
        }
        if (hasViceHead) {
            roles.add("รองหัวหน้า");
        }

        boolean hasSecretary = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รักษาการณ์หัวหน้า")) {
                hasSecretary = true;
                break;
            }
        }
        if (hasSecretary) {
            roles.add("รักษาการณ์หัวหน้า");
            roles.add("หัวหน้า");
        }
        return roles;
    }

    public DepartmentApproverList clone() {
        DepartmentApproverList clone = new DepartmentApproverList();
        for (DepartmentApprover approver : getApprovers()) {
            clone.addApprover(approver.getClone());
        }
        return clone;
    }

    public void removeApprover(DepartmentApprover approver) {
        departmentApprovers.remove(approver);
    }

    public void clear() {
        departmentApprovers.clear();
    }

    public ArrayList<String> getAllRole() {
        ArrayList<String> roles = new ArrayList<>();
        for (DepartmentApprover approver : getApprovers()) {
            roles.add(approver.getRole());
        }
        return roles;
    }
}
