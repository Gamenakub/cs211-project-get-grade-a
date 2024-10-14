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

    private final DepartmentList departmentList;

    public DepartmentApproverDataSource(DepartmentList departmentList) {
        this.departmentList = departmentList;
    }


    @Override
    public String getFileName() {
        return "department_approver.csv";
    }

    @Override
    public String getDirectory() {
        return "data/";
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("nameTitle");
        header.add("name");
        header.add("surname");
        header.add("role");
        header.add("departmentId");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(DepartmentApprover departmentApprover) {
        HashMap<String, String> map = new HashMap<>();
        map.put("nameTitle", departmentApprover.getNameTitle());
        map.put("name", departmentApprover.getName());
        map.put("surname", departmentApprover.getSurname());
        map.put("role", departmentApprover.getRole());
        map.put("departmentId", departmentApprover.getDepartment().getId());
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
        String nameTitle = row.get("nameTitle");
        Department department = departmentList.findDepartmentById(row.get("departmentId"));
        return new DepartmentApprover(nameTitle, name, surname, role, department);
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
