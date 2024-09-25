package ku.cs.services.datahandle;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {
    public ArrayList<String> getHeader() {
        return header;
    }

    public ArrayList<HashMap<String,String>> getData() {
        return data;
    }

    private ArrayList<String> header;
    private ArrayList<HashMap<String,String>> data;
    public DataFrame(ArrayList<String> header) {
        this.header = header;
        this.data = new ArrayList<>();
    }

    public boolean isInTable(String column) {
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equals(column)) {
                return true;
            }
        }
        return false;
    }

    // check if row matched header
    public boolean isMatch(ArrayList<String> row) {
        for (int i = 0; i < header.size(); i++) {
            if (!row.get(i).equals(header.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isMatch(HashMap<String,String> row) {
        for (int i = 0; i < header.size(); i++) {
            if (!row.containsKey(header.get(i))) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<String> getRowList(int index) {
        if (index < 0 || index >= data.size()) {
            return new ArrayList<String>();
        }
        ArrayList<String> head = getHeader();
        ArrayList<String> row = new ArrayList<>();
        ArrayList<HashMap<String,String>> data = getData();
        // loop all header
        for (int i = 0; i < head.size();i++){
            String key = head.get(i);
            String value = data.get(index).get(key);
            row.add(value);
        }
        return row;
    }

    public void addRow(ArrayList<String> row) {
        if (row.size() != header.size()) {
            throw new IllegalArgumentException("Row array size doesn't match header array size");
        }
        HashMap<String ,String> newRow = new HashMap<>();
        for (int i = 0; i < header.size(); i++) {
            newRow.put(header.get(i), row.get(i));
        }
        data.add(newRow);

    }
    public void addRow(HashMap<String, String> row) {
        System.out.println("xx "+row);
        if (isMatch(row)) {
            data.add(row);
        }
        else {
            throw new IllegalArgumentException("Row incorrect format");
        }
    }

    public void addRow(String[] data) {
        ArrayList<String> newRow = new ArrayList<>();
        for (int i = 0; i < header.size(); i++) {
            newRow.add(data[i]);
        }
        addRow(newRow);
    }

    public HashMap<String, String> findWhere(String selector, String value){
        if (!isInTable(selector)) {
            throw new IllegalArgumentException("selector doesn't exist");
        }
        for (int i = 0;i < data.size();i++) {
            if (data.get(i).get(selector).equals(value)) {
                return data.get(i);
            }
        }
        return null;
    }


    public static interface Datasource<T> {
        T readData();
        void writeData(T data);
    }
}
