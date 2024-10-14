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
        return "faculty.csv";
    }

    @Override
    public String getDirectory() {
        return "data";
    }

    @Override
    public Faculty hashMapToModel(HashMap<String, String> row) {
        String name = row.get("facultyName");
        String id = row.get("facultyId");
        return new Faculty(name, id);
    }

    @Override
    public FacultyList collectionInitializer() {
        return new FacultyList();
    }

    @Override
    public void addModelToList(FacultyList list, Faculty model) {
        list.addFaculty(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("facultyName");
        header.add("facultyId");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(Faculty model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("facultyName", model.getName());
        map.put("facultyId", model.getId());
        return map;
    }

    @Override
    public ArrayList<Faculty> getCollectionArrayList(FacultyList collection) {
        return collection.getFaculties();
    }
}

