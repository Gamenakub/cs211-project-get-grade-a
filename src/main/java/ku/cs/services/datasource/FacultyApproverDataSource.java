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

    private FacultyList facultyList;


    public FacultyApproverDataSource(FacultyList facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String getFileName() {
        return "faculty-approver.csv";
    }

    @Override
    public String getDirectory() {
        return "data/";
    }



    @Override
    public FacultyApprover hashMapToModel(HashMap<String, String> row) {
        String name = row.get("name");
        String surname = row.get("surname");
        String role = row.get("role");
        Faculty faculty = facultyList.findFacultyById(row.get("faculty")); // Assuming Faculty has a constructor that takes a String
        return new FacultyApprover(name, surname, role, faculty);
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
        headers.add("name");
        headers.add("surname");
        headers.add("role");
        headers.add("faculty");
        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(FacultyApprover model) {
        HashMap<String, String> row = new HashMap<>();
        row.put("name", model.getName());
        row.put("surname", model.getSurname());
        row.put("role", model.getRole());
        row.put("faculty", model.getFaculty().getId()); // Assuming Faculty has a getName() method
        return row;
    }

    @Override
    public ArrayList<FacultyApprover> getCollectionArrayList(FacultyApproverList collection) {
        return collection.getApprovers();
    }
}
