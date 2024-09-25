package ku.cs.services.datasource;


import ku.cs.models.AppData;
import ku.cs.models.collections.AddDataList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class AppDataSource implements Writable<AddDataList, AppData>, Readable<AddDataList, AppData> {

    private final String fileName = "app_data.csv";
    private final String directory = "data/";

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getDirectory() {
        return directory;
    }

    @Override
    public AppData hashMapToModel(HashMap<String, String> row) {
        String key = row.get("key");
        String value = row.get("value");
        return new AppData(key, value);
    }

    @Override
    public AddDataList collectionInitializer() {
        return new AddDataList();
    }

    @Override
    public void addModelToList(AddDataList list, AppData model) {
        list.addData(model);
    }

    @Override
    public ArrayList<String> getTableHeader() {
        ArrayList<String> header = new ArrayList<>();
        header.add("key");
        header.add("value");
        return header;
    }

    @Override
    public HashMap<String, String> modelToHashMap(AppData model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", model.getKey()); // Assuming getKey() exists
        map.put("value", model.getValue());
        return map;
    }

    @Override
    public ArrayList<AppData> getCollectionArrayList(AddDataList collection) {
        return collection.getDataList();
    }
}
