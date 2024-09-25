package ku.cs.models.collections;

import ku.cs.models.Department;
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
    public DepartmentApproverList getRelatedToDepartmentApproverList(Department department) {
        DepartmentApproverList relatedDepartmentApproverList = new DepartmentApproverList();
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getDepartment().isId(department.getId())) {
                relatedDepartmentApproverList.addApprover(approver);
            }
        }
        return relatedDepartmentApproverList;
    }
    public ArrayList<String> getAvailableRole(){
        ArrayList<String> roles = new ArrayList<>();
        // if หัวหน้า not in approver
        boolean hasHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("หัวหน้า")) {
                hasHead = true;
            }
        }
        if (!hasHead) {
            roles.add("หัวหน้า");
        }
        // if รองหัวหน้า not in approver
        boolean hasViceHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รองหัวหน้า")) {
                hasViceHead = true;
            }
        }
        if (!hasViceHead) {
            roles.add("รองหัวหน้า");
        }
        // if รักษาการณ์ not in approver
        boolean hasSecretary = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รักษาการณ์หัวหน้า")) {
                hasSecretary = true;
            }
        }
        if (!hasSecretary && !hasHead) {
            roles.add("รักษาการณ์หัวหน้า");
        }
        return roles;
    }

    public ArrayList<String> getUnavailableRole(){
        ArrayList<String> roles = new ArrayList<>();
        // if หัวหน้า in approver
        boolean hasHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("หัวหน้า")) {
                hasHead = true;
            }
        }
        if (hasHead) {
            roles.add("หัวหน้า");
        }
        // if รองหัวหน้า in approver
        boolean hasViceHead = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รองหัวหน้า")) {
                hasViceHead = true;
            }
        }
        if (hasViceHead) {
            roles.add("รองหัวหน้า");
        }
        // if รักษาการณ์ in approver
        boolean hasSecretary = false;
        for (DepartmentApprover approver : getApprovers()) {
            if (approver.getRole().equals("รักษาการณ์หัวหน้า")) {
                hasSecretary = true;
            }
        }
        if (hasSecretary && !hasHead) {
            roles.add("รักษาการณ์หัวหน้า");
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
}
