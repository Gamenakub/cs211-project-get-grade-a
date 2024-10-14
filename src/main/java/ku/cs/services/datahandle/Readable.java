package ku.cs.services.datahandle;

import java.util.HashMap;


public interface Readable<C, E> {
    String getFileName();

    String getDirectory();

    
    E hashMapToModel(HashMap<String, String> row);

    
    C collectionInitializer();

    
    void addModelToList(C list, E model);
}