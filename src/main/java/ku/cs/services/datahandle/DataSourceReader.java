package ku.cs.services.datahandle;

import java.io.IOException;
import java.util.HashMap;

public class DataSourceReader<C, E> {
    private final Readable<C, E> dataSource;

    public DataSourceReader(Readable<C, E> dataSource) {
        this.dataSource = dataSource;
    }

    
    public C readData() {
        DataFrame userDataFrame;
        try {
            DataManagementSystem dataManagementSystem = new DataManagementSystem(this.dataSource.getDirectory(), this.dataSource.getFileName());
            userDataFrame = dataManagementSystem.readTable();
        } catch (IOException e) {
            return this.dataSource.collectionInitializer();
        }
        C userCollection = this.dataSource.collectionInitializer();
        if (userDataFrame == null) {
            return userCollection;
        }
        for (HashMap<String, String> row : userDataFrame.getData()) {
            this.dataSource.addModelToList(userCollection, this.dataSource.hashMapToModel(row));
        }
        return userCollection;
    }
}
