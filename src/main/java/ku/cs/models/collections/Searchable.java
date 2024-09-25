package ku.cs.models.collections;

import java.util.ArrayList;

public interface Searchable<T> {
    ArrayList<T> search(String term);
    default ArrayList<T> filter(String status){
        return null;
    };
}
