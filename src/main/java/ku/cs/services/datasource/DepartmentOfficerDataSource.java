package ku.cs.services.datasource;

import ku.cs.models.collections.DepartmentList;
import ku.cs.models.collections.DepartmentOfficerList;
import ku.cs.models.collections.StudentList;
import ku.cs.models.users.Student;
import ku.cs.models.users.officers.DepartmentOfficer;
import ku.cs.models.users.officers.Officer;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentOfficerDataSource implements Readable<DepartmentOfficerList, DepartmentOfficer>, Writable<DepartmentOfficerList, DepartmentOfficer> {

    private final DepartmentList departmentList;
    private final StudentList studentList;

    public DepartmentOfficerDataSource(DepartmentList departmentList, StudentList studentList) {
        this.departmentList = departmentList;
        this.studentList = studentList;
    }

    @Override
    public String getFileName() {
        return "department_officer.csv";
    }

    @Override
    public String getDirectory() {
        return "data/users";
    }

    @Override
    public DepartmentOfficer hashMapToModel(HashMap<String, String> row) {
        StudentList studentList = new StudentList();
        for (Student student : this.studentList.getStudents()) {
            if (student.getDepartment().getId().equals(row.get("department"))) {
                studentList.addStudent(student);
            }
        }
        return new DepartmentOfficer(
                row.get("username"),
                row.get("hashedPassword"),
                row.get("nameTitle"),
                row.get("name"),
                row.get("surname"),
                row.get("role"),
                row.get("recentTime").equals("null") ? null : LocalDateTime.parse(row.get("recentTime")),
                Boolean.parseBoolean(row.get("status")),
                Boolean.parseBoolean(row.get("activated")),
                row.get("profilePictureFileName"),
                this.departmentList.findDepartmentById(row.get("department")),
                studentList
        );
    }

    @Override
    public DepartmentOfficerList collectionInitializer() {
        return new DepartmentOfficerList();
    }

    @Override
    public void addModelToList(DepartmentOfficerList list, DepartmentOfficer model) {
        list.addOfficer(model);
    }



    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("username");
        headers.add("nameTitle");
        headers.add("hashedPassword");
        headers.add("name");
        headers.add("surname");
        headers.add("role");
        headers.add("recentTime");
        headers.add("status");
        headers.add("activated");
        headers.add("profilePictureFileName");
        headers.add("department");

        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(DepartmentOfficer model) {
        HashMap<String, String> row = new HashMap<>();
        row.put("username", model.getUsername());
        row.put("hashedPassword", model.getHashedPassword());
        row.put("nameTitle", model.getNameTitle());
        row.put("name", model.getName());
        row.put("surname", model.getSurname());
        row.put("role", model.getRole());
        row.put("recentTime", model.getRecentTimeString());
        row.put("status", String.valueOf(model.getStatus()));
        row.put("activated", String.valueOf(model.getActivated()));
        row.put("profilePictureFileName", model.getProfilePictureFileName());
        row.put("department", model.getDepartment().getId());
        return row;
    }

    @Override
    public ArrayList<DepartmentOfficer> getCollectionArrayList(DepartmentOfficerList collection) {
        ArrayList<DepartmentOfficer> officers = new ArrayList<>();
        for (Officer officer : collection.getOfficers()) {
            officers.add((DepartmentOfficer) officer);
        }
        return officers;
    }
}
