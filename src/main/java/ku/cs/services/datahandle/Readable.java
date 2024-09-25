package ku.cs.services.datahandle;

import java.util.HashMap;

/**
 * The `Readable` class is an abstract class that provides a template for loading data
 * It defines generic methods that must be implemented by subclasses
 * to handle specific types of data collections and models.
 *
 * @param <COLLECTION> The type of the data collection that will be handled (e.g., userList, facultyList).
 * @param <MODEL> The type of the data model that will be stored in the collection.
 */
public interface Readable<COLLECTION,MODEL> {
    String getFileName();
    String getDirectory();

    /**
     * Converts a row of data represented as a `HashMap` to a `MODEL` object.
     *
     * @param row A `HashMap` representing a row of data, with column names as keys and data as values.
     * @return A `MODEL` object representing the data in the row.
     */
    MODEL hashMapToModel(HashMap<String, String> row);

    /**
     * Initializes and returns an empty data collection.
     *
     * @return A new, empty data collection of type `COLLECTION`.
     */
    COLLECTION collectionInitializer();

    /**
     * Adds a `MODEL` object to a data collection.
     *
     * @param list  The data collection to which the model should be added.
     * @param model The `MODEL` object to add to the collection.
     */
    void addModelToList(COLLECTION list, MODEL model);
}