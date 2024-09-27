package ku.cs.services.datahandle;


import java.util.ArrayList;
import java.util.HashMap;

public class DataSourceWriter<COLLECTION,MODEL> {
    private Writable<COLLECTION,MODEL> dataSource;
    /**
     * Converts a data collection into a list of rows, where each row is represented as a `HashMap`.
     *
     * @param userList The data collection to convert.
     * @return An `ArrayList` of `HashMap` objects, each representing a row of data.
     */
    public ArrayList<HashMap<String,String>> getRowOfModel(COLLECTION userList){
        ArrayList<HashMap<String,String>> table = new ArrayList<>();
        for (MODEL user : this.dataSource.getCollectionArrayList(userList)) {
            table.add(this.dataSource.modelToHashMap(user));
        }
        return table;
    }
    public DataSourceWriter(Writable<COLLECTION,MODEL> dataSource){
        this.dataSource = dataSource;
    }
    public void writeData(COLLECTION writeCollection){
        DataManagementSystem dataManagementSystem = new DataManagementSystem(this.dataSource.getDirectory(),this.dataSource.getFileName());
        DataFrame dataFrame = new DataFrame(this.dataSource.getTableHeader());
        for (HashMap<String,String> row : getRowOfModel(writeCollection)){
            // dataFrame.addRow(row);
            HashMap<String,String> sortedRow = new HashMap<>();
            for (String header : this.dataSource.getTableHeader()){
                sortedRow.put(header,row.get(header));
            }
            dataFrame.addRow(sortedRow);
        }
        dataManagementSystem.writeTable(dataFrame);
    }

    public static <MODEL, COLLECTION> DataSourceWriter<MODEL, COLLECTION> create(Writable<MODEL, COLLECTION> dataSource) {
        return new DataSourceWriter<>(dataSource);
    }
}
