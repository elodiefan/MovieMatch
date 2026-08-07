package use_case.search;

import java.util.List;

/**
 * Interface for objects that can be searched.
 */
public interface Searchable<T> {

    /**
     * Searches items using a keyword.
     */
    List<T> search(String keyword);
}
