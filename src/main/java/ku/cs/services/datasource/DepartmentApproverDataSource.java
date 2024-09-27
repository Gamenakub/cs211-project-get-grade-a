package ku.cs.services.datasource;

import ku.cs.models.Department;
import ku.cs.models.DepartmentApprover;
import ku.cs.models.collections.DepartmentApproverList;
import ku.cs.models.collections.DepartmentList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentApproverDataSource implements Writable<DepartmentApproverList, DepartmentApprover>, Readable<DepartmentApproverList, DepartmentApprover> {

    private DepartmentList departmentList;

    public DepartmentApproverDataSource(DepartmentList departmentList) {
        this.departmentList = departmentList;
    }


    @Override
    public String getFileName() {
        return "department-approver.csv"; // Example filename
    }

    @Override
    public String getDirectory() {
        return "data/"; // Example directory
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("name");
        header.add("surname");
        header.add("role");
        header.add("departmentId");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(DepartmentApprover departmentApprover) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", departmentApprover.getName());
        map.put("surname", departmentApprover.getSurname());
        map.put("role", departmentApprover.getRole());
        map.put("departmentId", departmentApprover.getDepartment().getId()); // Assuming Department has a getName() method
        return map;
    }

    @Override
    public ArrayList<DepartmentApprover> getCollectionArrayList(DepartmentApproverList collection) {
        return collection.getApprovers();
    }

    @Override
    public DepartmentApprover hashMapToModel(HashMap<String, String> row) {
        String name = row.get("name");
        String surname = row.get("surname");
        String role = row.get("role");
        Department department = departmentList.findDepartmentById(row.get("departmentId"));
        return new DepartmentApprover(name, surname, role,  department);
    }

    @Override
    public DepartmentApproverList collectionInitializer() {
        return new DepartmentApproverList();
    }

    @Override
    public void addModelToList(DepartmentApproverList list, DepartmentApprover model) {
        list.addApprover(model);
    }
}
