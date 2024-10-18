package ku.cs.models.collections;

import ku.cs.models.ApplicationRecord;
import java.util.ArrayList;

public class ApplicationRecordList {
    private final ArrayList<ApplicationRecord> dataList;

    public ApplicationRecordList() {
        dataList = new ArrayList<>();
    }

    public void addData(ApplicationRecord data) {
        dataList.add(data);
    }

    public ApplicationRecord findDataByKey(String key) {
        for (ApplicationRecord data : dataList) {
            if (data.isKey(key)) {
                return data;
            }
        }
        return null;
    }

    public ArrayList<ApplicationRecord> getDataList() {
        return dataList;
    }
}
