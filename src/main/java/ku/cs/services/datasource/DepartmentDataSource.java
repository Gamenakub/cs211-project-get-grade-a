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

    private FacultyList facultyList;

    public DepartmentDataSource(FacultyList facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String getFileName() {
        return "department.csv";  // Assuming the data is stored in CSV format
    }

    @Override
    public String getDirectory() {
        return "data";  // Directory where the file is located
    }

    @Override
    public Department hashMapToModel(HashMap<String, String> row) {
        String name = row.get("departmentName");
        String id = row.get("departmentId");
        String facultyName = row.get("FacultyName");

        Faculty faculty = facultyList.findFacultyByName(facultyName);  // Assuming Faculty can be created this way
        return faculty.addDepartment(name, id);  // Create a Department model from the row data
    }

    @Override
    public DepartmentList collectionInitializer() {
        return new DepartmentList();  // Initialize an empty DepartmentList
    }

    @Override
    public void addModelToList(DepartmentList list, Department model) {
        list.addDepartment(model);  // Add a Department model to the DepartmentList
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("departmentName");         // Name of the department
        header.add("departmentId");           // ID of the department
        header.add("FacultyName"); // Faculty name associated with the department
        return header;  // Return the table header for CSV
    }

    @Override
    public HashMap<String, String> modelToHashMap(Department model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("departmentName", model.getName());               // Map the department name
        map.put("departmentId", model.getId());                   // Map the department ID
        map.put("FacultyName", model.getFaculty().getName());  // Map the faculty name
        return map;
    }

    @Override
    public ArrayList<Department> getCollectionArrayList(DepartmentList collection) {
        return collection.getDepartments();  // Return the list of departments from DepartmentList
    }
}
