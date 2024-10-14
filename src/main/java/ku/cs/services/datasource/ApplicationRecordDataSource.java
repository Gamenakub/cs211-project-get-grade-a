package ku.cs.services.datasource;


import ku.cs.models.ApplicationRecord;
import ku.cs.models.collections.ApplicationRecordList;
import ku.cs.services.datahandle.Readable;
import ku.cs.services.datahandle.Writable;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationRecordDataSource implements Writable<ApplicationRecordList, ApplicationRecord>, Readable<ApplicationRecordList, ApplicationRecord> {

    @Override
    public String getFileName() {
        return "application_record.csv";
    }

    @Override
    public String getDirectory() {
        return "data/";
    }

    @Override
    public ApplicationRecord hashMapToModel(HashMap<String, String> row) {
        String key = row.get("key");
        String value = row.get("value");
        return new ApplicationRecord(key, value);
    }

    @Override
    public ApplicationRecordList collectionInitializer() {
        return new ApplicationRecordList();
    }

    @Override
    public void addModelToList(ApplicationRecordList list, ApplicationRecord model) {
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
    public HashMap<String, String> modelToHashMap(ApplicationRecord model) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", model.getKey());
        map.put("value", model.getValue());
        return map;
    }

    @Override
    public ArrayList<ApplicationRecord> getCollectionArrayList(ApplicationRecordList collection) {
        return collection.getDataList();
    }
}
