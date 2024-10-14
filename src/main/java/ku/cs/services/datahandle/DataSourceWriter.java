package ku.cs.services.datahandle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataSourceWriter<C, E> {
    private final Writable<C, E> dataSource;

    public DataSourceWriter(Writable<C, E> dataSource) {
        this.dataSource = dataSource;
    }

    
    public ArrayList<HashMap<String, String>> getRowOfModel(C userList) {
        ArrayList<HashMap<String, String>> table = new ArrayList<>();
        for (E user : this.dataSource.getCollectionArrayList(userList)) {
            table.add(this.dataSource.modelToHashMap(user));
        }
        return table;
    }

    public void writeData(C writeCollection) throws IOException {
        DataManagementSystem dataManagementSystem = new DataManagementSystem(this.dataSource.getDirectory(), this.dataSource.getFileName());
        DataFrame dataFrame = new DataFrame(this.dataSource.getTableHeader());
        for (HashMap<String, String> row : getRowOfModel(writeCollection)) {

            HashMap<String, String> sortedRow = new HashMap<>();
            for (String header : this.dataSource.getTableHeader()) {
                sortedRow.put(header, row.get(header));
            }
            dataFrame.addRow(sortedRow);
        }
        dataManagementSystem.writeTable(dataFrame);
    }
}
