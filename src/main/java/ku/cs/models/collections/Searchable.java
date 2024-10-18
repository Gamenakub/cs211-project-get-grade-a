package ku.cs.models.collections;

import java.util.ArrayList;

public interface Searchable<E> {
    ArrayList<E> search(String keyword);

    default ArrayList<E> filter(String keyword) {
        return null;
    }

}
