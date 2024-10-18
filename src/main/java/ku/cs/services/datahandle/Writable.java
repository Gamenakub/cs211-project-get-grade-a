package ku.cs.services.datahandle;

import java.util.ArrayList;
import java.util.HashMap;


public interface Writable<C, E> {
    String getFileName();
    String getDirectory();
    ArrayList<String> getTableHeader();
    HashMap<String, String> modelToHashMap(E model);
    ArrayList<E> getCollectionArrayList(C collection);
}