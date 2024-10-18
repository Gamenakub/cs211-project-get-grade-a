package ku.cs.services.datasource;

import ku.cs.models.Department;
import ku.cs.models.Faculty;
import ku.cs.models.collections.DepartmentList;
import ku.cs.models.collections.FacultyList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;
import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentDataSource implements Writable<DepartmentList, Department>, Readable<DepartmentList, Department> {

    private final FacultyList facultyList;

    public DepartmentDataSource(FacultyList facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String getFileName() {
        return "department.csv";
    }

    @Override
    public String getDirectory() {
        return "data";
    }

    @Override
    public Department hashMapToModel(HashMap<String, String> row) {
        String name = row.get("departmentName");
        String id = row.get("departmentId");
        String facultyName = row.get("facultyName");

        Faculty faculty = facultyList.findFacultyByName(facultyName);
        return faculty.addDepartment(name, id);
    }

    @Override
    public DepartmentList collectionInitializer() {
        return new DepartmentList();
    }

    @Override
    public void addModelToList(DepartmentList list, Department model) {
        list.addDepartment(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("departmentName");
        header.add("departmentId");
        header.add("facultyName");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(Department model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("departmentName", model.getName());
        map.put("departmentId", model.getId());
        map.put("facultyName", model.getFaculty().getName());
        return map;
    }

    @Override
    public ArrayList<Department> getCollectionArrayList(DepartmentList collection) {
        return collection.getDepartments();
    }
}
