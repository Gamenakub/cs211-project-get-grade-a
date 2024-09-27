package ku.cs.models.collections;

import ku.cs.models.AppData;

import java.util.ArrayList;

public class AddDataList {
    private ArrayList<AppData> dataList;
    public AddDataList() {
        dataList = new ArrayList<>();
    }
    public void addData(AppData data) {
        dataList.add(data);
    }
    public AppData findDataByKey(String key) {
        for (AppData data : dataList) {
            if (data.isKey(key)) {
                return data;
            }
        }
        return null;
    }
    public void removeDataByKey(String key) {
        AppData data = findDataByKey(key);
        if (data != null) {
            dataList.remove(data);
        }
    }
    public void setValueByKey(String key, String value) {
        AppData data = findDataByKey(key);
        if (data != null) {
            data.setValue(value);
        }
    }

    public ArrayList<AppData> getDataList() {
        return dataList;
    }
}
