package ku.cs.services.datasource;

import ku.cs.models.Faculty;
import ku.cs.models.collections.FacultyList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class FacultyDataSource implements Writable<FacultyList, Faculty>, Readable<FacultyList, Faculty> {

    @Override
    public String getFileName() {
        return "faculty.csv";  // Assuming the data is stored in CSV format
    }

    @Override
    public String getDirectory() {
        return "data";  // Directory where the file is located
    }

    @Override
    public Faculty hashMapToModel(HashMap<String, String> row) {
        String name = row.get("facultyName");
        String id = row.get("facultyId");
        return new Faculty(name, id);  // Create a Faculty model from the row data
    }

    @Override
    public FacultyList collectionInitializer() {
        return new FacultyList();  // Initialize an empty FacultyList
    }

    @Override
    public void addModelToList(FacultyList list, Faculty model) {
        list.addFaculty(model);  // Add a Faculty model to the FacultyList
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("facultyName");  // Name of the faculty
        header.add("facultyId");    // ID of the faculty
        return header;  // Return the table header for CSV
    }

    @Override
    public HashMap<String, String> modelToHashMap(Faculty model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("facultyName", model.getName());  // Map the faculty name
        map.put("facultyId", model.getId());      // Map the faculty ID
        return map;
    }

    @Override
    public ArrayList<Faculty> getCollectionArrayList(FacultyList collection) {
        return collection.getFaculties();  // Return the list of faculties from FacultyList
    }
}

