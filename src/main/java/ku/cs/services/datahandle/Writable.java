package ku.cs.services.datahandle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The `Writable` class is an abstract class that provides a template for saving data
 * It defines generic methods that must be implemented by subclasses
 * to handle specific types of data collections and models.
 *
 * @param <COLLECTION> The type of the data collection that will be handled (e.g., userList, facultyList).
 * @param <MODEL> The type of the data model that will be stored in the collection.
 */
public interface Writable<COLLECTION,MODEL> {
    String getFileName();
    String getDirectory();
    /**
     * Returns the table header `ArrayList` of strings representing/
     *
     * @return An `ArrayList` of strings representing the column headers of the data table.
     */
    ArrayList<String> getTableHeader();

    /**
     * Converts a `MODEL` object to a `HashMap` representing a row of data.
     *
     * @param model The `MODEL` object to convert.
     * @return A `HashMap` representing the model's data, with column names as keys and data as values.
     */
    HashMap<String,String> modelToHashMap(MODEL model); //

    /**
     * Returns an `ArrayList` of `MODEL` objects from the data collection.
     *
     * @param collection The data collection from which to extract models.
     * @return An `ArrayList` of `MODEL` objects contained in the collection.
     */
    ArrayList<MODEL> getCollectionArrayList(COLLECTION collection); //
}