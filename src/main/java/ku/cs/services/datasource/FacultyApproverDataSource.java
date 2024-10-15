package ku.cs.services.datasource;

import ku.cs.models.Faculty;
import ku.cs.models.FacultyApprover;
import ku.cs.models.collections.FacultyApproverList;
import ku.cs.models.collections.FacultyList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;
import java.util.ArrayList;
import java.util.HashMap;

public class FacultyApproverDataSource implements Writable<FacultyApproverList, FacultyApprover>, Readable<FacultyApproverList, FacultyApprover> {
    private final FacultyList facultyList;

    public FacultyApproverDataSource(FacultyList facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String getFileName() {
        return "faculty_approver.csv";
    }

    @Override
    public String getDirectory() {
        return "data/";
    }


    @Override
    public FacultyApprover hashMapToModel(HashMap<String, String> row) {
        String nameTitle = row.get("nameTitle");
        String name = row.get("name");
        String surname = row.get("surname");
        String role = row.get("role");
        Faculty faculty = facultyList.findFacultyById(row.get("faculty"));
        return new FacultyApprover(nameTitle, name, surname, role, faculty);
    }

    @Override
    public FacultyApproverList collectionInitializer() {
        return new FacultyApproverList();
    }

    @Override
    public void addModelToList(FacultyApproverList list, FacultyApprover model) {
        list.addApprover(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("nameTitle");
        headers.add("name");
        headers.add("surname");
        headers.add("role");
        headers.add("faculty");
        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(FacultyApprover model) {
        HashMap<String, String> row = new HashMap<>();
        row.put("nameTitle", model.getNameTitle());
        row.put("name", model.getName());
        row.put("surname", model.getSurname());
        row.put("role", model.getRole());
        row.put("faculty", model.getFaculty().getId());
        return row;
    }

    @Override
    public ArrayList<FacultyApprover> getCollectionArrayList(FacultyApproverList collection) {
        return collection.getApprovers();
    }
}
