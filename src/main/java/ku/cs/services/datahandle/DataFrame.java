package ku.cs.services.datahandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataFrame {
    private final ArrayList<String> header;
    private final ArrayList<HashMap<String, String>> data;

    public DataFrame(ArrayList<String> header) {
        this.header = header;
        this.data = new ArrayList<>();
    }

    public ArrayList<String> getHeader() {
        return header;
    }

    public ArrayList<HashMap<String, String>> getData() {
        return data;
    }

    public boolean isMatch(HashMap<String, String> row) {
        for (String s : header) {
            if (!row.containsKey(s)) {
                return false;
            }
        }
        return true;
    }


    public ArrayList<String> getRowList(int index) {
        if (index < 0 || index >= data.size()) {
            return new ArrayList<>();
        }
        ArrayList<String> head = getHeader();
        ArrayList<String> row = new ArrayList<>();
        ArrayList<HashMap<String, String>> data = getData();

        for (String key : head) {
            String value = data.get(index).get(key);
            row.add(value);
        }
        return row;
    }

    public void addRow(ArrayList<String> row) {
        if (row.size() != header.size()) {
            throw new IllegalArgumentException("Row array size doesn't match header array size");
        }
        HashMap<String, String> newRow = new HashMap<>();
        for (int i = 0; i < header.size(); i++) {
            newRow.put(header.get(i), row.get(i));
        }
        data.add(newRow);

    }

    public void addRow(HashMap<String, String> row) {
        if (isMatch(row)) {
            data.add(row);
        } else {
            throw new IllegalArgumentException("Row incorrect format");
        }
    }

    public void addRow(String[] data) {
        ArrayList<String> newRow = new ArrayList<>(Arrays.asList(data).subList(0, header.size()));
        addRow(newRow);
    }

}
