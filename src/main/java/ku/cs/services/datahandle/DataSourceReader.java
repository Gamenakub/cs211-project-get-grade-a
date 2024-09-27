package ku.cs.services.datahandle;

import java.util.HashMap;

public class DataSourceReader<COLLECTION,MODEL> {
    private Readable<COLLECTION,MODEL> dataSource;

    public DataSourceReader(Readable<COLLECTION,MODEL> dataSource){
        this.dataSource = dataSource;
    }

    /**
     * Reads data from a file and converts it into a collection of models.
     *
     * @return A collection of type `COLLECTION` containing the data models read from the file.
     */
    public COLLECTION readData() {
        DataManagementSystem dataManagementSystem = new DataManagementSystem(this.dataSource.getDirectory(),this.dataSource.getFileName());
        DataFrame userDataFrame = dataManagementSystem.readTable();
        COLLECTION userCollection = this.dataSource.collectionInitializer();
        if (userDataFrame == null) {
            throw new RuntimeException("Data file not found");
        }
        for (HashMap<String,String> row : userDataFrame.getData()) {
            this.dataSource.addModelToList(userCollection, this.dataSource.hashMapToModel(row));
        }
        return userCollection;
    }
}
