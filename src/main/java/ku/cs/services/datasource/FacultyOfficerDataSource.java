package ku.cs.services.datasource;

import ku.cs.models.collections.FacultyList;
import ku.cs.models.collections.FacultyOfficerList;
import ku.cs.models.users.officers.FacultyOfficer;
import ku.cs.models.users.officers.Officer;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class FacultyOfficerDataSource implements Readable<FacultyOfficerList, FacultyOfficer>, Writable<FacultyOfficerList, FacultyOfficer> {

    private FacultyList facultyList;

    public FacultyOfficerDataSource(FacultyList facultyList) {
        this.facultyList = facultyList;
    }

    @Override
    public String getFileName() {
        return "faculty_officer.csv";
    }

    @Override
    public String getDirectory() {
        return "data/users/";
    }

    @Override
    public FacultyOfficer hashMapToModel(HashMap<String, String> row) {
        return new FacultyOfficer(
                row.get("username"),
                row.get("hashedPassword"),
                row.get("nameTitle"),
                row.get("name"),
                row.get("surname"),
                row.get("role"),
                row.get("recentTime"),
                Boolean.parseBoolean(row.get("status")),
                Boolean.parseBoolean(row.get("activated")),
                row.get("profilePictureFileName"),
                this.facultyList.findFacultyById(row.get("faculty"))
        );
    }

    @Override
    public FacultyOfficerList collectionInitializer() {
        return new FacultyOfficerList();
    }

    @Override
    public void addModelToList(FacultyOfficerList list, FacultyOfficer model) {
        list.addOfficer(model);
    }

    // Writable interface methods

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("nameTitle");
        headers.add("username");
        headers.add("hashedPassword");
        headers.add("name");
        headers.add("surname");
        headers.add("role");
        headers.add("recentTime");
        headers.add("status");
        headers.add("activated");
        headers.add("profilePictureFileName");
        headers.add("faculty");

        return headers;
    }

    @Override
    public HashMap<String, String> modelToHashMap(FacultyOfficer model) {
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
        row.put("faculty", model.getFaculty().getId());

        return row;
    }

    @Override
    public ArrayList<FacultyOfficer> getCollectionArrayList(FacultyOfficerList collection) {
        ArrayList<FacultyOfficer> officers = new ArrayList<>();
        for (Officer officer : collection.getOfficers()) {
            officers.add((FacultyOfficer) officer);
        }
        return officers;
    }
}
